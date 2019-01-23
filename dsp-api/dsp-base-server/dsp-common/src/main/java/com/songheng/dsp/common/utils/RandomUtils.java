package com.songheng.dsp.common.utils;

import java.util.UUID;

/**
 *
 * @Title: StringUtils
 * @Package com.songheng.dsp.utils
 * @Description: 字符串工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-22 22:24
 * @version V1.0
 **/
public class RandomUtils {
    /**
     * @Description: 生成随机的UUID
     * @param isShort 是否生成不带 "-" 的 UUID
     * @return 随机ID
     **/

    public static String generateUUID(boolean isShort) {
        return UUID.randomUUID().toString().replace("-", isShort ? "" : "-");
    }

    public static String generate(){
        return "";
    }

    public static void main(String[] args) {
        System.out.println("generateUUID:" + RandomUtils.generateUUID(true));
    }
}
