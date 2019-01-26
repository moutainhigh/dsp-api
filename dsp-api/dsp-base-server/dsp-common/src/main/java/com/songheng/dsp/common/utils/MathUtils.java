package com.songheng.dsp.common.utils;

import com.google.common.math.DoubleMath;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @description: 算法工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-24 20:01
 **/
public final class MathUtils {

    /**
     * 精度舍入模式枚举
     * **/
    public enum  ROUND_MODE_ENUM {
        /**
         * 默认模式:四舍五入
         * **/
        ROUND_DEFAULT(BigDecimal.ROUND_HALF_UP),
        /**
         * 四舍五入
         * **/
        ROUND_HALF_UP(BigDecimal.ROUND_HALF_UP),
        /**
         * 截断模式 ：该模式永远不会增加被操作的数的值
         * **/
        ROUND_DOWN(BigDecimal.ROUND_DOWN),
        /**
         * 向上取整模式：该模式永远不会减少被操作的数的值
         * 在精度最后一位加一个单位
         * 1.666 ->1.67 1.011->1.02  1.010->1.01
         * **/
        ROUND_UP(BigDecimal.ROUND_UP),
        /**
         * 如果为正数，行为和round_up一样，
         * 如果为负数，行为和round_down一样
         * **/
        ROUND_CEILING(BigDecimal.ROUND_CEILING),
        /**
         * 朝负无穷方向round 如果为正数，行为和round_down一样，
         * 如果为负数，行为和round_up一样
         * **/
        ROUND_FLOOR(BigDecimal.ROUND_FLOOR),
        /**
         * 向下近似
         * 遇到.5的情况时往下近似,例: 1.5 ->;1 注：1.51->2
         * **/
        ROUND_HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),
        /**
         * 如果舍弃部分左边的数字为奇数，则作ROUND_HALF_UP
         * 如果它为偶数，则作ROUND_HALF_DOWN
         * **/
        ROUND_HALF_EVEN(BigDecimal.ROUND_HALF_EVEN);
        private final int value;

        ROUND_MODE_ENUM(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    /**
     * 默认保留的小数位数
     * **/
    private static final int DEF_DIV_SCALE = 3;

    private MathUtils(){}
    /**
     * 精度舍入默认模式
     * */
    public final static int ROUND_MODE = ROUND_MODE_ENUM.ROUND_DEFAULT.getValue();

    /***
     * 验证精度输入是否正确
     * @param scale 精度,小数点后需要保留的小数
     * */
    private static void verificationScale(int scale){
        if(scale<0){
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
    }

    /**
     * 加法
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        return add(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 加法
     * @param v1 被加数
     * @param v2 加数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的和
     */
    public static double add(double v1,double v2,int scale){
        verificationScale(scale);
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale,ROUND_MODE);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale,ROUND_MODE);
        double result = b1.add(b2).setScale(scale,ROUND_MODE).doubleValue();
        return result;
    }
    /**
     * 减法
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        return sub(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 减法
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2,int scale){
        verificationScale(scale);
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale,ROUND_MODE);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale,ROUND_MODE);
        return b1.subtract(b2).setScale(scale,ROUND_MODE).doubleValue();
    }
    /**
     * 乘法
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2){
        return mul(v1,v2,DEF_DIV_SCALE);
    }

    /**
     * 乘法
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1,double v2,int scale){
        verificationScale(scale);
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale,ROUND_MODE);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale,ROUND_MODE);
        return b1.multiply(b2).setScale(scale,ROUND_MODE).doubleValue();
    }
    /**
     * 除数
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1,double v2){
        return div(v1,v2,DEF_DIV_SCALE);
    }
    /**
     * 除法
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1,double v2,int scale){
        verificationScale(scale);
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(scale,ROUND_MODE);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(scale,ROUND_MODE);
        return b1.divide(b2,scale,ROUND_MODE).doubleValue();
    }
    /**
     * 将double指定精度
     * @param v 处理的数字
     * @return 结果
     */
    public static double round(double v){
        return round(v,DEF_DIV_SCALE);
    }
    /**
     * 将double指定精度
     * @param v 需要处理的数字
     * @param scale 小数点后保留几位
     */
    public static double round(double v,int scale){
        verificationScale(scale);
        BigDecimal b = new BigDecimal(Double.toString(v)).setScale(scale,ROUND_MODE);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,ROUND_MODE).doubleValue();
    }
    /**
     * 取模
     * @retrun 非负数。
     * **/
    public static int mode(int x, int m){
        return ((x%m)+m)%m;
    }
    /**
     * double ---> long
     * **/
    public static long doubleToLong(double v){
        return DoubleMath.roundToLong(v, RoundingMode.HALF_UP);
    }
    /**
     * double ---> int
     * **/
    public static int doubleToInt(double v){
        return DoubleMath.roundToInt(v,RoundingMode.HALF_UP);
    }
    public static void main(String[] args) {
        System.out.println(mul(add(1.745,0),1000));
        System.out.println(sub(7.091,7.342221));
        System.out.println(mul(1.5,0.12121121212121212));
        System.out.println(div(10,3));
        System.out.println(div(10,3));
        System.out.println(round(10.2125512));
        System.out.println(new BigDecimal(1.745).multiply(new BigDecimal(100)).longValue());
        System.out.println(mul(1.745,100));
        System.out.println(mul(0.3276,100));
        System.out.println(mode(-7,4));
        System.out.println(
                doubleToLong(sub(200000000,
                        100000)));
        System.out.println(doubleToLong(1.745));
        System.out.println((long)1.745);
    }
}
