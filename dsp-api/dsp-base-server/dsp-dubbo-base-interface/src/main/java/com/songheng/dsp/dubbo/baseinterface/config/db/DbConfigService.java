package com.songheng.dsp.dubbo.baseinterface.config.db;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/4 14:27
 * @description: DbConfig
 */
public interface DbConfigService {

    /**
     * 获取DBCONFIG 属性值
     * @param terminal
     * @param key
     * @return
     */
    String getDbConfigValue(String terminal, String key);

    /**
     * 获取DBCONFIG指定终端对应的key value
     * @param terminal
     * @return
     */
    Map<String, String> getDbConfigMap(String terminal);

    /**
     * 获取所有DBCONFIG
     * @return
     */
    Map<String, String> getDbConfigMap();

}
