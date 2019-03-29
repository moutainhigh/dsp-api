package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvDictSellSeatService;
import com.songheng.dsp.model.ssp.AdvDictSellSeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 21:02
 * @description: AdvDictSellSeat DC远程RPC调用
 */
@Service
public class AdvDictSellSeatInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advDictSellSeatService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvDictSellSeatService advDictSellSeatService;

    /**
     * getAdvDictSellSeat
     * @param sellSeatId
     * @return
     */
    @Cacheable(value ="adv_dict_sell_seat", key = "#sellSeatId", condition = "#result != null")
    public AdvDictSellSeat getAdvDictSellSeat(String sellSeatId){
        return advDictSellSeatService.getAdvDictSellSeat(sellSeatId);
    }

    /**
     * getAdvDictSellSeat
     * @param priority
     * @return
     */
    @Cacheable(value ="adv_dict_sell_seat", key = "#priority", condition = "#result != null")
    public AdvDictSellSeat getAdvDictSellSeat(int priority){
        return advDictSellSeatService.getAdvDictSellSeat(priority);
    }

    /**
     * 更新缓存
     */
    public void updateAdvDictSellSeat(){
        Map<String, AdvDictSellSeat> sellSeatIdMap = advDictSellSeatService.getSellSeatIdMap();
        Cache cache = cacheManager.getCache("adv_dict_sell_seat");
        if (null != sellSeatIdMap && sellSeatIdMap.size() > 0){
            for (String key : sellSeatIdMap.keySet()){
                cache.put(key, sellSeatIdMap.get(key));
            }
        }

        Map<Integer, AdvDictSellSeat> priorityMap = advDictSellSeatService.getPriorityMap();
        if (null != priorityMap && priorityMap.size() > 0){
            for (Integer key : priorityMap.keySet()){
                cache.put(key, priorityMap.get(key));
            }
        }
    }
}
