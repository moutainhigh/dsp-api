package com.songheng.dsp.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @description: 随机数工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-22 22:24
 **/
public class RandomUtils {

    private RandomUtils(){}

    private static final String CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
    /**
     * @description: 生成随机的UUID
     * @param isShort 是否生成不带 "-" 的 UUID
     * @return 随机ID
     **/

    public static String generateUUID(boolean isShort) {
        return UUID.randomUUID().toString().replace("-", isShort ? "" : "-");
    }
    /**
     * @description: 生成指定长度的数字随机数
     * @param perfix 前缀
     * @param length 长度
     * @return String
     */
    public static  String generateRandNumber (String perfix,int length)    {
        StringBuilder result =  new StringBuilder();
        Random rand = new Random();
        for(int i=0;i<length;i++){
            result.append(rand.nextInt(10));
        }
        return perfix+result.toString();
    }
    /**
     * @description: 生成相应长度的数字字母组合的随机数
     * @param perfix 前缀
     * @param size 长度
     * @return String
     */
    public static String generateRandString(String perfix,int size) {
        int length = CHARS.length();
        int p;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            p = rand.nextInt(length);
            String subChar = CHARS.substring(p,p+1);
            sb.append(subChar);
        }
        return perfix + sb.toString();
    }
    /**
     * @description: 生成当前时间戳
     * @param perfix 前缀
     * @return String
     */
    public static String generateTimeMillis(String perfix) {
        return perfix + System.currentTimeMillis();
    }

    /**
     *SimpleDateFormat 非线程安全,使用ThreadLocal 解决线程安全问题,以及频繁创建该对象的问题
     * **/
    private static ThreadLocal<DateFormat> threadLocalDateFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyMMddHHmmss");
        }
    };

    /**
     * @description: 生成当前时间(yyMMddHHmmss)+<code>randLength</code> 长度的随机数
     * @param perfix 前缀
     * @return String
     */

    public static String generateDateRand(String perfix,int randLength){
        String date =  threadLocalDateFormat.get().format(new Date());
        return perfix + date + generateRandNumber("",randLength);
    }
}
