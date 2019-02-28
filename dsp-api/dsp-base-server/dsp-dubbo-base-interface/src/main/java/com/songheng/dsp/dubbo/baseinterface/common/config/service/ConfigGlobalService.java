package com.songheng.dsp.dubbo.baseinterface.common.config.service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/28 20:01
 * @description: 全局配置缓存
 */
public interface ConfigGlobalService {

    /**
     * 获取配置文件属性值
     * @param name
     * @return
     */
    String getProperty(String name);

    /**
     * 获取所有配置文件key value
     * @return
     */
    Map<String, String> getAllProperty();

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

}
