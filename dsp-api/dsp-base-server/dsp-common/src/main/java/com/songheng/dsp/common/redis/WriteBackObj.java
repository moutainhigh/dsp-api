package com.songheng.dsp.common.redis;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import redis.clients.jedis.BinaryClient;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/19 13:41
 * @description: redis重写对象
 */
@Getter
@Setter
@ToString
public class WriteBackObj {

    /**
     * redis写操作类型 枚举
     */
    private RedisCommand command;
    /**
     * 是否redis新集群 true 新集群 false 老集群
     */
    private boolean isNewSharedJedis;
    /**
     * redis key
     */
    private String key;
    /**
     * keys
     */
    private List<String> keys;
    /**
     * 秒
     */
    private int seconds;
    /**
     * value 值（String）
     */
    private String valueS;
    /**
     * value 值（Long）
     */
    private long valueL;
    /**
     * value 值（Double）
     */
    private double valueD;
    /**
     * 哈希 域
     */
    private String field;
    /**
     * 哈希 field --> value
     */
    private Map<String, String> hash;
    /**
     * fields 数组
     */
    private String[] valueArray;
    /**
     * 哈希 incr field --> value
     */
    private Map<String,Long> field_vals_byincr;
    /**
     * 哈希 hmset field --> value
     */
    private Map<String,String> field_vals_byset;
    /**
     * 哈希 key incr field --> value
     */
    private Map<String,Map<String,Long>> key_field_vals_byincr;
    /**
     * 哈希 key hmset field --> value
     */
    private Map<String,Map<String,String>> key_field_vals_byset;
    /**
     * expire key --> value
     */
    private Map<String,Long> key_expire_times;
    /**
     *  BinaryClient.LIST_POSITION BEFORE/AFTER
     */
    private BinaryClient.LIST_POSITION where;
    /**
     * pivot
     */
    private String pivot;
    /**
     * 下标起始（Long）
     */
    private long startL;
    /**
     * 下标结束（Long）
     */
    private long endL;
    /**
     * 有序集合 member --> score
     */
    private Map<String, Double> scoreMembers;
    /**
     * 下标起始（Double）
     */
    private double startD;
    /**
     * 下标结束（Double）
     */
    private double endD;


    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, int seconds){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.seconds = seconds;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, long valueL){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueL = valueL;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, String valueS){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueS = valueS;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, double valueD){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueD = valueD;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, String field, String valueS){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.field = field;
        this.valueS = valueS;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, String field, double valueD){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.field = field;
        this.valueD = valueD;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, String field, long valueL){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.field = field;
        this.valueL = valueL;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, Map<String, String> hash){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.hash = hash;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, String... valueArray){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueArray = valueArray;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, Map<String,Long> field_vals_byincr,
                        Map<String,String> field_vals_byset){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.field_vals_byincr = field_vals_byincr;
        this.field_vals_byset = field_vals_byset;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, Map<String,Map<String,Long>> key_field_vals_byincr,
                        Map<String,Map<String,String>> key_field_vals_byset, Map<String,Long> key_expire_times){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key_field_vals_byincr = key_field_vals_byincr;
        this.key_field_vals_byset = key_field_vals_byset;
        this.key_expire_times = key_expire_times;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, long valueL, String valueS){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueL = valueL;
        this.valueS = valueS;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, BinaryClient.LIST_POSITION where, String pivot, String valueS){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.where = where;
        this.pivot = pivot;
        this.valueS = valueS;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, long start, long end){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.startL = start;
        this.endL = end;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, double valueD, String valueS){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.valueD = valueD;
        this.valueS = valueS;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, Map<String, Double> scoreMembers, String key){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.scoreMembers = scoreMembers;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, String key, double start, double end){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.key = key;
        this.startD = start;
        this.endD = end;
    }

    public WriteBackObj(RedisCommand command, boolean isNewSharedJedis, List<String> keys){
        this.command = command;
        this.isNewSharedJedis = isNewSharedJedis;
        this.keys = keys;
    }
}
