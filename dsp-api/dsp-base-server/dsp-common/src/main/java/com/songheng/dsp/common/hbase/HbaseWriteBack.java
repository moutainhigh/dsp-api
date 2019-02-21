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
 * @date: 2019/2/21 13:43
 * @description: HbaseWriteBack
 */
@Slf4j
public abstract class HbaseWriteBack {

    /**
     *  按照hbase的操作类型，分类存储 回写队列
     *  Hbase Sentinel 拦截的数据
     */
    private volatile static Map<String,ConcurrentLinkedQueue<HbaseExecArgs>> writeBackMap = new ConcurrentHashMap<>();

    /**
     * Hbase 回写文件数 上限
     */
    public static int fileCount = Integer.parseInt(com.songheng.dsp.common.utils.StringUtils.replaceInvalidString(
            PropertyPlaceholder.getProperty("sentinel_hbase_wb_file_count"), "100"));
    /**
     * 回写队列最大size
     */
    public static int writebackmaxsize = Integer.parseInt(com.songheng.dsp.common.utils.StringUtils.replaceInvalidString(
            PropertyPlaceholder.getProperty("sentinel_hbase_wb_max_size"), "50000"));
    /**
     * Hbase QPS 峰值
     */
    public static int qpsLimit = Integer.parseInt(com.songheng.dsp.common.utils.StringUtils.replaceInvalidString(
            PropertyPlaceholder.getProperty("sentinel_hbase_qps_limit"), "300"));


