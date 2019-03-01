package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.common.adx.impl.AdxPositionImpl;
import com.songheng.dsp.datacenter.common.adx.impl.IpCityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 20:19
 * @description: 定时任务更新ADX广告缓存
 */
@Slf4j
@Component
public class UpdateAdxCache {

    /**
     * adxPositionImpl
     */
    @Autowired
    private AdxPositionImpl adxPositionImpl;
    /**
     * ipCityImpl
     */
    @Autowired
    private IpCityImpl ipCityImpl;

    /**
     * 定时任务更新ADX广告位置缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateAdxPosition(){
        log.debug("开始更新ADX广告位置缓存数据...");
        try {
            adxPositionImpl.updateAdPosition();
        } catch (Exception e) {
            log.error("更新ADX广告位置缓存数据失败\n{}",e);
        }
        log.debug("更新ADX广告位置缓存数据成功！");
    }

    /**
     * 定时任务更新IpCity缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateIpCity(){
        log.debug("开始更新IpCity缓存数据...");
        try {
            ipCityImpl.updateIpCityInfo();
        } catch (Exception e) {
            log.error("更新IpCity缓存数据失败\n{}",e);
        }
        log.debug("更新IpCity缓存数据成功！");
    }

}
