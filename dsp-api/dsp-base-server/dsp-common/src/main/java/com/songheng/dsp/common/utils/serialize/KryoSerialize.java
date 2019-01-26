package com.songheng.dsp.common.utils.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.Base64;
import org.objenesis.strategy.StdInstantiatorStrategy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
/**
 * @description: 序列化工具
 * @author: zhangshuai@021.com
 * @date: 2019-01-25 15:50
 **/
@Slf4j
public class KryoSerialize {

    private KryoSerialize() {
    }

    private static final String DEFAULT_ENCODING = "UTF-8";

    /***
     *kryo的对象本身不是线程安全的 ,使用Threadlocal来保障线程安全
     * **/
    private static final ThreadLocal<Kryo> kryoLocal = new ThreadLocal<Kryo>() {
        @Override
        protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            /**
             * 默认值就是 true，不要改变这个配置 ,支持对象循环引用（否则会栈溢出）
             */
            kryo.setReferences(true);
            /**
             *  默认值就是 false,不要改变这个配置
             * **/
            kryo.setRegistrationRequired(false);
            //Fix the NPE bug when deserializing Collections.
            ((Kryo.DefaultInstantiatorStrategy) kryo.getInstantiatorStrategy())
                    .setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    /**
     * @description 获得当前线程的 Kryo 实例
     * @return 当前线程的 Kryo 实例
     */
    private static Kryo getInstance() {
        return kryoLocal.get();
    }

    /**
     * @description 将对象【及类型】序列化为字节数组
     * @param obj 任意对象
     * @param <T> 对象的类型
     * @return 序列化后的字节数组
     */
    public static <T> byte[] writeToByteArray(T obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            Kryo kryo = getInstance();
            kryo.writeClassAndObject(output, obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }catch (Exception e){
            log.error("[kryo:obj to bytes]\t{}",e);
            return null;
        }
    }

    /**
     * @description 将对象【及类型】序列化为 String 利用了 Base64 编码
     * @param obj 任意对象
     * @param <T> 对象的类型
     * @return 序列化后的字符串
     */
    public static <T> String writeToString(T obj) {
        try {
            return new String(Base64.encodeBase64(writeToByteArray(obj)), DEFAULT_ENCODING);
        } catch (Exception e) {
            log.error("[kryo:obj to string]\t{}",e);
            return null;
        }
    }

    /**
     * @description 将字节数组反序列化为原对象
     * @param byteArray writeToByteArray 方法序列化后的字节数组
     * @param <T>       原对象的类型
     * @return 原对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T readFromByteArray(byte[] byteArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = getInstance();
            return (T) kryo.readClassAndObject(input);
        }catch (Exception e){
            log.error("[kryo:bytes to obj]\t{}",e);
            return null;
        }
    }

    /**
     * @description 将 String 反序列化为原对象 利用了 Base64 编码
     * @param str writeToString 方法序列化后的字符串
     * @param <T> 原对象的类型
     * @return 原对象
     */
    public static <T> T readFromString(String str) {
        try {
            return readFromByteArray(Base64.decodeBase64(str.getBytes(DEFAULT_ENCODING)));
        } catch (Exception e) {
            log.error("[kryo:string to obj]\t{}",e);
            return null;
        }
    }

   /***
    * @description 克隆对象
    * @param obj 需要克隆的字符串
    * @return 原对象
    * */
    public static <T> T deepCopyObject(T obj) {
        byte[] bytes = writeToByteArray(obj);
        return readFromByteArray(bytes);
    }


    public static void main(String[] args) {
        List<Simple> cache = Lists.newArrayList(Simple.getSimple(1),Simple.getSimple(2),Simple.getSimple(3));
        List<Simple> temp = deepCopyObject(cache);
        for(Simple simple : temp){
            simple.setAge(100);
        }
        System.out.println(cache);
        System.out.println(temp);
    }
}
@Getter
@Setter
@ToString
class Simple implements java.io.Serializable {
    private String name;
    private int age;

    public static Simple getSimple(int age) {
        Simple simple = new Simple();
        simple.setAge(age);
        simple.setName("XiaoMing-"+age);
        return simple;
    }

}
