package com.songheng.dsp.model.enums;

/**
 * @author zhangshuai@021.com
 * 操作系统 Android/iOS/Windows/Mac OS
 * */
public enum OS {
    /**
     * Android
     * */
    Android("4","android"),
    /**
     * iOS
     * */
    iOS("5","ios"),

    /**
     * Windows
     * */
    Windows("7","Windows"),
    /**
     * Mac OS
     * */
    MacOS("8","Mac OS"),
    /**
     *Linux
     **/
    Linux("9","Linux"),
    /**
     * 未知
     * */
    Unknown("-1","Unknown")
    ;

    private String key;
    private String name;

    OS(String key,String name) {
        this.key = key;
        this.name = name;
    }
    /**
     * 判断是否是安卓系统
     * */
    public static boolean isAndroid(String osName){
        return OS.Android.name.equalsIgnoreCase(osName);
    }
    /**
     * 判断是否是iOS系统
     * */
    public static boolean isIOS(String osName){
        return OS.iOS.name.equalsIgnoreCase(osName);
    }
    /**
     * 判断是否是Windows
     * */
    public static boolean isWindows(String osName){
        return OS.Windows.name.equalsIgnoreCase(osName);
    }
    /**
     * 判断是否是 Mac OS系统
     * */
    public static boolean isMacOS(String osName){
        return OS.MacOS.name.equalsIgnoreCase(osName);
    }

    /**
     * 获取操作系统名称
     * */
    public static String getOsName(String name){
        for(OS os:OS.values()){
            if(os.name.equalsIgnoreCase(name)){
                return os.name;
            }
        }
        return Unknown.name;
    }
    /**
     * 获取操作系统的key
     * */
    public static String getOsKey(String name){
        for(OS os:OS.values()){
            if(os.name.equalsIgnoreCase(name)){
                return os.key;
            }
        }
        return Unknown.key;
    }
}
