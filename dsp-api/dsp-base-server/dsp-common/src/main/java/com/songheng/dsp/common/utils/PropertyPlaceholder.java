package com.songheng.dsp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 15:45
 * @description:初始化配置文件加载
 */
public class PropertyPlaceholder {
    /**
     * 属性key value映射
     */
    private static Map<String,String> propertyMap = new HashMap<>(128);


    /**
     * 解析属性文件
     * @param props
     */
    private static void processProperties(Properties props) {
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            try {
                propertyMap.put(keyStr, new String(props.getProperty(keyStr).getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  加载属性文件，可加载多个
     * @param propertyFileName
     */
    public static void loadProperties(String... propertyFileName) {
        try {
            for (String fileName : propertyFileName){
                Properties properties = new Properties();
                InputStream input = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(fileName);
                properties.load(input);
                processProperties(properties);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查是否存在key
     * @param _key
     * @return
     */
    public static boolean chkProperty(String _key) {
        return propertyMap.containsKey(_key);
    }

    /**
     * static method for accessing context properties
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        return propertyMap.get(name);
    }

    /**
     * 获取所有属性
     * @return
     */
    public static Map<String,String> getPropertyMap(){
        return propertyMap;
    }

}
