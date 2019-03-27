package com.songheng.dsp.ssp.context;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.ssp.service.AdvSspSlotLocalService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @description: dubbo服务容器
 * @author: zhangshuai@021.com
 * @date: 2019-03-18 17:22
 **/
@Deprecated
public class RpcServiceContext<T> {


    private RpcServiceContext(){}

    /**
     *指定Spring扫描dubbo配置路径
     * */
    //@Configuration
    //@EnableDubbo(scanBasePackages = "com.songheng.dsp.ssp.service")
    //@PropertySource("classpath:/dubbo-consumer.properties")
    //@ComponentScan(value = {"com.songheng.dsp.ssp.service"})
    static class SspConsumerConfiguration {

    }
    /**
     * 初始化容器
     * */

    private static AnnotationConfigApplicationContext context;

    static{
        context = new AnnotationConfigApplicationContext(SspConsumerConfiguration.class);
        /**
         * 启动spring容器
         * */
        //context.start();
        System.out.println("容器启动完成:com.songheng.dsp.ssp.RpcServiceContext");

    }

    /**
     * 销毁容器
     * */
    public static void destoryContext(){
        if(null!=context) {
            context.destroy();
        }
    }
    /**
     * 获取本地服务
     * */
    public static<T> T getLocalService(Class clazz){
        return (T)context.getBean(clazz);
    }

}