    /**
     * 将回写异常数据插入此队列的尾部
     * @param resourceKey Hbase sentinel resourceKey
     * @param writeBack 回写对象
     */
    public static void putWriteBackInfo(String resourceKey, HbaseExecArgs writeBack) {
        if (StringUtils.isNotBlank(resourceKey) && null != writeBack){
            synchronized (writeBackMap) {
                if (writeBackMap.containsKey(resourceKey)){
                    writeBackMap.get(resourceKey).offer(writeBack);
                }else{
                    ConcurrentLinkedQueue<HbaseExecArgs> writeBackInfo = new ConcurrentLinkedQueue<>();
                    writeBackInfo.offer(writeBack);
                    writeBackMap.put(resourceKey, writeBackInfo);
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
            synchronized (writeBackMap) {
                if (writeBackMap.containsKey(resourceKey)){
                    writeBackMap.get(resourceKey).addAll(writeBack);
                }else{
                    writeBackMap.put(resourceKey, writeBack);
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
        synchronized (writeBackMap) {
            return writeBackMap.containsKey(resourceKey) ? writeBackMap.get(resourceKey)
                    : new ConcurrentLinkedQueue<HbaseExecArgs>();
        }
    }

    /**
     * 获取全部回写队列信息
     * @return
     */
    public static Map<String,ConcurrentLinkedQueue<HbaseExecArgs>> getWriteBackInfo(){
        synchronized (writeBackMap) {
            return HbaseWriteBack.writeBackMap;
        }
    }

    /**
     * 通过定时任务 将 Hbase 回写，
     * 如回写队列size大于上限值，
     * 则将该队列序列化至文件，
     * 然后清空该队列，
     * 待Hbase读写峰值降下来之后再次回写
     *
     */
    protected void writeBackHbase(){
        try {
            if (HbaseWriteBack.writeBackMap.size() > 0){
                for (String resourceKey : HbaseWriteBack.writeBackMap.keySet()){
                    final ConcurrentLinkedQueue<HbaseExecArgs> writeBackInfo =
                            HbaseWriteBack.writeBackMap.get(resourceKey);
                    //回写队列size
                    int size = writeBackInfo.size();
                    //回写队列size大于上限值，序列化至文件，清空队列
                    if (size > writebackmaxsize){
                        String fileName = null;
                        for (int i=1; i<=fileCount; i++){
                            //循环找到不存在的文件 如：adplatform_adstatus_0001.txt
                            fileName = String.format("%s_%04d%s", resourceKey, i, PropertyPlaceholder.getProperty("writeback.file.suffix"));
                            File file = Paths.get(RedisWriteBack.fileDir, fileName).toFile();
                            //找到没被占用的文件编号
                            if (!file.exists()){
                                break;
                            }else{
                                fileName = null;
                            }
                        }
                        ConcurrentLinkedQueue<HbaseExecArgs> wbRecord = new ConcurrentLinkedQueue<>();
                        for (int i=0; i<size; i++){
                            //获取需要序列化至文件的队列，指定大小，并且移除缓存队列
                            HbaseExecArgs obj = writeBackInfo.poll();
                            if (null != obj) {
                                wbRecord.offer(obj);
                            }
                        }
                        int serializeSize = wbRecord.size();
                        if (StringUtils.isNotBlank(fileName)){
                            String filePath = String.format("%s%s", RedisWriteBack.fileDir, fileName);
                            boolean serializeRet = FileUtils.writeFile(filePath, KryoSerialize.writeToString(wbRecord), false);

                            StringBuffer sb = new StringBuffer();
                            sb.append("【回写队列size大于上限值，序列化至文件】\t").append("【size: "+serializeSize+"】");
                            //序列化成功
                            if (serializeRet){
                                sb.append("【serializeResult: true】").append("\t")
                                        .append("【filepath："+filePath+"】");
                            } else{//序列化失败，重新写入缓存队列
                                HbaseWriteBack.putAllWriteBackInfo(resourceKey, wbRecord);
                                File file = Paths.get(filePath).toFile();
                                if (file.exists() && file.isFile()) {
                                    //反序列化失败，如果存在脏数据文件，则删除
                                    file.delete();
                                }
                                sb.append("【serializeResult: false】").append("\t")
                                        .append("【重新写入缓存队列】");
                            }
                            log.info(sb.toString());
                        }
//                        StringBuffer msg = new StringBuffer("【ip:")
//                                .append(OpUtils.getServerIp())
//                                .append("】【dspdatalog】【Hbase Sentinel 回写队列size大于上限值】【")
//                                .append(resourceKey)
//                                .append("】【size: ").append(serializeSize)
//                                .append("】\r\n");
//                        MsgNotifyUtil.sendMsg(msg.toString());

                    }else{
                        //此时Hbase Sentinel Qps
                        int totalQps = 0;
                        //获取 该 resourceKey 的 ClusterNode
                        ClusterNode clusterNode = ClusterBuilderSlot.getClusterNode(resourceKey,EntryType.IN);
                        if (null != clusterNode){
                            totalQps = new Long(clusterNode.totalQps()).intValue();
                        }
                        //该 resourceKey Hbase峰值 持续不降
                        if (totalQps > qpsLimit) {
                            continue;
                        }
                        final int batchNum = qpsLimit - totalQps;
                        if (batchNum > 0){
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

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 分批次执行Hbase待回写内容
     *
     * @param writeBack ConcurrentLinkedQueue<HbaseExecArgs>
     * @param batchNum 每次批量执行数量
     *
     */
    public static void batchExeHbaseWBInfo(ConcurrentLinkedQueue<HbaseExecArgs> writeBack, int batchNum){
        if (writeBack.isEmpty() || batchNum <= 0){
            return;
        }
        int dataSize = writeBack.size();
        //执行偏移量(多少批)
        int offset = dataSize/batchNum;
        //取模，剩余不满一个批次的量
        int surplus = dataSize%batchNum;
        for (int i=0; i<offset; i++){
            //每次执行量
            int exeNumPer = batchNum;
            while (exeNumPer > 0 && !writeBack.isEmpty()){
                HbaseExecArgs wbObj = writeBack.poll();
                //执行 hbase 方法
                HbaseUtil.writeExec(wbObj);
                exeNumPer -= 1;
            }
            try {
                //每次执行完睡眠80ms
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //剩余不满一个批次的量，一次执行
        if (surplus > 0){
            while (surplus > 0 && !writeBack.isEmpty()){
                HbaseExecArgs wbObj = writeBack.poll();
                //执行 hbase 方法
                HbaseUtil.writeExec(wbObj);
                surplus -= 1;
            }
        }
        log.info("【批量回写 Hbase Sentinel超峰值数据 成功】【size：{} 】", dataSize);

    }

    /**
     * 序列化写入文件
     *
     */
    protected void serialWriteBackInfos(){
        //获取Hbase未处理的数据
        Map<String,ConcurrentLinkedQueue<HbaseExecArgs>>  writeBackMap = HbaseWriteBack.getWriteBackInfo();
        if (null != writeBackMap && writeBackMap.size() > 0){
            for (String resourceKey : writeBackMap.keySet()){
                ConcurrentLinkedQueue<HbaseExecArgs> memoryData = writeBackMap.get(resourceKey);
                ConcurrentLinkedQueue<HbaseExecArgs> serialRecord = new ConcurrentLinkedQueue<>();
                while (!memoryData.isEmpty()){
                    //获取需要序列化至文件的队列，指定大小，并且移除缓存队列
                    serialRecord.offer(memoryData.poll());
                }
                String filePath = String.format("%s%s%s", RedisWriteBack.fileDir, resourceKey, PropertyPlaceholder.getProperty("writeback.file.suffix"));
                if (!serialRecord.isEmpty()){
                    boolean result = FileUtils.writeFile(filePath, KryoSerialize.writeToString(serialRecord), false);
                    log.info("【HbaseWriteBack】序列化写入文件，result = {}, filePath = {}, serialObjSize = {}", result, filePath, serialRecord.size());
                }
            }
        }
    }

    /**
     * 反序列化读取文件
     *
     */
    protected void deSerialWriteBackInfos(){
        try {
            for (HbaseSentinelResource ds : HbaseSentinelResource.values()){
                String resourceKey = ds.getName();
                String filePath = String.format("%s%s%s", RedisWriteBack.fileDir, resourceKey, PropertyPlaceholder.getProperty("writeback.file.suffix"));
                File file = new File(filePath);
                if (!file.exists()){
                    continue;
                }
                //反序列化 读取数据
                ConcurrentLinkedQueue<HbaseExecArgs> deSerialWriteBackInfos =
                        KryoSerialize.readFromString(FileUtils.readString(filePath));
                if (!deSerialWriteBackInfos.isEmpty()){
                    HbaseWriteBack.putAllWriteBackInfo(resourceKey,deSerialWriteBackInfos);
                }
                //反序列化 读取文件成功，删除该文件
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                log.info("【HbaseWriteBack】反序列化读取文件，filePath = {}, deSerialObjSize = {}", filePath, deSerialWriteBackInfos.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定时任务 回写监控拦截的数据
     */
    protected abstract void writeBackSentinelByTimer();

    /**
     * 容器初始化调用
     */
    protected abstract void initialized();

    /**
     * 容器销毁调用
     */
    protected abstract void destroyed();

}
