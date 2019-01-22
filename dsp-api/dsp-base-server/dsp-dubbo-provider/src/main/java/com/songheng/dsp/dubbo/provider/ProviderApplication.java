package com.songheng.dsp.dubbo.provider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 13:56
 * @description: ProviderApplication启动类
 */
@SpringBootApplication
@EnableDubboConfig
public class ProviderApplication {
    public static void main(String[] args){
        SpringApplication.run(ProviderApplication.class, args);
    }
}
