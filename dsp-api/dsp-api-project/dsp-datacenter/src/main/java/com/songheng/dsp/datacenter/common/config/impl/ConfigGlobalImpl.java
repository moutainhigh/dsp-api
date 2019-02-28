package com.songheng.dsp.datacenter.common.config.impl;

import com.songheng.dsp.datacenter.common.config.DbConfigLoader;
import com.songheng.dsp.datacenter.common.config.PropertiesLoader;
import com.songheng.dsp.dubbo.baseinterface.common.config.service.ConfigGlobalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/28 20:12
 * @description: 全局配置缓存接口
 */
@Slf4j
@Component
public class ConfigGlobalImpl implements ConfigGlobalService{

    /**
     * 根据属性key获取value
     * @param name
     * @return
     */
    @Override
    public String getProperty(String name) {
        return PropertiesLoader.getProperty(name);
    }

    /**
     * 获取所有propertiesMap
     * @return
     */
    @Override
    public Map<String, String> getAllProperty() {
        return PropertiesLoader.getAllProperty();
    }

    /**
     * 获取指定 key 的 value
     * @param terminal
     * @param dspkey
     * @return
     */
    @Override
    public String getDbConfigValue(String terminal, String dspkey) {
        String key = String.format("%s%s%s", terminal, "_", dspkey);
        return DbConfigLoader.getDbConfigValue(key);
    }

    /**
     * 获取指定 key 的 Map<String, String>
     * key : app/h5/pc/logs
     * @param terminal
     * @return
     */
    @Override
    public Map<String, String> getDbConfigMap(String terminal) {
        return DbConfigLoader.getDbConfigMap(terminal);
    }
}
