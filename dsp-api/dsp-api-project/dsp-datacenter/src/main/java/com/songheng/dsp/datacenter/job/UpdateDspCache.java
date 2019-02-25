package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.common.adx.impl.DspUserImpl;
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

    @Autowired
    private DspUserImpl dspUserImpl;

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
            log.error("更新DSP用户缓存数据失败\t{}",e);
        }
        log.debug("更新DSP用户缓存数据成功！");
    }


}
