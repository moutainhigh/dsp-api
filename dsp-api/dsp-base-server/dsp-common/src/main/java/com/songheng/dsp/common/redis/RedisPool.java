package com.songheng.dsp.common.redis;

import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.DateUtils;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.StringUtils;
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
     * ShardedJedisPool_B 连接池
     */
    private static ShardedJedisPool shardedJedisPool_B;
    /**
     * shardedJedisPoolNew_B 新集群连接池
     */
    private static ShardedJedisPool shardedJedisPoolNew_B;

    /**
     * shardedJedisPool_E 连接池
     */
    private static ShardedJedisPool shardedJedisPool_E;

    /**
     * 获取 ShardedJedis
     * @return
     */
    public static ShardedJedis getSharedJedisByClusterB() {
        return shardedJedisPool_B.getResource();
    }

    /**
     * 获取redis连接池活动数
     * @return
     */
    public static int getNumActiveByClusterB(){
        return shardedJedisPool_B.getNumActive();
    }

    /**
     * 获取redis连接池等待数
     * @return
     */
    public static int getNumWaitersByClusterB(){
        return shardedJedisPool_B.getNumWaiters();
    }

    /**
     * 获取redis连接池空闲数
     * @return
     */
    public static int getNumIdleByClusterB(){
        return shardedJedisPool_B.getNumIdle();
    }

    /**
     * 获取 新集群 ShardedJedis
     * @return
     */
    public static ShardedJedis getSharedJedisNewByClusterB() {
        return shardedJedisPoolNew_B.getResource();
    }

    /**
     * 获取 新集群 redis连接池活动数
     * @return
     */
    public static int getNumActiveNewByClusterB(){
        return shardedJedisPoolNew_B.getNumActive();
    }

    /**
     * 获取 新集群 redis连接池等待数
     * @return
     */
    public static int getNumWaitersNewByClusterB(){
        return shardedJedisPoolNew_B.getNumWaiters();
    }

    /**
     * 获取 新集群 redis连接池空闲数
     * @return
     */
    public static int getNumIdleNewByClusterB(){
        return shardedJedisPoolNew_B.getNumIdle();
    }

    /**
     * 获取 ShardedJedis
     * @return
     */
    public static ShardedJedis getSharedJedisByClusterE() {
        return shardedJedisPool_E.getResource();
    }

    /**
     * 获取redis连接池活动数
     * @return
     */
    public static int getNumActiveByClusterE(){
        return shardedJedisPool_E.getNumActive();
    }

    /**
     * 获取redis连接池等待数
     * @return
     */
    public static int getNumWaitersByClusterE(){
        return shardedJedisPool_E.getNumWaiters();
    }

    /**
     * 获取redis连接池空闲数
     * @return
     */
    public static int getNumIdleByClusterE(){
        return shardedJedisPool_E.getNumIdle();
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
     * @param cluster CLUSTER_B/CLUSTER_E
     */
    public static void initRedisPool(String projectName, String[] cluster){
       synchronized (ShardedJedisPool.class) {
           initShardePool(projectName, cluster);
       }
    }
    /**
     * 初始化分片池
     * @param projectName 项目名称 admethod/partner/dfpcitv/dspdatalog_B/dspdatalog_E
     * @param cluster CLUSTER_B/CLUSTER_E
     */
    private static void initShardePool(String projectName, String[] cluster){
        TreeSet<String> redisNodes_B = new TreeSet<>();
        //新集群节点
        TreeSet<String> redisNewNodes_B = new TreeSet<>();
        TreeSet<String> redisNodes_E = new TreeSet<>();
        Map<String, String> redisConf = new HashMap<>(128);
        Map<String, String> confMap = PropertyPlaceholder.getPropertyMap();
        //获取 redis从节点信息
        for (String key : confMap.keySet()){
            if (cluster.length >= 2){
                if (key.startsWith("E_REDIS_NODE_")){
                    redisNodes_E.add(confMap.get(key));
                }
                if (key.startsWith("B_REDIS_NODE_")){
                    if (Integer.parseInt(key.substring(key.lastIndexOf("_")+1).trim()) <= 40){
                        redisNodes_B.add(confMap.get(key));
                    }
                    redisNewNodes_B.add(confMap.get(key));
                }
            } else {
                String clusterName = cluster[0];
                if (ClusterEnum.CLUSTER_E.name().equalsIgnoreCase(clusterName)){
                    if (key.startsWith("E_REDIS_NODE_")){
                        redisNodes_E.add(confMap.get(key));
                    }
                } else {
                    if (key.startsWith("B_REDIS_NODE_")){
                        if (Integer.parseInt(key.substring(key.lastIndexOf("_")+1).trim()) <= 40){
                            redisNodes_B.add(confMap.get(key));
                        }
                        redisNewNodes_B.add(confMap.get(key));
                    }
                }
            }
        }
        //获取各项目 redis pool 配置信息
        if (ProjectEnum.APP.getProjectName().equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("APP_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("APP_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("APP_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("APP_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("APP_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("APP_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("APP_REDIS_TIMEOUT"));
        } else if (ProjectEnum.H5.getProjectName().equalsIgnoreCase(projectName)) {
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("H5_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("H5_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("H5_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("H5_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("H5_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("H5_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("H5_REDIS_TIMEOUT"));
        } else if (ProjectEnum.DSPDATALOG.getProjectName().equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("LOG_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("LOG_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("LOG_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("LOG_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("LOG_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("LOG_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("LOG_REDIS_TIMEOUT"));
        } else if (ProjectEnum.PC.getProjectName().equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("PC_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("PC_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("PC_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("PC_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("PC_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("PC_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("PC_REDIS_TIMEOUT"));
        } else if (ProjectEnum.DATACENTER.getProjectName().equalsIgnoreCase(projectName)){
            redisConf.put("REDIS_MAX_TOTAL", confMap.get("DATA_REDIS_MAX_TOTAL"));
            redisConf.put("REDIS_MAX_IDEL", confMap.get("DATA_REDIS_MAX_IDEL"));
            redisConf.put("REDIS_MIN_IDEL", confMap.get("DATA_REDIS_MIN_IDEL"));
            redisConf.put("REDIS_MAX_WAIT_MILLIS", confMap.get("DATA_REDIS_MAX_WAIT_MILLIS"));
            redisConf.put("REDIS_TEST_ON_BORROW", confMap.get("DATA_REDIS_TEST_ON_BORROW"));
            redisConf.put("REDIS_TEST_ON_RETURN", confMap.get("DATA_REDIS_TEST_ON_RETURN"));
            redisConf.put("REDIS_TIMEOUT", confMap.get("DATA_REDIS_TIMEOUT"));
        }

        initPoolConfig(projectName, redisNodes_B, redisConf, redisNewNodes_B, redisNodes_E);
    }

    /**
     * 初始化redis池子配置
     * @param projectName
     * @param redisNodes_B
     * @param redisConf
     * @param redisNewNodes_B
     * @param redisNodes_E
     */
    private static void initPoolConfig(String projectName, TreeSet<String> redisNodes_B, Map<String, String> redisConf,
                                       TreeSet<String> redisNewNodes_B, TreeSet<String> redisNodes_E){
        System.out.println("初始化 【"+projectName+"】 redis池子前");
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

            List<JedisShardInfo> shardInfos_B = new ArrayList<>();
            List<JedisShardInfo> shardInfosNew_B = new ArrayList<>();
            if (StringUtils.isNotBlank(PropertyPlaceholder.getProperty("B_REDIS_MASTER_HOST"))){
                JedisShardInfo master_B = new JedisShardInfo(PropertyPlaceholder.getProperty("B_REDIS_MASTER_HOST"),
                        Integer.parseInt(PropertyPlaceholder.getProperty("B_REDIS_MASTER_PORT")),
                        PropertyPlaceholder.getProperty("B_REDIS_MASTER_NAME"));
                master_B.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                master_B.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfos_B.add(master_B);
                shardInfosNew_B.add(master_B);
            }
            for (String node : redisNodes_B){
                JedisShardInfo slaveInfo = new JedisShardInfo(node.split(":")[0],Integer.parseInt(node.split(":")[1]));
                slaveInfo.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                slaveInfo.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfos_B.add(slaveInfo);
            }
            for (String newNode : redisNewNodes_B){
                JedisShardInfo slaveInfoNew = new JedisShardInfo(newNode.split(":")[0],Integer.parseInt(newNode.split(":")[1]));
                slaveInfoNew.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                slaveInfoNew.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfosNew_B.add(slaveInfoNew);
            }
            if (shardInfos_B.size() > 0){
                shardedJedisPool_B = new ShardedJedisPool(config, shardInfos_B);
            }
            if (shardInfosNew_B.size() > 0){
                shardedJedisPoolNew_B = new ShardedJedisPool(config, shardInfosNew_B);
            }
            List<JedisShardInfo> shardInfos_E = new ArrayList<>();
            if (StringUtils.isNotBlank(PropertyPlaceholder.getProperty("E_REDIS_MASTER_HOST"))){
                JedisShardInfo master_E = new JedisShardInfo(PropertyPlaceholder.getProperty("E_REDIS_MASTER_HOST"),
                        Integer.parseInt(PropertyPlaceholder.getProperty("E_REDIS_MASTER_PORT")),
                        PropertyPlaceholder.getProperty("E_REDIS_MASTER_NAME"));
                master_E.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                master_E.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfos_E.add(master_E);
            }
            for (String node : redisNodes_E){
                JedisShardInfo slaveInfo = new JedisShardInfo(node.split(":")[0],Integer.parseInt(node.split(":")[1]));
                slaveInfo.setConnectionTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                slaveInfo.setSoTimeout(Integer.parseInt(redisConf.get("REDIS_TIMEOUT")));
                shardInfos_E.add(slaveInfo);
            }
            if (shardInfos_E.size() > 0){
                shardedJedisPool_E = new ShardedJedisPool(config, shardInfos_E);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("【"+ DateUtils.dateFormat(new Date(), DateUtils.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS)
                +"】\t【"+projectName+"】\t【初始化redis连接池】\t【slaves_B"+redisNodes_B+"】\t【newSlaves_B"+redisNewNodes_B+"】\t【"
                +PropertyPlaceholder.getProperty("B_REDIS_MASTER_HOST")+":"+PropertyPlaceholder.getProperty("B_REDIS_MASTER_PORT")+"】"
                +"\t【slaves_E"+redisNodes_E+"】\t【"+PropertyPlaceholder.getProperty("E_REDIS_MASTER_HOST")
                +":"+PropertyPlaceholder.getProperty("E_REDIS_MASTER_PORT")+"】");
    }

}
