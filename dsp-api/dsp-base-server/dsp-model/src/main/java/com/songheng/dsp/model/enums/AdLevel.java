package com.songheng.dsp.model.enums;

import lombok.Getter;

/**
 * @description:广告等级
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 14:14
 **/
@Getter
public enum AdLevel {
    UNKNOW(100,"UNKNOW"),
    A(0,"A"),
    B(-1,"B"),
    C(2,"C"),
    D(1,"D"),
    E(3,"E"),
    F(4,"F"),
    G(5,"G"),
    H(6,"H"),
    I(7,"I"),
    J(8,"J");
    private int levelValue;
    private String levelName;

    AdLevel(int levelValue,String levelName){
        this.levelValue = levelValue;
        this.levelName = levelName;
    }

    public static int getLevelValueByName(String name){
        for(AdLevel adLevel : AdLevel.values()){
            if(adLevel.levelName.equalsIgnoreCase(name)){
                return adLevel.levelValue;
            }
        }
        return AdLevel.UNKNOW.levelValue;
    }

    public static String getLevelNameByValue(int value){
        for(AdLevel adLevel : AdLevel.values()){
            if(adLevel.levelValue == value){
                return adLevel.levelName;
            }
        }
        return AdLevel.UNKNOW.levelName;
    }

    public static void main(String[] args) {
        System.out.println(getLevelNameByValue(1));
        System.out.println(getLevelValueByName("BB"));
    }

}
