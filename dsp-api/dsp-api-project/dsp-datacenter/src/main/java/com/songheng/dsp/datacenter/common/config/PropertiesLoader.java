package com.songheng.dsp.datacenter.common.config;

import org.springframework.beans.BeansException;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/2/27 10:19
 * @description: 配置文件加载
 */
public class PropertiesLoader {

    /**
     * propertiesMap
     */
    private volatile static Map<String, String> propertiesMap = new ConcurrentHashMap<>(128);
    /**
     * 临时缓存 propertiesTmp
     */
    private static Map<String, String> propertiesTmp = null;

    /**
     * 解析Properties内容
     * @param props
     * @throws BeansException
     */
    private static void processProperties(Properties props) {
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                // PropertiesLoaderUtils的默认编码是ISO-8859-1
                propertiesTmp.put(keyStr, new String(props.getProperty(keyStr).getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载多个属性文件
     * @param propertyFileName
     */
    public static void loadAllProperties(String... propertyFileName) {
        //先加载至临时缓存，然后再全量替换
        propertiesTmp = new ConcurrentHashMap<>(128);
        try {
            for (String fileName : propertyFileName){
                Properties properties = PropertiesLoaderUtils.loadAllProperties(fileName);
                processProperties(properties);
            }
            if (propertiesTmp.size() > 0){
                propertiesMap = propertiesTmp;
                propertiesTmp = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据属性key获取value
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        return propertiesMap.get(name);
    }

    /**
     * 获取所有propertiesMap
     * @return
     */
    public static Map<String, String> getAllProperty() {
        return propertiesMap;
    }
}
