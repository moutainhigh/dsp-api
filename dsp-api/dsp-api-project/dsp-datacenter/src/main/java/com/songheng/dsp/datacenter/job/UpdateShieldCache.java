package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.shield.ShieldAreaImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author: luoshaobing
 * @date: 2019/3/19 20:16
 * @description: UpdateShieldCache
 */
@Slf4j
@Component
public class UpdateShieldCache {

    /**
     * shieldAreaImpl
     */
    @Autowired
    private ShieldAreaImpl shieldAreaImpl;

    /**
     * 定时任务更新地域屏蔽信息
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateShieldArea(){
        log.debug("开始更新屏蔽信息缓存数据...");
        try {
            shieldAreaImpl.updateShieldArea();
        } catch (Exception e) {
            log.error("更新地域屏蔽信息缓存数据失败\n{}",e);
        }
        try {
            shieldAreaImpl.updateSectorInfo();
        } catch (Exception e) {
            log.error("更新行业信息缓存数据失败\n{}",e);
        }
        try {
            shieldAreaImpl.updateMobileVendor();
        } catch (Exception e) {
            log.error("更新手机型号信息缓存数据失败\n{}",e);
        }
        try {
            shieldAreaImpl.updateOtherVendor();
        } catch (Exception e) {
            log.error("更新其他手机型号信息缓存数据失败\n{}",e);
        }

        log.debug("更新屏蔽信息缓存数据成功！");
    }

}
