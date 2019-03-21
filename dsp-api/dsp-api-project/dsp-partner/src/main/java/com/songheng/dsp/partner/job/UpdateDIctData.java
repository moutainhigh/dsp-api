package com.songheng.dsp.partner.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description: 更新字典数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 15:01
 **/
@Slf4j
public class UpdateDIctData {
    /**
     * 定时任务更新广告缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateDictCache(){
        System.out.println("定时更新内存数据");
    }
}
