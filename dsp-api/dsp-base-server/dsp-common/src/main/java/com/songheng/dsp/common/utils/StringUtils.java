package com.songheng.dsp.common.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.*;


/**
 * @description: 字符串工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-22 22:24
 */
public final class StringUtils {

    private StringUtils() {
    }


    /**
     * @Fields NUM_MAX_LENGTH : 数值类字符串的最大长度
     */
    private final static int NUM_MAX_LENGTH = 9;

    /**
     * @Fields DEFAULT_PAD_CHAR : 默认填充的字符
     */
    private final static char DEFAULT_PAD_CHAR = '0';

    /**
     * @Fields DEFAULT_PAD_LENGTH : 默认填充后的字符串长度
     */
    private final static int DEFAULT_PAD_LENGTH = 4;

    /**
     * @Fields SPLIt_MAP_CHAR : 字符串转map的分割字符串
     */
    private final static String STR_TO_MAP_SPLIT_CHAR = "=";

    /**
     * @description: 判断是否空字符串
     * @param string 需要验空的参数
     * @return 如果是空字符串则返回 <code>true</code> 否则返回 <code>false</code>
     */
    public static boolean isNullOrEmpty(String string){
        return Strings.isNullOrEmpty(string);
    }
    /**
     * @description: 判断是否非空字符串
     * @param string 需要验空的参数
     * @return 如果是空字符串则返回 <code>false</code> 否则返回 <code>ture</code>
     */
    public static boolean isNotNullOrEmpty(String string){
        return !isNullOrEmpty(string);
    }
    /**
     * @description: 判断是否空白字符串
     * @param cs 需要验空的参数
     * @return 如果是空字符串则返回 <code>true</code> 否则返回 <code>false</code>
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }
    /**
     * @description: 判断是否非空白字符串
     * @param cs 需要验空的参数
     * @return 如果是空字符串则返回 <code>false</code> 否则返回 <code>true</code>
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
    /**
     * @description: 判断是否无效字符串(null, " ", " null ", " NULL ", " nan ", " NaN ", " NAN ", " undefined " ...)
     * @param string 需要验空的参数
     * @return 如果是无效字符串则返回 <code>true</code> 否则返回 <code>false</code>
     */
    public static boolean isInvalidString(String string) {
        return (string == null || "".equals(string.trim()) || "null".equalsIgnoreCase(string)
                || "nan".equalsIgnoreCase(string) || "undefined".equalsIgnoreCase(string)
        );
    }

    /***
     * @description: 将 (null,"","null","NULL","nan","NaN","NAN"."undefined"...) 无效字符串替换成 <code>replaceString</code>
     * 一般用于传入参数的处理
     * @param oldString 原始字符串
     * @param replaceString 需要替换的字符串
     * @return 如果参数是无效字符串则返回replaceString, 否则返回oldString并且去掉前后两端空格
     * */
    public static String replaceInvalidString(String oldString, String replaceString) {
        return isInvalidString(oldString) ? replaceString : oldString.trim();
    }

    /**
     * @description: 判断这个字符是否是数值类型的
     * @param string 需要验证的字符串
     * @return 如果参数是数字则返回true, 否则返回false
     *
     */
    public static boolean isNumeric(String string) {
        if (isInvalidString(string) || string.length() >= NUM_MAX_LENGTH) {
            return false;
        }
        for (int i = string.length(); --i >= 0; ) {
            int chr = string.charAt(i);
            if (chr < 48 || chr > 57) {
                return false;
            }
        }
        return true;
    }

    /**
     * @description: 将数值类字符串转化为Integer
     * @param string 需要转换的字符串
     * @return 如果字符串是有效数值则返回对应数值 否则返回 -1
     **/
    public static Integer parseInteger(String string) {
        return isNumeric(string) ? Integer.parseInt(string) : -1;
    }

    /**
     * @description: 将字符串前面补充 <code>padChar</code> 字符 , 使整个字符长度为 <code>strLength</code>
     * @param oldStr    原始字符串
     * @param strLength 需要的字符串长度
     * @param padChar   需要填充的字符
     * @return 填充后的字符串，若原始字符串长度大于<code>strLength</code> 则返回原始字符串
     **/

    public static String padStart(String oldStr, Integer strLength, char padChar) {
        return Strings.padStart(oldStr, strLength, padChar);
    }

