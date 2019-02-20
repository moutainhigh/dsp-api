package com.songheng.dsp.common.redis;


import com.songheng.dsp.common.utils.DateUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 21:49
 * @description: redis 分片连接池配置
 */
public class RedisPool {

    /**
     * ShardedJedisPool连接池
     */
    private static ShardedJedisPool shardedJedisPool;
    /**
     * shardedJedisPoolNew 新集群连接池
     */
    private static ShardedJedisPool shardedJedisPoolNew;


    /**
     * 获取 ShardedJedis
     * @return
     */
    public static ShardedJedis getSharedJedis() {
        return shardedJedisPool.getResource();
    }

    /**
     * 获取redis连接池活动数
     * @return
     */
    public static int getNumActive(){
        return shardedJedisPool.getNumActive();
    }

    /**
     * 获取redis连接池等待数
     * @return
     */
    public static int getNumWaiters(){
        return shardedJedisPool.getNumWaiters();
    }

    /**
     * 获取redis连接池空闲数
     * @return
     */
    public static int getNumIdle(){
        return shardedJedisPool.getNumIdle();
    }

    /**
     * 获取 新集群 ShardedJedis
     * @return
     */
    public static ShardedJedis getSharedJedisNew() {
        return shardedJedisPoolNew.getResource();
    }

    /**
     * 获取 新集群 redis连接池活动数
     * @return
     */
    public static int getNumActiveNew(){
        return shardedJedisPoolNew.getNumActive();
    }

    /**
     * 获取 新集群 redis连接池等待数
     * @return
     */
    public static int getNumWaitersNew(){
        return shardedJedisPoolNew.getNumWaiters();
    }

    /**
     * 获取 新集群 redis连接池空闲数
     * @return
     */
    public static int getNumIdleNew(){
        return shardedJedisPoolNew.getNumIdle();
    }

    /**
     * 关闭 ShardedJedis
     * @param shardedJedis
     */
    public static void closeSharedJedis(ShardedJedis shardedJedis){
        if (null != shardedJedis){
            shardedJedis.close();
        }
    }

