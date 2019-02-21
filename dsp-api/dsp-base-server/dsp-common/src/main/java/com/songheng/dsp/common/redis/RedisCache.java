package com.songheng.dsp.common.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * @author: luoshaobing
 * @date: 2019/2/18 13:55
 * @description: redis统一读写工具类
 */
@Slf4j
public class RedisCache {

    /******************************** key操作 ***************************************/

    /**
     * 检查给定 key 是否存在
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static boolean exists(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.exists(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("exists");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return false;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 设置生存时间 单位秒
     * @param isNewSharedJedis
     * @param key
     * @param seconds
     * @return
     */
    public static Long expire(boolean isNewSharedJedis, String key, int seconds){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.expire(key, seconds);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("expire");
            sb.append("\t").append(key).append("\t").append(seconds).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.EXPIRE,isNewSharedJedis,key,seconds));
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 设置生存时间 UNIX 时间戳(unix timestamp)
     * @param isNewSharedJedis
     * @param key
     * @param unixTime
     * @return
     */
    public static Long expireAt(boolean isNewSharedJedis, String key, long unixTime){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.expireAt(key, unixTime);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("expireAt");
            sb.append("\t").append(key).append("\t").append(unixTime).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.EXPIREAT,isNewSharedJedis,key,unixTime));
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 以毫秒为单位设置 key 的生存时间
     * @param isNewSharedJedis
     * @param key
     * @param milliseconds
     * @return
     */
    public static Long pexpire(boolean isNewSharedJedis, String key, long milliseconds){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.pexpire(key ,milliseconds);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("pexpire");
            sb.append("\t").append(key).append("\t").append(milliseconds).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.PEXPIRE,isNewSharedJedis,key,milliseconds));
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 以毫秒为单位设置 key 的过期 unix 时间戳
     * @param isNewSharedJedis
     * @param key
     * @param millisecondsTimestamp
     * @return
     */
    public static Long pexpireAt(boolean isNewSharedJedis, String key, long millisecondsTimestamp){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.pexpireAt(key, millisecondsTimestamp);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("pexpireAt");
            sb.append("\t").append(key).append("\t").append(millisecondsTimestamp).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.PEXPIREAT,isNewSharedJedis,key,millisecondsTimestamp));
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long getTTL(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.ttl(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("ttl");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除给定 key 的生存时间
     * 将这个 key 从“易失的”(带生存时间 key )转换成“持久的”(一个不带生存时间、永不过期的 key )
     * @param isNewSharedJedis
     * @param key
     */
    public static void persist(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.persist(key);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("persist");
            sb.append("\t").append(key).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.PERSIST,isNewSharedJedis,key));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 删除给定的一个或多个 key
     * 不存在的 key 会被忽略
     * @param isNewSharedJedis
     * @param key
     */
    public static void del(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.del(key);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("del");
            sb.append("\t").append(key).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.DEL,isNewSharedJedis,key));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * STRLEN 命令返回字符串值的长度
     * 当键 key 不存在时， 返回 0
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long strlen(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.strlen(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("strlen");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 字符串操作 *************************************/

    /**
     * 将字符串值 value 关联到 key
     * 如果 key 已经持有其他值， SET 就覆写旧值， 无视类型
     * 当 SET 命令对一个带有生存时间（TTL）的键进行设置之后， 该键原有的 TTL 将被清除
     * @param isNewSharedJedis
     * @param key
     * @param value
     */
    public static void set(boolean isNewSharedJedis, String key, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.set(key, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("set");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.SET,isNewSharedJedis,key,value));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 只在键 key 不存在的情况下， 将键 key 的值设置为 value
     * 若键 key 已经存在， 则 SETNX 命令不做任何动作
     * @param isNewSharedJedis
     * @param key
     * @param value
     */
    public static void setnx(boolean isNewSharedJedis, String key, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.setnx(key, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("setnx");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.SETNX,isNewSharedJedis,key,value));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回与键 key 相关联的字符串值
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static String get(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.get(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("get");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将键 key 的值设为 value ， 并返回键 key 在被设置之前的旧值
     * @param isNewSharedJedis
     * @param key
     * @param value
     * @return
     */
    public static String getSet(boolean isNewSharedJedis, String key, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.getSet(key, value);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("getSet");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为键 key 储存的数字值加上 1
     * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long incr(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.incr(key);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("incr");
            sb.append("\t").append(key).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.INCR,isNewSharedJedis,key));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为键 key 储存的数字值加上增量 increment
     * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 INCRBY 命令
     * @param isNewSharedJedis
     * @param key
     * @param value
     * @return
     */
    public static Long incrBy(boolean isNewSharedJedis, String key, long value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.incrBy(key, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("incrBy");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.INCRBY,isNewSharedJedis,key,value));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为键 key 储存的值加上浮点数增量 increment
     * 如果键 key 不存在， 那么 INCRBYFLOAT 会先将键 key 的值设为 0 ， 然后再执行加法操作
     * @param isNewSharedJedis
     * @param key
     * @param value
     * @return
     */
    public static Double incrByFloat(boolean isNewSharedJedis, String key, double value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.incrByFloat(key, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("incrByFloat");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.INCRBYFLOAT,isNewSharedJedis,key,value));
            return 0D;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为键 key 储存的数字值减 1
     * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 DECR 操作
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long decr(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.decr(key);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("decr");
            sb.append("\t").append(key).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.DECR,isNewSharedJedis,key));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将键 key 储存的整数值减去减量 decrement
     * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 DECRBY 命令
     * @param isNewSharedJedis
     * @param key
     * @param value
     * @return
     */
    public static Long decrBy(boolean isNewSharedJedis, String key, long value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.decrBy(key, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("decrBy");
            sb.append("\t").append(key).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.DECRBY,isNewSharedJedis,key,value));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 哈希表操作 *************************************/

    /**
     * 返回哈希表中给定域的值
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @return
     */
    public static String hget(boolean isNewSharedJedis, String key, String field){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hget(key, field);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hget");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回哈希表 key 中，一个或多个给定域的值
     * @param isNewSharedJedis
     * @param key
     * @param fields
     * @return
     */
    public static List<String> hmget(boolean isNewSharedJedis, String key, String... fields){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hmget(key, fields);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmget");
            sb.append("\t").append(key).append("\t").append(fields).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new ArrayList<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回哈希表 key 中，所有的域和值
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Map<String, String> hgetAll(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hgetAll(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hgetAll");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashMap<>(16);
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将哈希表 hash 中域 field 的值设置为 value
     * 如果给定的哈希表并不存在， 那么一个新的哈希表将被创建并执行 HSET 操作
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @param value
     */
    public static void hset(boolean isNewSharedJedis, String key, String field, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.hset(key, field, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hset");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HSET,isNewSharedJedis,key,field,value));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 当且仅当域 field 尚未存在于哈希表的情况下， 将它的值设置为 value
     * 如果给定域已经存在于哈希表当中， 则放弃执行设置操作
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @param value
     */
    public static void hsetnx(boolean isNewSharedJedis, String key, String field, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.hsetnx(key, field, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hsetnx");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HSETNX,isNewSharedJedis,key,field,value));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 同时将多个 field-value (域-值)对设置到哈希表 key 中
     * @param isNewSharedJedis
     * @param key
     * @param hash
     */
    public static void hmset(boolean isNewSharedJedis, String key, Map<String, String> hash){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.hmset(key, hash);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmset");
            sb.append("\t").append(key).append("\t").append(hash).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HMSET,isNewSharedJedis,key,hash));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为 哈希表 field 增加 增量value值，并返回增加后的值（Long）
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hincrBy(boolean isNewSharedJedis, String key, String field, long value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hincrBy(key, field, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hincrBy");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HINCRBY,isNewSharedJedis,key,field,value));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为 哈希表 field 增加 增量value值，并返回增加后的值（Double）
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Double hincrByFloat(boolean isNewSharedJedis, String key, String field, double value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hincrByFloat(key, field, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hincrByFloat");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HINCRBYFLOAT,isNewSharedJedis,key,field,value));
            return 0D;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 判断哈希表中 field 是否存在
     * @param isNewSharedJedis
     * @param key
     * @param field
     * @return
     */
    public static boolean hexists(boolean isNewSharedJedis, String key, String field){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hexists(key, field);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hexists");
            sb.append("\t").append(key).append("\t").append(field).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return false;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 删除 哈希表 中 指定 key 的一个或多个 field
     * @param isNewSharedJedis
     * @param key
     * @param fields
     */
    public static void hdel(boolean isNewSharedJedis, String key, String... fields){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.hdel(key, fields);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hdel");
            sb.append("\t").append(key).append("\t").append(fields).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HDEL,isNewSharedJedis,key,fields));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回哈希表中 指定 key 的 所有 fields
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Set<String> hkeys(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hkeys(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hkeys");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回 哈希表 中指定 key 的 所有 values
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static List<String> hvals(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hvals(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hvals");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new ArrayList<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回 哈希表 key 中 field 数量
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long hlen(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.hlen(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hlen");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 管道操作 *************************************/

    /**
     * 批量获取 哈希表 多个key 相同对应 field 的数据 --> 管道
     * @param isNewSharedJedis
     * @param keys
     * @param fields
     * @return
     */
    public static List<Object> hmgetByPipeline(boolean isNewSharedJedis, List<String> keys, String... fields){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            ShardedJedisPipeline pipeline = sharedJedis.pipelined();
            for (String key : keys){
                pipeline.hmget(key,fields);
            }
            List<Object> result = pipeline.syncAndReturnAll();
            return result;
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmgetByPipeline");
            sb.append("\t").append(keys).append("\t").append(fields).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new ArrayList<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 批量 获取 哈希表 hmget 数据 -->管道
     * @param isNewSharedJedis
     * @param keyAndFields
     * @return
     */
    public static List<Object> hmgetByPipeline(boolean isNewSharedJedis, Map<String,String[]> keyAndFields){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            ShardedJedisPipeline pipeline = sharedJedis.pipelined();
            for (String key : keyAndFields.keySet()){
                pipeline.hmget(key, keyAndFields.get(key));
            }
            List<Object> result = pipeline.syncAndReturnAll();
            return result;
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmgetByPipeline");
            sb.append("\t").append(keyAndFields).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new ArrayList<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 批量执行哈希表 指定 key 的 incr/hmset操作 -->管道
     * 参数可为 null
     * @param isNewSharedJedis
     * @param key
     * @param field_vals_byincr
     * @param field_vals_byset
     */
    public static void hmsetByPipeline(boolean isNewSharedJedis, String key, Map<String,Long> field_vals_byincr,
                                       Map<String,String> field_vals_byset){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            ShardedJedisPipeline pipeline = sharedJedis.pipelined();
            if (null != field_vals_byincr){
                for (String field : field_vals_byincr.keySet()){
                    pipeline.hincrBy(key, field, field_vals_byincr.get(field));
                }
            }
            if (null != field_vals_byset){
                pipeline.hmset(key, field_vals_byset);
            }
            pipeline.sync();
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmsetByPipeline");
            sb.append("\t").append(key).append("\t").append(field_vals_byincr).append("\t")
                    .append(field_vals_byset).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HMSETBYPIPELINE,isNewSharedJedis,key,field_vals_byincr,field_vals_byset));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 批量执行哈希表 incr/hmset/expire操作 -->管道
     * 参数可为 null
     * @param isNewSharedJedis
     * @param key_field_vals_byincr
     * @param key_field_vals_byset
     * @param key_expire_times
     */
    public static void hmsetByPipeline(boolean isNewSharedJedis, Map<String,Map<String,Long>> key_field_vals_byincr,
                                       Map<String,Map<String,String>> key_field_vals_byset, Map<String,Long> key_expire_times){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            ShardedJedisPipeline pipeline = sharedJedis.pipelined();
            //自增Map
            if(null != key_field_vals_byincr && key_field_vals_byincr.size() > 0){
                for(String key : key_field_vals_byincr.keySet()){
                    Map<String,Long> field_vals = key_field_vals_byincr.get(key);
                    for(String field : field_vals.keySet()){
                        if (null != field_vals.get(field)) {
                            //对应 key 自增
                            pipeline.hincrBy(key, field, field_vals.get(field));
                        }
                    }
                }
            }
            //hmset 值 Map
            if(null != key_field_vals_byset && key_field_vals_byset.size() > 0){
                for(String key : key_field_vals_byset.keySet()){
                    Map<String,String> field_vals = key_field_vals_byset.get(key);
                    if (null != field_vals){
                        pipeline.hmset(key, field_vals);
                    }
                }
            }
            // 生存时间Map
            if(null != key_expire_times && key_expire_times.size() > 0){
                for(String key : key_expire_times.keySet()){
                    if (null != key_expire_times.get(key) && key_expire_times.get(key) > 0){
                        //设置生存时间
                        pipeline.expireAt(key, key_expire_times.get(key));
                    }
                }
            }
            pipeline.sync();
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("hmsetByPipeline");
            sb.append("\t").append(key_field_vals_byincr).append("\t").append(key_field_vals_byset).append("\t")
                    .append(key_expire_times).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.HMSETBYPIPELINE,isNewSharedJedis,key_field_vals_byincr,key_field_vals_byset,key_expire_times));
            log.error(sb.toString());
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 批量删除 key -->管道
     * @param isNewSharedJedis
     * @param keys
     */
    public static void delByPipeline(boolean isNewSharedJedis, List<String> keys){
        ShardedJedis sharedJedis = null;
        long delNum = 0L;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            ShardedJedisPipeline pipeline = sharedJedis.pipelined();
            if (null != keys){
                for (String key : keys){
                    pipeline.del(key);
                }
            }
            List<Object> result = pipeline.syncAndReturnAll();
            for (Object obj : result){
                if (null != obj){
                    delNum += Long.parseLong(obj.toString());
                }
            }
        } catch (Exception e) {
            String id = "d" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("delByPipeline");
            sb.append("\t").append(keys).append("\t").append(delNum).append("\t").append("del").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.DELBYPIPELINE,isNewSharedJedis,keys));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 列表操作 *************************************/

    /**
     * 将一个或多个 值 插入 key 列表的表头（从左边）
     * @param isNewSharedJedis
     * @param key
     * @param lists
     */
    public static void lpush(boolean isNewSharedJedis, String key, String... lists){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.lpush(key, lists);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lpush");
            sb.append("\t").append(key).append("\t").append(lists).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.LPUSH,isNewSharedJedis,key,lists));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将一个或多个 值 插入 key 列表的表尾（从右边）
     * @param isNewSharedJedis
     * @param key
     * @param lists
     */
    public static void rpush(boolean isNewSharedJedis, String key, String... lists){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.rpush(key, lists);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("rpush");
            sb.append("\t").append(key).append("\t").append(lists).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.RPUSH,isNewSharedJedis,key,lists));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除并返回列表 key 的头元素
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static String lpop(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.lpop(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lpop");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除并返回列表 key 的尾元素
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static String rpop(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.rpop(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("rpop");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值
     * count = 0 : 移除表中所有与 value 相等的值
     * @param isNewSharedJedis
     * @param key
     * @param count
     * @param value
     * @return
     */
    public static Long lrem(boolean isNewSharedJedis, String key, long count, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.lrem(key, count, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lrem");
            sb.append("\t").append(key).append("\t").append(count).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.LREM,isNewSharedJedis,key,count,value));
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回列表 key 的元素数量
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long llen(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.llen(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("llen");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回列表 key 中，下标为 index 的元素
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素，
     * -2 表示列表的倒数第二个元素，以此类推
     * @param isNewSharedJedis
     * @param key
     * @param index
     * @return
     */
    public static String lindex(boolean isNewSharedJedis, String key, long index){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.lindex(key, index);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lindex");
            sb.append("\t").append(key).append("\t").append(index).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后
     * 当 pivot 不存在于列表 key 时，不执行任何操作
     * 当 key 不存在时， key 被视为空列表，不执行任何操作
     * @param isNewSharedJedis
     * @param key
     * @param where
     * @param pivot
     * @param value
     */
    public static void linsert(boolean isNewSharedJedis, String key, BinaryClient.LIST_POSITION where, String pivot, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.linsert(key, where, pivot, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("linsert");
            sb.append("\t").append(key).append("\t").append(where).append("\t").append(pivot).append("\t").append(value)
                    .append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.LINSERT,isNewSharedJedis,key,where,pivot,value));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value
     * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误
     * @param isNewSharedJedis
     * @param key
     * @param index
     * @param value
     * @return
     */
    public static String lset(boolean isNewSharedJedis, String key, long index, String value){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.lset(key, index, value);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lset");
            sb.append("\t").append(key).append("\t").append(index).append("\t").append(value).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.LSET,isNewSharedJedis,key,index,value));
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推
     * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static List<String> lrange(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.lrange(key, start, end);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("lrange");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new ArrayList<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static String ltrim(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.ltrim(key, start, end);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("ltrim");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.LTRIM,isNewSharedJedis,key,start,end));
            return null;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 集合操作 *************************************/

    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
     * @param isNewSharedJedis
     * @param key
     * @param members
     */
    public static void sadd(boolean isNewSharedJedis, String key, String... members){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.sadd(key, members);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("sadd");
            sb.append("\t").append(key).append("\t").append(members).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.SADD,isNewSharedJedis,key,members));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 判断 member 元素是否集合 key 的成员
     * @param isNewSharedJedis
     * @param key
     * @param member
     * @return
     */
    public static boolean sismember(boolean isNewSharedJedis, String key, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.sismember(key, member);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("sismember");
            sb.append("\t").append(key).append("\t").append(member).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return false;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
     * @param isNewSharedJedis
     * @param key
     * @param members
     */
    public static void srem(boolean isNewSharedJedis, String key, String... members){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.srem(key, members);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("srem");
            sb.append("\t").append(key).append("\t").append(members).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.SREM,isNewSharedJedis,key,members));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回集合 key 的基数(集合中元素的数量)
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long scard(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.scard(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("scard");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回集合 key 中的所有成员
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Set<String> smembers(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.smembers(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("smembers");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**************************************** 有序集合操作 *************************************/

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，
     * 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上
     * @param isNewSharedJedis
     * @param key
     * @param score
     * @param member
     */
    public static void zadd(boolean isNewSharedJedis, String key, double score, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.zadd(key, score, member);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zadd");
            sb.append("\t").append(key).append("\t").append(score).append("\t").append(member).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZADD,isNewSharedJedis,key,score,member));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 将member 元素与 score 值映射表加入到有序集 key 当中
     * @param isNewSharedJedis
     * @param key
     * @param scoreMembers
     */
    public static void zadd(boolean isNewSharedJedis, String key, Map<String, Double> scoreMembers){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            sharedJedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zadd");
            sb.append("\t").append(key).append("\t").append(scoreMembers).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZADD,isNewSharedJedis,scoreMembers,key));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，成员 member 的 score 值
     * @param isNewSharedJedis
     * @param key
     * @param member
     * @return
     */
    public static Double zscore(boolean isNewSharedJedis, String key, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zscore(key ,member);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zscore");
            sb.append("\t").append(key).append("\t").append(member).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0D;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 为有序集 key 的成员 member 的 score 值加上增量 increment
     * @param isNewSharedJedis
     * @param key
     * @param score
     * @param member
     * @return
     */
    public static Double zincrby(boolean isNewSharedJedis, String key, double score, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zincrby(key, score, member);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zincrby");
            sb.append("\t").append(key).append("\t").append(member).append("\t").append(score).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZINCRBY,isNewSharedJedis,key,score,member));
            return 0D;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 的基数(元素数量)
     * @param isNewSharedJedis
     * @param key
     * @return
     */
    public static Long zcard(boolean isNewSharedJedis, String key){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zcard(key);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zcard");
            sb.append("\t").append(key).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Long zcount(boolean isNewSharedJedis, String key, double min, double max){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zcount(key, min, max);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zcount");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return 0L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递增(从小到大)来排序 升序
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，
     * 以 1 表示有序集第二个成员，以此类推
     * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrange(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrange(key, start, end);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrange");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，指定区间内的 有序集成员及其 score 值
     * 其中成员的位置按 score 值递增(从小到大)来排序 升序
     * 具有相同 score 值的成员按字典序(lexicographical order )来排列
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Tuple> zrangeWithScores(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrangeWithScores(key, start, end);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrangeWithScores");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，指定区间内的成员
     * 其中成员的位置按 score 值递减(从大到小)来排列  降序
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> zrevrange(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrange(key, start, end);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrange");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，指定区间内的 有序集成员及其 score 值
     * 其中成员的位置按 score 值递减(从大到小)来排列  降序
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Tuple> zrevrangeWithScores(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrangeWithScores");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 有序集成员按 score 值递增(从小到大)次序排列 升序
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<String> zrangeByScore(boolean isNewSharedJedis, String key, double min, double max){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrangeByScore");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的 有序集成员及其 score 值
     * 有序集成员按 score 值递增(从小到大)次序排列 升序
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Tuple> zrangeByScoreWithScores(boolean isNewSharedJedis, String key, double min, double max){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrangeByScoreWithScores");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 有序集成员按 score 值递增(从小到大)次序排列 升序
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，
     * 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public static Set<String> zrangeByScore(boolean isNewSharedJedis, String key, double min, double max,
                                            int offset, int count){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrangeByScore");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max)
                    .append("\t").append(offset).append("\t").append(count).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的 有序集成员及其 score 值
     * 有序集成员按 score 值递增(从小到大)次序排列 升序
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，
     * 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public static Set<Tuple> zrangeByScoreWithScores(boolean isNewSharedJedis, String key, double min, double max,
                                            int offset, int count){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrangeByScoreWithScores");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max)
                    .append("\t").append(offset).append("\t").append(count).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 有序集成员按 score 值递减(从大到小)次序排列 降序
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<String> zrevrangeByScore(boolean isNewSharedJedis, String key, double min, double max){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrangeByScore(key, min, max);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrangeByScore");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的 有序集成员及其 score 值
     * 有序集成员按 score 值递减(从大到小)次序排列 降序
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Tuple> zrevrangeByScoreWithScores(boolean isNewSharedJedis, String key, double min, double max){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrangeByScoreWithScores");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * 有序集成员按 score 值递减(从大到小)次序排列 降序
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，
     * 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public static Set<String> zrevrangeByScore(boolean isNewSharedJedis, String key, double min, double max,
                                            int offset, int count){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrangeByScore(key, min, max, offset, count);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrangeByScore");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max)
                    .append("\t").append(offset).append("\t").append(count).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的 有序集成员及其 score 值
     * 有序集成员按 score 值递减(从大到小)次序排列 降序
     * 可选的 LIMIT 参数指定返回结果的数量及区间(就像SQL中的 SELECT LIMIT offset, count )，
     * 注意当 offset 很大时，定位 offset 的操作可能需要遍历整个有序集
     * @param isNewSharedJedis
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @return
     */
    public static Set<Tuple> zrevrangeByScoreWithScores(boolean isNewSharedJedis, String key, double min, double max,
                                                     int offset, int count){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrangeByScoreWithScores(key, min, max, offset, count);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrangeByScoreWithScores");
            sb.append("\t").append(key).append("\t").append(min).append("\t").append(max)
                    .append("\t").append(offset).append("\t").append(count).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return new HashSet<>();
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中成员 member 的排名 其中有序集成员按 score 值递增(从小到大)顺序排列
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0
     * @param isNewSharedJedis
     * @param key
     * @param member
     * @return
     */
    public static Long zrank(boolean isNewSharedJedis, String key, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrank(key, member);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrank");
            sb.append("\t").append(key).append("\t").append(member).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 返回有序集 key 中成员 member 的排名 其中有序集成员按 score 值递减(从大到小)排序
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0
     * @param isNewSharedJedis
     * @param key
     * @param member
     * @return
     */
    public static Long zrevrank(boolean isNewSharedJedis, String key, String member){
        ShardedJedis sharedJedis = null;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            return sharedJedis.zrevrank(key, member);
        } catch (Exception e) {
            String id = "r" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrevrank");
            sb.append("\t").append(key).append("\t").append(member).append("\t").append("read").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            return -1L;
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     * @param isNewSharedJedis
     * @param key
     * @param members
     */
    public static void zrem(boolean isNewSharedJedis, String key, String... members){
        ShardedJedis sharedJedis = null;
        long remNum = 0L;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            remNum = sharedJedis.zrem(key, members);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zrem");
            sb.append("\t").append(key).append("\t").append(members).append("\t").append(remNum).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZREM,isNewSharedJedis,key,members));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员
     * 区间分别以下标参数 start 和 stop 指出，包含 start 和 stop 在内
     * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，
     * 以 1 表示有序集第二个成员，以此类推
     * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     */
    public static void zremrangeByRank(boolean isNewSharedJedis, String key, long start, long end){
        ShardedJedis sharedJedis = null;
        long remNum = 0L;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            remNum = sharedJedis.zremrangeByRank(key, start, end);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zremrangeByRank");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end)
                    .append("\t").append(remNum).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZREMRANGEBYRANK,isNewSharedJedis,key,start,end));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
     * @param isNewSharedJedis
     * @param key
     * @param start
     * @param end
     */
    public static void zremrangeByScore(boolean isNewSharedJedis, String key, double start, double end){
        ShardedJedis sharedJedis = null;
        long remNum = 0L;
        try {
            if (isNewSharedJedis){
                sharedJedis = RedisPool.getSharedJedisNew();
            } else {
                sharedJedis = RedisPool.getSharedJedis();
            }
            remNum = sharedJedis.zremrangeByScore(key, start, end);
        } catch (Exception e) {
            String id = "w" +(new Random().nextInt(10) + System.currentTimeMillis());
            StringBuffer sb = new StringBuffer("zremrangeByScore");
            sb.append("\t").append(key).append("\t").append(start).append("\t").append(end)
                    .append("\t").append(remNum).append("\t").append("write").append("\t").append(id)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
            RedisWriteBack.putWriteBackInfo(new WriteBackObj(RedisCommand.ZREMRANGEBYSCORE,isNewSharedJedis,key,start,end));
        } finally{
            if (null != sharedJedis) {
                RedisPool.closeSharedJedis(sharedJedis);
            }
        }
    }


}
