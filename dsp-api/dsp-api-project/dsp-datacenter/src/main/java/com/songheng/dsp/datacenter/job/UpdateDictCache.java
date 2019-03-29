package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.dict.AdvDictImpl;
import com.songheng.dsp.datacenter.dict.AdxPositionImpl;
import com.songheng.dsp.datacenter.dict.IpCityImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 16:09
 * @description: UpdateDictCache
 */
@Slf4j
@Component
public class UpdateDictCache {

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
     * advDictImpl
     */
    @Autowired
    private AdvDictImpl advDictImpl;

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

    /**
     * 定时任务更新AdvDict缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateAdvDict(){
        log.debug("开始更新AdvDict缓存数据...");
        try {
            advDictImpl.updateSectorInfo();
        } catch (Exception e) {
            log.error("更新行业信息缓存数据失败\n{}",e);
        }
        try {
            advDictImpl.updateMobileVendor();
        } catch (Exception e) {
            log.error("更新手机型号信息缓存数据失败\n{}",e);
        }
        try {
            advDictImpl.updateOtherVendor();
        } catch (Exception e) {
            log.error("更新其他手机型号信息缓存数据失败\n{}",e);
        }
        log.debug("更新AdvDict缓存数据成功！");
    }

}
