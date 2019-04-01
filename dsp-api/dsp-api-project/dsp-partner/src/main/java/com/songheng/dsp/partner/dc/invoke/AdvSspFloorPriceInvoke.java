package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspFloorPriceService;
import com.songheng.dsp.model.ssp.AdvSspFloorPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/4/1 13:49
 * @description: AdvSspFloorPrice DC远程RPC调用
 */
@Service
public class AdvSspFloorPriceInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advSspFloorPriceService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvSspFloorPriceService advSspFloorPriceService;

    /**
     * 根据 slotId，qid获取底价列表
     * @param slotId
     * @param qid
     * @return
     */
    @Cacheable(value ="adv_ssp_floor_price", key = "#slotId.concat('_').concat(#qid)", condition = "#result != null and #result.size() > 0")
    public List<AdvSspFloorPrice> getFloorPriceListBySlotId(String slotId, String qid){
        return advSspFloorPriceService.getFloorPriceListBySlotId(slotId, qid);
    }

    /**
     * 根据 slotId，pgnum, idx 获取底价列表
     * @param slotId
     * @param pgnum
     * @param idx
     * @return
     */
    @Cacheable(value ="adv_ssp_floor_price", key = "#slotId.concat('_').concat(#pgnum).concat('_').concat(idx)", condition = "#result != null and #result.size() > 0")
    public AdvSspFloorPrice getFloorPriceById(String slotId, String pgnum, String idx){
        return advSspFloorPriceService.getFloorPriceById(slotId, pgnum, idx);
    }

    /**
     * 更新缓存
     */
    public void updateAdvSspFloorPrice(){
        Map<String, List<AdvSspFloorPrice>> floorPriceListMap = advSspFloorPriceService.getFloorPriceListMap();
        Cache cache = cacheManager.getCache("adv_ssp_floor_price");
        if (null != floorPriceListMap && floorPriceListMap.size() > 0){
            for (String key : floorPriceListMap.keySet()){
                cache.put(key, floorPriceListMap.get(key));
            }
        }

        Map<String, AdvSspFloorPrice> floorPriceMap = advSspFloorPriceService.getFloorPriceMap();
        if (null != floorPriceMap && floorPriceMap.size() > 0){
            for (String key : floorPriceMap.keySet()){
                cache.put(key, floorPriceMap.get(key));
            }
        }
    }
}
