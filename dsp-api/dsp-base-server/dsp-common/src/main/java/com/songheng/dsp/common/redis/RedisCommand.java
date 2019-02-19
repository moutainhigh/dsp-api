package com.songheng.dsp.common.redis;

/**
 * @author: luoshaobing
 * @date: 2019/2/19 16:05
 * @description: redis 写操作异常回写 枚举
 */
public enum RedisCommand {
    EXPIRE,
    EXPIREAT,
    PEXPIRE,
    PEXPIREAT,
    PERSIST,
    DEL,
    SET,
    SETNX,
    INCR,
    INCRBY,
    INCRBYFLOAT,
    DECR,
    DECRBY,
    HSET,
    HSETNX,
    HMSET,
    HINCRBY,
    HINCRBYFLOAT,
    HDEL,
    HMSETBYPIPELINE,
    DELBYPIPELINE,
    LPUSH,
    RPUSH,
    LREM,
    LINSERT,
    LSET,
    LTRIM,
    SADD,
    SREM,
    ZADD,
    ZINCRBY,
    ZREM,
    ZREMRANGEBYRANK,
    ZREMRANGEBYSCORE
}
