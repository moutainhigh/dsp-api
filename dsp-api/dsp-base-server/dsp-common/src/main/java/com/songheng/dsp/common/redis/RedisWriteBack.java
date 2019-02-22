package com.songheng.dsp.common.redis;

import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.serialize.KryoSerialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: luoshaobing
 * @date: 2019/2/19 16:23
 * @description: redis写异常回写机制
 */
@Slf4j
public abstract class RedisWriteBack {

    /**
     * 回写异常数据
     */
    private static ConcurrentLinkedQueue<WriteBackObj> writeBackExcpt = new ConcurrentLinkedQueue<>();
    /**
     * fileDir
     */
    public static String fileDir = String.format("%s%s%s", PropertyPlaceholder.getProperty("writeback.file.dataHome"), System.getProperty("dsp.project.name"),
            PropertyPlaceholder.getProperty("writeback.file.dir"));
    /**
     * filePath
     */
    private static String filePath = String.format("%s%s%s", RedisWriteBack.fileDir, PropertyPlaceholder.getProperty("writeback.file.redis.fileName"),
            PropertyPlaceholder.getProperty("writeback.file.suffix"));
    /**
     * 将回写异常数据插入此队列的尾部
     * @param writeBack
     */
    public static void putWriteBackInfo(WriteBackObj writeBack) {
        synchronized (writeBackExcpt) {
            writeBackExcpt.offer(writeBack);
        }
    }
    /**
     * 批量写入队列
     * @param writeBackObjs
     */
    public static void putAllWriteBackInfo(ConcurrentLinkedQueue<WriteBackObj> writeBackObjs){
        synchronized (writeBackExcpt) {
            if (!writeBackObjs.isEmpty()){
                writeBackExcpt.addAll(writeBackObjs);
            }
        }
    }
    /**
     * 获取回写异常数据
     * @return
     */
    public static ConcurrentLinkedQueue<WriteBackObj> getWriteBackInfo() {
        synchronized (writeBackExcpt) {
            return writeBackExcpt;
        }
    }

