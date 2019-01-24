package com.songheng.dsp.common;

import com.songheng.dsp.common.db.DruidConfiguration;
import com.songheng.dsp.common.utils.PropertyPlaceholder;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 19:32
 * @description:
 */
public class InitLoadConf {

    /**
     * 初始化加载配置
     */
    public static void init(){
        //加载配置文件
        PropertyPlaceholder.loadProperties("common-config.properties",
                "common-db-config.properties",
                "common-hbase-config.properties",
                "common-redis-config.properties");
        //加载druid数据源
        DruidConfiguration.getDataSource();
    }
}
