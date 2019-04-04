package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.common.utils.ZkClientUtils;
import com.songheng.dsp.datacenter.infosync.ZkWatcherAdvice;
import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.datacenter.ssp.*;
import com.songheng.dsp.datacenter.user.dsp.AdvertiserImpl;
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

    @Autowired
    private AdvertiserImpl advertiser;

    /**
     * advSspSlot
     */
    @Autowired
    private AdvSspSlotImpl advSspSlot;

    /**
     * zkWatcherAdvice
     */
    @Autowired
    private ZkWatcherAdvice zkWatcherAdvice;


    /**
     * 定时任务更新DSP广告缓存
     * 10秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 10 * 1000)
    public void updateDspCache(){
        log.debug("开始更新DSP广告缓存数据...");
        try {
            //更新DSP广告主信息
            advertiser.updateAdvertiser();
        } catch (Exception e){
            log.error("更新DSP广告主信息失败\n{}", e);
        }
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
        long startTs = System.currentTimeMillis();
        try {
            String nodeData = ZkClientUtils.readData(zkWatcherAdvice.getZkClient(), zkWatcherAdvice.getMinPath());
            long remoteTs = Long.parseLong(StringUtils.replaceInvalidString(nodeData, "0"));
            if ((startTs - remoteTs) > 10000L){
                //更新节点数据，通知监听该节点的所有客户端更新缓存
                ZkClientUtils.updateNode(zkWatcherAdvice.getZkClient(), zkWatcherAdvice.getMinPath(), startTs);
            }
        } catch (Exception e) {
            log.error("更新zk节点数据失败\tnode path ==>{}\n{}", zkWatcherAdvice.getMinPath(), e);
        }
        log.debug("更新DSP广告缓存数据成功！");
    }

}
