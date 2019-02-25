package com.songheng.dsp.datacenter;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 15:57
 * @description: DataCenterApplication启动类
 */
@SpringBootApplication
@EnableDubboConfig
public class DataCenterApplication {
    public static void main(String[] args){
        SpringApplication.run(DataCenterApplication.class, args);
    }

    @PostConstruct
    public void init(){
        InitLoadConf.init(ProjectEnum.DATACENTER);
    }
}
