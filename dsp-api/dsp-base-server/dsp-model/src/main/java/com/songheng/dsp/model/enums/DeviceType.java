package com.songheng.dsp.model.enums;

/**
 * @author zhangshuai@021.com
 * 设备类型 手机/电脑
 * */
public enum DeviceType {
    /**
     * 手机
     * */
    Mobile("1","Mobile"),
    /**
     * 电脑
     * */
    Computer("2","Computer"),
    /**
     * 未知
     * */
    Unknown("-1","Unknown")
    ;

    private String key;
    private String name;

    DeviceType(String key, String name) {
        this.key = key;
        this.name = name;
    }
    /**
     * 判断是否是手机
     * */
    public static boolean isMobile(String deviceType){
        return DeviceType.Mobile.name.equalsIgnoreCase(deviceType);
    }
    /**
     * 判断是否是电脑
     * */
    public static boolean isComputer(String deviceType){
        return DeviceType.Computer.name.equalsIgnoreCase(deviceType);
    }

    /**
     * 获取设备类型名称
     * */
    public static String getDeviceTypeName(String deviceTypeName){
        for(DeviceType deviceType : DeviceType.values()){
            if(deviceType.name.equalsIgnoreCase(deviceTypeName)){
                return deviceType.name;
            }
        }
        return Unknown.name;
    }
    /**
     * 获取操作系统的key
     * */
    public static String getDeviceTypeKey(String deviceTypeName){
        for(DeviceType deviceType : DeviceType.values()){
            if(deviceType.name.equalsIgnoreCase(deviceTypeName)){
                return deviceType.key;
            }
        }
        return Unknown.key;
    }

    public static void main(String[] args) {
        System.out.println(getDeviceTypeKey("computer1"));
        System.out.println(getDeviceTypeName("mobile"));
        System.out.println(isComputer("mobile"));
        System.out.println(isMobile("mobile"));
    }
}
