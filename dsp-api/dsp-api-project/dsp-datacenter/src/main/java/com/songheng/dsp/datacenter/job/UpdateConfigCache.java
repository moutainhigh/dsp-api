package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.common.config.PropertiesLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/2/27 10:42
 * @description: 定时任务更新配置文件
 */
@Slf4j
@Component
public class UpdateConfigCache {

    /**
     * 定时任务更新配置文件缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateAllConfig(){
        log.debug("开始更新配置文件缓存数据...");
        try {
            PropertiesLoader.loadAllProperties("conf/adxpro.properties");
        } catch (Exception e) {
            log.error("更新配置文件缓存数据失败\t\n{}",e);
        }
        log.debug("更新配置文件缓存数据成功！");
    }

}
