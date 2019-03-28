package com.songheng.dsp.model.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

/**
 * @description: 屏蔽策略
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 15:02
 **/
@Getter
public enum ShieldStrategy {
    UNKNOW("-1","UNKNOW","未知","未知策略"),
    positive("1","positive","正","正向策略"),
    opposition("0","opposition","反","反向策略");
    private String value;
    private String name;
    private String alias;
    private String desc;

    ShieldStrategy(String value,String name,String alias,String desc){
        this.value = value;
        this.name = name;
        this.alias = alias;
        this.desc = desc;
    }
    /**
     * 判断数据是否是正向策略
     * */
    public static boolean isPositive(String data){
        return positive.value.equalsIgnoreCase(data) ||
                positive.name.equalsIgnoreCase(data) ||
                positive.alias.equalsIgnoreCase(data);
    }

    /**
     * 获取正向策略的key
     * */
    public static String getPositiveKey(JSONObject json){
        if(null == json){
            return UNKNOW.name;
        }
        return json.containsKey(positive.value) ? positive.value :  (
                 json.containsKey(positive.name) ? positive.name : (
                   json.containsKey(positive.alias) ? positive.alias : UNKNOW.name
               )
         );
    }

    /**
     * 判断数据是否是反向策略
     * */
    public static boolean isOpposition(String data){
        return opposition.value.equalsIgnoreCase(data) ||
               opposition.name.equalsIgnoreCase(data) ||
               opposition.alias.equalsIgnoreCase(data);
    }



    /**
     * 判断数据是否是反向策略
     * */
    public static String getOppositionKey(JSONObject json){
        if(null == json){
            return UNKNOW.name;
        }
        return json.containsKey(opposition.value) ? opposition.value :  (
                json.containsKey(opposition.name) ? opposition.name : (
                        json.containsKey(opposition.alias) ? opposition.alias : UNKNOW.name
                )
        );
    }

    public static void main(String[] args) {
        System.out.println(isOpposition("反"));
    }
}
