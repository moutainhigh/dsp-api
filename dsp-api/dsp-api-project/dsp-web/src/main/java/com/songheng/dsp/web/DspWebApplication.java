package com.songheng.dsp.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author: zhangshuai@021.com
 * @date: 2019/3/13 12:57
 * @description: DspWebApplication启动类
 */
@SpringBootApplication
public class DspWebApplication {

    public static void main(String[] args){
        SpringApplication.run(DspWebApplication.class, args);
    }

    @PostConstruct
    public void init(){
        //InitLoadConf.init(ProjectEnum.DSPWEB);
    }
}
