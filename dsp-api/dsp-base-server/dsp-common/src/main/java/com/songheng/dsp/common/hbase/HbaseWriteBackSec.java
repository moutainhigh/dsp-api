package com.songheng.dsp.common.hbase;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;
import com.songheng.dsp.common.redis.RedisWriteBack;
import com.songheng.dsp.common.sentinel.HbaseSentinelResource;
import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.ThreadPoolUtils;
import com.songheng.dsp.common.utils.serialize.KryoSerialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 16:16
 * @description: HbaseWriteBackSec
 */
@Slf4j
public abstract class HbaseWriteBackSec {

    /**
     *  按照hbase的操作类型，分类存储 回写队列
     *  Hbase Sentinel 拦截数据，容量达到一定大小
     */
    private volatile static Map<String,ConcurrentLinkedQueue<HbaseExecArgs>> writeBackMapSec = new ConcurrentHashMap<>();

    /**
     * 将回写异常数据插入此队列的尾部
     * @param resourceKey Hbase sentinel resourceKey
     * @param writeBack 回写对象
     */
    public static void putWriteBackInfo(String resourceKey, HbaseExecArgs writeBack) {
        if (StringUtils.isNotBlank(resourceKey) && null != writeBack){
            synchronized (writeBackMapSec) {
                if (!writeBackMapSec.containsKey(resourceKey)){
                    ConcurrentLinkedQueue<HbaseExecArgs> writeBackInfo = new ConcurrentLinkedQueue<>();
                    writeBackInfo.offer(writeBack);
                    writeBackMapSec.put(resourceKey, writeBackInfo);
                }else{
                    writeBackMapSec.get(resourceKey).offer(writeBack);
                }
            }
        }
    }

    /**
     * 将回写异常数据插入此队列的尾部
     * @param resourceKey Hbase sentinel resourceKey
     * @param writeBack 回写对象
     */
    public static void putAllWriteBackInfo(String resourceKey, ConcurrentLinkedQueue<HbaseExecArgs> writeBack) {
        if (StringUtils.isNotBlank(resourceKey) && null != writeBack){
            synchronized (writeBackMapSec) {
                if (!writeBackMapSec.containsKey(resourceKey)){
                    writeBackMapSec.put(resourceKey, writeBack);
                }else{
                    writeBackMapSec.get(resourceKey).addAll(writeBack);
                }
            }
        }

    }

    /**
     * 获取回写队列信息
     * @param resourceKey Hbase sentinel resourceKey
     * @return
     */
    public static ConcurrentLinkedQueue<HbaseExecArgs> getWriteBackInfo(String resourceKey) {
        synchronized (writeBackMapSec) {
            return writeBackMapSec.containsKey(resourceKey) ? writeBackMapSec.get(resourceKey)
                    : new ConcurrentLinkedQueue<HbaseExecArgs>();
        }
    }

    /**
     * 获取全部回写队列信息
     * @return
     */
    public static Map<String,ConcurrentLinkedQueue<HbaseExecArgs>> getWriteBackInfo(){
        synchronized (writeBackMapSec) {
            return HbaseWriteBackSec.writeBackMapSec;
        }
    }

    /**
     * 定时任务检测Hbase读写QPS是否有下降，
     * 如有下降则执行剩余待回写内容，
     * 如峰值持续不降，则不做任何操作
     *
     */
    protected void checkHbaseQps(){
        for (HbaseSentinelResource ds : HbaseSentinelResource.values()){
            String resourceKey = ds.getName();
            //此时Hbase Sentinel Qps
            int totalQps = 0;
            //获取 该 resourceKey 的 ClusterNode
            ClusterNode clusterNode = ClusterBuilderSlot.getClusterNode(resourceKey,EntryType.IN);
            if (null != clusterNode){
                totalQps = new Long(clusterNode.totalQps()).intValue();
            }
            //该 resourceKey Hbase峰值 持续不降
            if (totalQps > HbaseWriteBack.qpsLimit) {
                continue;
            }
            final int batchNum = HbaseWriteBack.qpsLimit - totalQps;
            if (batchNum > 0){
                //按resourceKey 反序列化 读取对应文件
                deserializeWBHbaseSec(resourceKey);
                final ConcurrentLinkedQueue<HbaseExecArgs> writeBackInfo = HbaseWriteBackSec.getWriteBackInfo(resourceKey);
                if (null != writeBackInfo){
                    //回写耗时操作 新起一个线程执行
                    ThreadPoolUtils.submit("writeback", new Runnable() {
                        @Override
                        public void run() {
                            HbaseWriteBack.batchExeHbaseWBInfo(writeBackInfo, batchNum);
                        }
                    });
                }
            }
        }
    }

