package com.songheng.dsp.model.enums;

import lombok.Getter;

/**
 * @author zhangshuai@021.com
 * 开关
 * */
@Getter
public enum SwitchEnum {

    OFF("off"),
    ON("on");

    private String value;

    SwitchEnum(String value){
        this.value = value;
    }
    /**
     * 判断是否开启
     * */
    public static boolean isOn(String switchValue){
        return ON.getValue().equalsIgnoreCase(switchValue);
    }
    /**
     * 判断是否关闭
     * */
    public static boolean isOff(String switchValue){
        return !isOn(switchValue);
    }

    public static void main(String[] args) {
        System.out.println(SwitchEnum.isOn("off"));
        System.out.println(SwitchEnum.isOff("off"));

    }
}
