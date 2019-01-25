package com.songheng.dsp.common;

import com.songheng.dsp.common.db.DruidConfiguration;
import com.songheng.dsp.common.redis.RedisPool;
import com.songheng.dsp.common.utils.PropertyPlaceholder;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 19:32
 * @description: 初始化加载资源
 */
public class InitLoadConf {

    /**
     * 初始化加载配置
     * @param projectName 项目名称 admethod/partner/dfpcitv/dspdatalog_B/dspdatalog_E
     * @param channel pc/wap
     */
    public static void init(String projectName, String channel){
        //加载配置文件
        PropertyPlaceholder.loadProperties("common-config.properties",
                "common-db-config.properties",
                "common-hbase-config.properties",
                "common-redis-config.properties");
        //加载druid数据源
        DruidConfiguration.initDataSource();
        //初始化redis pool
        RedisPool.initRedisPool(projectName, channel);
    }
}