    /**
     * @description: 将字符串前面补充 <code>DEFAULT_PAD_CHAR</code> 字符 , 使整个字符长度为 <code>DEFAULT_PAD_LENGTH</code>
     * @param oldStr 原始字符串
     * @return 填充后的字符串，若原始字符串长度大于<code>strLength</code> 则返回原始字符串
     **/

    public static String padStart(String oldStr) {
        return padStart(oldStr, DEFAULT_PAD_LENGTH, DEFAULT_PAD_CHAR);
    }

    /**
     * @description: 将字符串后面补充 <code>padChar</code> 字符 , 使整个字符长度为 <code>strLength</code>
     * @param oldStr    原始字符串
     * @param strLength 需要的字符串长度
     * @param padChar   需要填充的字符
     * @return 填充后的字符串，若原始字符串长度大于<code>strLength</code> 则返回原始字符串
     **/

    public static String padEnd(String oldStr, Integer strLength, char padChar) {
        return Strings.padEnd(oldStr, strLength, padChar);
    }

    /**
     * @description: 将字符串前面补充 <code>DEFAULT_PAD_CHAR</code> 字符 , 使整个字符长度为 <code>DEFAULT_PAD_LENGTH</code>
     * @param oldStr 原始字符串
     * @return 填充后的字符串，若原始字符串长度大于<code>strLength</code> 则返回原始字符串
     **/

    public static String padEnd(String oldStr) {
        return padEnd(oldStr, DEFAULT_PAD_LENGTH, DEFAULT_PAD_CHAR);
    }


    /**
     * @description: 将带空格,",","，"的字符串分割转成list
     * @param strings 要分割的字符串
     * @return 分割好的字符串List集合
     **/
    public static List<String> strToList(String strings) {
        if(StringUtils.isBlank(strings)) {
            return new ArrayList<>();
        }
        return Splitter.onPattern("\\s+|，|,").omitEmptyStrings().trimResults().splitToList(strings);
    }
    /**
     * @description: 将k1=v1&k2=v2形式的字符串转化为map
     * @param strings 要分割的字符串
     * @return 分割好的字符串Map集合
     **/

    public static Map<String, String> strToMap(String strings){
        if(!strings.contains(STR_TO_MAP_SPLIT_CHAR)) {
            return new HashMap<>(0);
        }
        return Splitter.on("&").withKeyValueSeparator("=").split(strings);
    }
    /**
     * @description: 将map转化为k1=v1&k2=v2形式的字符串
     * @param maps 要转化的map
     * @return Map集合转化后的字符串
     **/

    public static String mapToStr(Map<String,String> maps){
        return Joiner.on("&").withKeyValueSeparator("=").join(maps);
    }
    /**
     * @description: 将集合转化为","分割的字符串
     * @param collection 要转化的集合
     * @return List集合转化后的字符串
     **/
    public static String collectionToStr(Collection collection){
        return Joiner.on(",").skipNulls().join(collection);
    }

    public static void main(String[] args) {
        //判断字符串是否为空
        System.out.println("isEmpty:" + StringUtils.isInvalidString(null));
        //将 null 或者 "" 字符串 使用默认值替换
        System.out.println("replaceInvalidString:" + StringUtils.replaceInvalidString(" aa aa bb ", "1") + ";");
        System.out.println("isNumeric:" + isNumeric(null));
        System.out.println("parseInteger:" + StringUtils.parseInteger("1298888"));
        System.out.println("padDefaultStart：" + StringUtils.padStart("11"));
        System.out.println("padStart:" + StringUtils.padStart("121", 4, '0'));
        System.out.println("padDefaultEnd:" + StringUtils.padEnd("11"));
        System.out.println("padEnd:" + StringUtils.padEnd("121", 4, '0'));
        List<String> list = StringUtils.strToList("a，b,c");
        System.out.println("collectionToStr:"+StringUtils.collectionToStr(list)+";");
        Map<String,String> maps = StringUtils.strToMap("k1=v1&k2=v2");
        System.out.println("strToMap:"+maps);
        System.out.println("mapToStr:"+mapToStr(maps));
        System.out.println(StringUtils.isNullOrEmpty("\t"));
        System.out.println(StringUtils.isNotNullOrEmpty("aaa"));
        System.out.println(isBlank("  \t"));

    }
}