    /**
     * 初始化redis pool
     * @param projectName
     * @param channel
     */
    public static void initRedisPool(String projectName, String channel){
       if (null == shardedJedisPool){
           synchronized (ShardedJedisPool.class) {
               if (null == shardedJedisPool){
                   initShardePool(projectName, channel);
               }
           }
       }
    }
    /**
     * 初始化分片池
     * @param projectName 项目名称 admethod/partner/dfpcitv/dspdatalog_B/dspdatalog_E
     * @param channel pc/wap
     */
    private static void initShardePool(String projectName, String channel){
        TreeSet<String> redisNodes = new TreeSet<>();
        //新集群节点
        TreeSet<String> redisNewNodes = new TreeSet<>();
        Map<String, String> redisConf = new HashMap<>(16);
        Map<String, String> confMap = PropertyPlaceholder.getPropertyMap();
        //获取pc/wap redis从节点信息
        for (String key : confMap.keySet()){
            if ("pc".equalsIgnoreCase(channel)){
                if (key.startsWith("PC_REDIS_NODE_")){
                    redisNodes.add(confMap.get(key));
                }
            } else {//默认 wap
                if (key.startsWith("REDIS_NODE_")){
                    for (int i = 0; i <= 40; i++) {
                        if (key.equals("REDIS_NODE_" + i)) {
                            redisNodes.add(confMap.get(key));
                            break;
                        }
                    }
                    redisNewNodes.add(confMap.get(key));
                }
            }
        }
        //获取pc/wap redis 主节点信息
        if ("pc".equalsIgnoreCase(channel)){
            redisConf.put("REDIS_MASTER_NAME", confMap.get("PC_REDIS_MASTER_NAME"));
            redisConf.put("REDIS_MASTER_HOST", confMap.get("PC_REDIS_MASTER_HOST"));
            redisConf.put("REDIS_MASTER_PORT", confMap.get("PC_REDIS_MASTER_PORT"));
        } else {//默认wap
            redisConf.put("REDIS_MASTER_NAME", confMap.get("REDIS_MASTER_NAME"));
            redisConf.put("REDIS_MASTER_HOST", confMap.get("REDIS_MASTER_HOST"));
            redisConf.put("REDIS_MASTER_PORT", confMap.get("REDIS_MASTER_PORT"));
        }
        //获取各项目 redis pool 配置信息
        if ("admethod".equalsIgnoreCase(projectName) || "partner".equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("REDIS_TIMEOUT"));
        } else if ("dspdatalog_B".equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("LOG_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("LOG_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("LOG_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("LOG_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("LOG_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("LOG_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("LOG_REDIS_TIMEOUT"));
        } else if ("dfpcitv".equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("PC_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("PC_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("PC_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("PC_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("PC_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("PC_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("PC_REDIS_TIMEOUT"));
        } else if ("dspdatalog_E".equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("LOG_PC_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("LOG_PC_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("LOG_PC_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("LOG_PC_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("LOG_PC_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("LOG_PC_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("LOG_PC_REDIS_TIMEOUT"));
        }

        initPoolConfig(projectName, redisNodes, redisConf, redisNewNodes);
    }

    /**
     * 初始化redis池子配置
     * @param projectName
     * @param redisNodes
     * @param redisConf
     * @param redisNewNodes
     */
    private static void initPoolConfig(String projectName, TreeSet<String> redisNodes, Map<String, String> redisConf,
                                       TreeSet<String> redisNewNodes){
        if (redisNodes.size() == 0 || redisConf.size() == 0){
            return;
        }
        System.out.println("初始化 "+projectName+" redis池子前");
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            //资源池中最大连接数
            config.setMaxTotal(Integer.parseInt(redisConf.get("REDIS_MAX_TOTAL")));
            //资源池允许最大空闲的连接数
            config.setMaxIdle(Integer.parseInt(redisConf.get("REDIS_MAX_IDEL")));
            //资源池确保最少空闲的连接数
            config.setMinIdle(Integer.parseInt(redisConf.get("REDIS_MIN_IDEL")));
            //当池内没有返回对象时，最大等待时间
            config.setMaxWaitMillis(Long.parseLong(redisConf.get("REDIS_MAX_WAIT_MILLIS")));
            //向资源池借用连接时是否做连接有效性检测(ping)，无效连接会被移除，业务量很大时候建议设置为false(多一次ping的开销)。
            config.setTestOnBorrow(Boolean.parseBoolean(redisConf.get("REDIS_TEST_ON_BORROW")));
            //向资源池归还连接时是否做连接有效性检测(ping)，无效连接会被移除，业务量很大时候建议设置为false(多一次ping的开销)。
            config.setTestOnReturn(Boolean.parseBoolean(redisConf.get("REDIS_TEST_ON_RETURN")));
            //是否开启空闲资源监测,建议开启
            config.setTestWhileIdle(true);
            List<JedisShardInfo> shardInfos = new ArrayList<>();
            List<JedisShardInfo> shardInfosNew = new ArrayList<>();
            JedisShardInfo master = new JedisShardInfo(redisConf.get("REDIS_MASTER_HOST"),
                    Integer.parseInt(redisConf.get("REDIS_MASTER_PORT")), redisConf.get("REDIS_MASTER_NAME"));
            master.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
            master.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));

            shardInfos.add(master);
            shardInfosNew.add(master);
            for (String node : redisNodes){
                JedisShardInfo slaveInfo = new JedisShardInfo(node.split(":")[0],Integer.parseInt(node.split(":")[1]));
                slaveInfo.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                slaveInfo.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfos.add(slaveInfo);
            }
            shardedJedisPool = new ShardedJedisPool(config, shardInfos);

            for (String newNode : redisNewNodes){
                JedisShardInfo slaveInfoNew = new JedisShardInfo(newNode.split(":")[0],Integer.parseInt(newNode.split(":")[1]));
                slaveInfoNew.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                slaveInfoNew.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfosNew.add(slaveInfoNew);
            }
            shardedJedisPoolNew = new ShardedJedisPool(config, shardInfosNew);
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("【"+ DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)
                +"】\t【"+projectName+"】\t【初始化redis连接池】\t【oldSlaves"+redisNodes+"】\t【newSlaves"+redisNewNodes+"】\t【"
                +redisConf.get("REDIS_MASTER_HOST")+":"+redisConf.get("REDIS_MASTER_PORT")+"】");
    }



}