    /**
     * redis写异常回写数据
     * @param writeBackObj
     */
    protected void writeBack(WriteBackObj writeBackObj){
        if (null == writeBackObj){
            return;
        }
        try {
            RedisCommand command = writeBackObj.getCommand();
            switch (command) {
                case EXPIRE:
                    RedisCache.expire(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getSeconds());
                    break;
                case EXPIREAT:
                    RedisCache.expireAt(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL());
                    break;
                case PEXPIRE:
                    RedisCache.pexpire(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL());
                    break;
                case PEXPIREAT:
                    RedisCache.pexpireAt(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL());
                    break;
                case PERSIST:
                    RedisCache.persist(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey());
                    break;
                case DEL:
                    RedisCache.del(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey());
                    break;
                case SET:
                    RedisCache.set(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueS());
                    break;
                case SETNX:
                    RedisCache.setnx(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueS());
                    break;
                case INCR:
                    RedisCache.incr(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey());
                    break;
                case INCRBY:
                    RedisCache.incrBy(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL());
                    break;
                case INCRBYFLOAT:
                    RedisCache.incrByFloat(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueD());
                    break;
                case DECR:
                    RedisCache.decr(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey());
                    break;
                case DECRBY:
                    RedisCache.decrBy(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL());
                    break;
                case HSET:
                    RedisCache.hset(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getField(),writeBackObj.getValueS());
                    break;
                case HSETNX:
                    RedisCache.hsetnx(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getField(),writeBackObj.getValueS());
                    break;
                case HMSET:
                    RedisCache.hmset(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getHash());
                    break;
                case HINCRBY:
                    RedisCache.hincrBy(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getField(),writeBackObj.getValueL());
                    break;
                case HINCRBYFLOAT:
                    RedisCache.hincrByFloat(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getField(),writeBackObj.getValueD());
                    break;
                case HDEL:
                    RedisCache.hdel(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case HMSETBYPIPELINE:
                    if (StringUtils.isNotBlank(writeBackObj.getKey())
                            && (null != writeBackObj.getField_vals_byincr() || null != writeBackObj.getField_vals_byset())){
                        RedisCache.hmsetByPipeline(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getField_vals_byincr(),writeBackObj.getField_vals_byset());
                        break;
                    }
                    RedisCache.hmsetByPipeline(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey_field_vals_byincr(),writeBackObj.getKey_field_vals_byset(),writeBackObj.getKey_expire_times());
                    break;
                case DELBYPIPELINE:
                    RedisCache.delByPipeline(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKeys());
                    break;
                case LPUSH:
                    RedisCache.lpush(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case RPUSH:
                    RedisCache.rpush(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case LREM:
                    RedisCache.lrem(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL(),writeBackObj.getValueS());
                    break;
                case LINSERT:
                    RedisCache.linsert(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getWhere(),writeBackObj.getPivot(),writeBackObj.getValueS());
                    break;
                case LSET:
                    RedisCache.lset(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueL(),writeBackObj.getValueS());
                    break;
                case LTRIM:
                    RedisCache.ltrim(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getStartL(),writeBackObj.getEndL());
                    break;
                case SADD:
                    RedisCache.sadd(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case SREM:
                    RedisCache.srem(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case ZADD:
                    if (null != writeBackObj.getScoreMembers() && writeBackObj.getScoreMembers().size() > 0){
                        RedisCache.zadd(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getScoreMembers());
                        break;
                    }
                    RedisCache.zadd(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueD(),writeBackObj.getValueS());
                    break;
                case ZINCRBY:
                    RedisCache.zincrby(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueD(),writeBackObj.getValueS());
                    break;
                case ZREM:
                    RedisCache.zrem(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getValueArray());
                    break;
                case ZREMRANGEBYRANK:
                    RedisCache.zremrangeByRank(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getStartL(),writeBackObj.getEndL());
                    break;
                case ZREMRANGEBYSCORE:
                    RedisCache.zremrangeByScore(writeBackObj.getClusterEnum(),writeBackObj.isNewSharedJedis(),writeBackObj.getKey(),writeBackObj.getStartD(),writeBackObj.getEndD());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis 回写数据异常，RedisCommand = {} \t WriteBackObj = {}", writeBackObj.getCommand(), writeBackObj);
        }

    }

    /**
     * 序列化写入文件
     *
     */
    protected void serialWriteBackInfos(){
        log.info("【RedisWriteBack】序列化写入文件开始...");
        ConcurrentLinkedQueue<WriteBackObj> memoryData = RedisWriteBack.getWriteBackInfo();
        ConcurrentLinkedQueue<WriteBackObj> serialRecord = new ConcurrentLinkedQueue<>();
        while (!memoryData.isEmpty()) {
            serialRecord.offer(memoryData.poll());
        }
        boolean result = FileUtils.writeFile(filePath, KryoSerialize.writeToString(serialRecord), false);
        log.info("【RedisWriteBack】序列化写入文件，result = {}, filePath = {}, serialObjSize = {}", result, filePath, serialRecord.size());
    }

    /**
     * 反序列化读取文件
     *
     */
    protected void deSerialWriteBackInfos(){
        log.info("【RedisWriteBack】反序列化读取文件开始...");
        ConcurrentLinkedQueue<WriteBackObj> obj = KryoSerialize.readFromString(FileUtils.readString(filePath));
        RedisWriteBack.putAllWriteBackInfo(obj);
        log.info("【RedisWriteBack】反序列化读取文件，filePath = {}, deSerialObjSize = {}", filePath, obj.size());
    }

    /**
     * 定时任务 回写异常的数据
     */
    protected abstract void writeBackExcptByTimer();

    /**
     * 容器初始化调用
     */
    protected abstract void initialized();

    /**
     * 容器销毁调用
     */
    protected abstract void destroyed();

}
