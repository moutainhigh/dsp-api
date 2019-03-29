package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.shield.AdvDictShieldImpl;
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
     * advDictShieldImpl
     */
    @Autowired
    private AdvDictShieldImpl advDictShieldImpl;

    /**
     * 定时任务更新地域屏蔽信息
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateShieldArea(){
        log.debug("开始更新屏蔽信息缓存数据...");
        try {
            advDictShieldImpl.updateAdvDictShield();
        } catch (Exception e) {
            log.error("更新地域屏蔽信息缓存数据失败\n{}",e);
        }
        log.debug("更新屏蔽信息缓存数据成功！");
    }

}