    /**
     * 定时任务 调取
     * 反序列化 Hbase 读取内容
     *
     * 读取回写队列size大于上限的序列化文件
     *
     */
    protected void deserializeWBHbaseSec(String resourceKey){
        if (StringUtils.isBlank(resourceKey)){
            return;
        }
        try {
            String fileName = null;
            for (int i=1; i<=HbaseWriteBack.fileCount; i++){
                //循环找到不存在的文件 如：adplatform_adstatus_0001.txt
                fileName = String.format("%s_%04d%s", resourceKey, i, PropertyPlaceholder.getProperty("writeback.file.suffix"));
                File file = Paths.get(RedisWriteBack.fileDir, fileName).toFile();
                //找到对应文件
                if (file.exists()){
                    break;
                }else{
                    fileName = null;
                }
            }
            if (StringUtils.isNotBlank(fileName)){
                String filePath = String.format("%s%s", RedisWriteBack.fileDir, fileName);
                //反序列化 读取数据
                ConcurrentLinkedQueue<HbaseExecArgs> deSerialWriteBackInfos =
                        KryoSerialize.readFromString(FileUtils.readString(filePath));
                if (!deSerialWriteBackInfos.isEmpty()){
                    HbaseWriteBackSec.putAllWriteBackInfo(resourceKey, deSerialWriteBackInfos);
                }
                File file = Paths.get(filePath).toFile();
                //反序列化 读取文件成功，删除该文件
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                log.info("【HbaseWriteBackSec】反序列化读取文件，filePath = {}, deSerialObjSize = {}", filePath, deSerialWriteBackInfos.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 容器销毁时
     * 序列化Hbase未处理的数据
     *
     * 容器销毁时，将未处理完的回写任务再次序列化至文件
     *
     */
    protected void serializeWBHbaseSec(){
        //获取Hbase未处理的数据
        Map<String,ConcurrentLinkedQueue<HbaseExecArgs>>  writeBackMap = HbaseWriteBackSec.getWriteBackInfo();
        if (null != writeBackMap && writeBackMap.size() > 0){
            for (String resourceKey : writeBackMap.keySet()){
                ConcurrentLinkedQueue<HbaseExecArgs> memoryData = writeBackMap.get(resourceKey);
                ConcurrentLinkedQueue<HbaseExecArgs> serialRecord = new ConcurrentLinkedQueue<>();
                while (!memoryData.isEmpty()){
                    //获取需要序列化至文件的队列，指定大小，并且移除缓存队列
                    serialRecord.offer(memoryData.poll());
                }
                String fileName = null;
                for (int i=1; i<=HbaseWriteBack.fileCount; i++){
                    //循环找到不存在的文件 如：adplatform_adstatus_0001.txt
                    fileName = String.format("%s_%04d%s", resourceKey, i, PropertyPlaceholder.getProperty("writeback.file.suffix"));
                    File file = Paths.get(RedisWriteBack.fileDir, fileName).toFile();
                    //找出没被占用的文件编号
                    if (!file.exists()){
                        break;
                    }else{
                        fileName = null;
                    }
                }
                if (StringUtils.isNotBlank(fileName)){
                    String filePath = String.format("%s%s", RedisWriteBack.fileDir, fileName);
                    boolean result = FileUtils.writeFile(filePath, KryoSerialize.writeToString(serialRecord), false);
                    log.info("【HbaseWriteBackSec】序列化写入文件，result = {}, filePath = {}, serialObjSize = {}", result, filePath, serialRecord.size());
                }
            }
        }
    }

    /**
     * 时任务 回写监控拦截，序列化至文件的数据
     */
    protected abstract void writeBackSentinelSizeOutByTimer();

    /**
     * 容器初始化调用
     */
    protected abstract void initialized();

    /**
     * 容器销毁调用
     */
    protected abstract void destroyed();

}
