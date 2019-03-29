package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.materiel.ExtendNews;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 21:20
 * @description: AdvSspSlot DC远程RPC调用
 */
@Service
public class AdvSspSlotInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advSspSlotService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvSspSlotService advSspSlotService;

    /**
     * getExtendNewsSet
     * @param slotId
     * @return
     */
    @Cacheable(value ="adv_ssp_slot", key = "#slotId", condition = "#result != null and #result.size() > 0")
    public Set<ExtendNews> getExtendNewsSet(String slotId){
        return advSspSlotService.getExtendNewsSet(slotId);
    }

    /**
     * getAdvSspSlotById
     * @param slotId
     * @return
     */
    @Cacheable(value ="adv_ssp_slot", key = "#slotId", condition = "#result != null")
    public AdvSspSlot getAdvSspSlotById(String slotId){
        return advSspSlotService.getAdvSspSlotById(slotId);
    }

    /**
     * 更新缓存
     */
    public void updateAdvSspSlot(){
        Map<String, Set<ExtendNews>> extendNewsMap = advSspSlotService.getExtendNewsMap();
        Cache cache = cacheManager.getCache("adv_ssp_slot");
        if (null != extendNewsMap && extendNewsMap.size() > 0){
            for (String key : extendNewsMap.keySet()){
                cache.put(key, extendNewsMap.get(key));
            }
        }

        Map<String, AdvSspSlot> advSspSlotMap = advSspSlotService.getAdvSspSlotMap();
        if (null != advSspSlotMap && advSspSlotMap.size() > 0){
            for (String key : advSspSlotMap.keySet()){
                cache.put(key, advSspSlotMap.get(key));
            }
        }
    }
}
