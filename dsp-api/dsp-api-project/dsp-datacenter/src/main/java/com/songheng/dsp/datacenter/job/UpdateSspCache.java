package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.datacenter.ssp.AdvDictAdStyleImpl;
import com.songheng.dsp.datacenter.ssp.AdvDictSellSeatImpl;
import com.songheng.dsp.datacenter.ssp.AdvSspFloorPriceImpl;
import com.songheng.dsp.datacenter.ssp.AdvSspQidImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/4/1 12:02
 * @description: UpdateSspCache
 */
@Slf4j
@Component
public class UpdateSspCache {

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
     * advSspFloorPriceImpl
     */
    @Autowired
    private AdvSspFloorPriceImpl advSspFloorPriceImpl;


    /**
     * 定时任务更新SSP缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 60 * 1000)
    public void updateSspCache(){
        log.debug("开始更新SSP缓存数据...");
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
        try {
            //更新 底价列表
            advSspFloorPriceImpl.updateAdvSspFloorPrice();
        } catch (Exception e){
            log.error("更新底价列表缓存数据失败\n{}", e);
        }
        log.debug("更新SSP缓存数据成功！");
    }
}
