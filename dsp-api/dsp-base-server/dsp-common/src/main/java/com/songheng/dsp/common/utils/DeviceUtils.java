package com.songheng.dsp.common.utils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * @Description: 获取设备信息工具类
 * @Package com.songheng.dsp.utils
 * @Title: DeviceUtils
 * @author: zhangshuai@021.com
 * @date: 2019-01-23 13:32
 * @version 1.0
 */
public final class DeviceUtils {

    private DeviceUtils(){}
    /**
     * @Description: 获取操作系统名称
     * @param ua UserAgent
     * @return Android,iOS,Mac OS X,Windows,Unknown
     */

    public static String getOsName(String ua){
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent.getOperatingSystem().getGroup().getName();
    }
    /**
     * @Description: 获取设备类型
     * @param ua UserAgent
     * @return Mobile,Computer,Unknown
     */
    public static String getDeviceType(String ua){
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent.getOperatingSystem().getDeviceType().getName();
    }

    /**
     * @Description: 获取浏览器名称
     * @param ua UserAgent
     * @return Chrome,Outlook,Firefox,Safari,Opera,Unknown
     */
    public static String getBrowserName(String ua){
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent.getBrowser().getGroup().getName();
    }
    /**
     * @Description: 获取浏览器版本
     * @param ua UserAgent
     * @return 71.0.3578.98,10.0,Unknown
     */
    public static String getBrowserVersion(String ua){
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent.getBrowserVersion().getVersion();
    }
    /**
     * @Description: 获取UserAgentId
     * @param ua UserAgent
     * @return 251989860,Unknown
     */
    public static int getUserAgentId(String ua){
        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
        return userAgent.getId();
    }

    public static void main(String[] args) {
        String macua =     "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
        String androidua = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36";
        String iosua     = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1";
        String winua     = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36";
        System.out.println(getOsName(winua));
        System.out.println(getDeviceType(macua));
        System.out.println(getBrowserName(androidua));
        System.out.println(getBrowserVersion(androidua));
        System.out.println(getUserAgentId(iosua));
    }
}
