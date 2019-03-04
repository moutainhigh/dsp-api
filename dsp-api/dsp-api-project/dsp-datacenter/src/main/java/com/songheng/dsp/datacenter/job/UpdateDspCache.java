package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.materiel.adx.OtherDspAdvImpl;
import com.songheng.dsp.datacenter.user.adx.DspUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 23:34
 * @description: UpdateDspCache
 */
@Slf4j
@Component
public class UpdateDspCache {

    /**
     * dspUserImpl
     */
    @Autowired
    private DspUserImpl dspUserImpl;

    /**
     * otherDspAdvImpl
     */
    @Autowired
    private OtherDspAdvImpl otherDspAdvImpl;

    /**
     * 定时任务更新DSP用户缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateDspUsers(){
        log.debug("开始更新DSP用户缓存数据...");
        try {
            dspUserImpl.updateDspUsers();
        } catch (Exception e) {
            log.error("更新DSP用户缓存数据失败\n{}",e);
        }
        log.debug("更新DSP用户缓存数据成功！");
    }

    /**
     * 定时任务更新第三方DSP广告缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateOtherDspAdvs(){
        log.debug("开始更新第三方DSP广告缓存数据...");
        try {
            otherDspAdvImpl.updateDspAdvs();
        } catch (Exception e) {
            log.error("更新第三方DSP广告缓存数据失败\n{}",e);
        }
        log.debug("更新第三方DSP广告缓存数据成功！");
    }

}
