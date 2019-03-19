package com.songheng.dsp.datacenter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 15:57
 * @description: DataCenterApplication启动类
 */
@SpringBootApplication
@EnableDubboConfig
@EnableScheduling
public class DataCenterApplication {
    public static void main(String[] args){
        //设置dubbo使用slf4j来桥接，再由slf4j 转接到 log4j2 进行日志输出
        System.setProperty("dubbo.application.logger","slf4j");
        SpringApplication.run(DataCenterApplication.class, args);
    }

    @PostConstruct
    public void init(){
        InitLoadConf.init(ProjectEnum.DATACENTER);
    }
}
