package com.songheng.dsp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/1/25 16:15
 * @description: JSON转换工具类
 */
public class FastJsonUtils {

    /**
     * 序列化设置
     */
    private static SerializeConfig mapping = new SerializeConfig();

    static {
        /**
         * 序列化时日期转换
         */
        mapping.put(java.util.Date.class,
                new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     *  toJSONString
     * @param obj Object
     * @return String
     */
    public static String toJSONString(Object obj){

        return JSON.toJSONString(obj,mapping);
    }

    /**
     * Json ==> Bean
     * @param json String
     * @param cls Class
     * @param <T> T
     * @return
     */
    public static <T> T json2Bean(String json,Class<?> cls){

        return (T) JSON.parseObject(json,cls);
    }

    /**
     * Json ==> List
     * @param str String
     * @param clazz Class
     * @return List
     */
    public static List<?> json2List(String str,Class<?> clazz){

        return JSON.parseArray(str,clazz);
    }

    /**
     * toJson
     * @param obj Object
     * @return JSON
     */
    public static JSON toJson(Object obj){

        return (JSON) JSON.toJSON(obj,mapping);
    }

    /**
     * Json ==> Map
     * @param json String
     * @return Map<?,?>
     */
    public static Map<?,?> json2Map(String json){

        return JSON.parseObject(json,new TypeReference<Map<?, ?>>(){});
    }

    /**
     * Bean ==> Map
     * @param bean Object
     * @return Map<?,?>
     */
    public static Map<?,?> bean2Map(Object bean){

        String jsonString = FastJsonUtils.toJSONString(bean);

        return JSONObject.parseObject(jsonString);

    }

    /**
     * Json ==> Object
     * @param json String
     * @param cls Class
     * @param <T> T
     * @return
     */
    public static <T> T jsonToObject(String json, Class<?> cls){

        return (T) JSONObject.parseObject(json,cls);

    }

    /**
     * 测试专用
     * @param args
     */
    public static void main(String[] args){

    }

}
