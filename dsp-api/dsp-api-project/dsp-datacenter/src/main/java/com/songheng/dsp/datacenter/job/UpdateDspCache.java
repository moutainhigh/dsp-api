package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.datacenter.ssp.AdvDictAdStyleImpl;
import com.songheng.dsp.datacenter.ssp.AdvDictSellSeatImpl;
import com.songheng.dsp.datacenter.ssp.AdvSspQidImpl;
import com.songheng.dsp.datacenter.ssp.AdvSspSlotImpl;
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
     * dfDspAdvCache
     */
    @Autowired
    private DfDspAdvCache dfDspAdvCache;

    /**
     * advDictAdStyle
     */
    @Autowired
    private AdvDictAdStyleImpl advDictAdStyle;

    /**
     * advDictSellSeat
     */
    @Autowired
    private AdvDictSellSeatImpl advDictSellSeat;

    /**
     * advSspQid
     */
    @Autowired
    private AdvSspQidImpl advSspQid;

    /**
     * advSspSlot
     */
    @Autowired
    private AdvSspSlotImpl advSspSlot;


    /**
     * 定时任务更新DSP广告缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateDspCache(){
        log.debug("开始更新DSP广告缓存数据...");
        try {
            //更新DfDsp广告池缓存
            dfDspAdvCache.updateDfDspAdv();
        } catch (Exception e){
            log.error("更新DFDSP广告池缓存数据失败\n{}", e);
        }
        try {
            //更新Ssp 广告位缓存，并映射slotId对应广告池
            advSspSlot.updateAdvSspSlot();
        } catch (Exception e){
            log.error("更新SSP广告位缓存数据失败\n{}", e);
        }
        try {
            //更新 渠道白名单缓存
            advSspQid.updateAdvSspQid();
        } catch (Exception e){
            log.error("更新渠道白名单缓存数据失败\n{}", e);
        }
        try {
            //更新 dsp售卖位置
            advDictSellSeat.updateAdvDictSellSeat();
        } catch (Exception e){
            log.error("更新DSP售卖位置缓存数据失败\n{}", e);
        }
        try {
            //更新 广告样式
            advDictAdStyle.updateAdvDictAdStyle();
        } catch (Exception e){
            log.error("更新广告样式缓存数据失败\n{}", e);
        }
        log.debug("更新DSP广告缓存数据成功！");
    }

}
