package com.songheng.dsp.dubbo.baseinterface.ssp;

import com.songheng.dsp.model.ssp.AdvSspFloorPrice;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/4/1 10:53
 * @description: ssp底价列表缓存接口
 */
public interface AdvSspFloorPriceService {

    /**
     * 根据 slotId，qid获取底价列表
     * @param slotId
     * @param qid
     * @return
     */
    List<AdvSspFloorPrice> getFloorPriceListBySlotId(String slotId, String qid);

    /**
     * 获取底价列表
     * @return
     */
    Map<String, List<AdvSspFloorPrice>> getFloorPriceListMap();

    /**
     * 根据 slotId，pgnum, idx 获取底价列表
     * @param slotId
     * @param pgnum
     * @param idx
     * @return
     */
    AdvSspFloorPrice getFloorPriceById(String slotId, String pgnum, String idx);

    /**
     * 获取底价列表
     * @return
     */
    Map<String, AdvSspFloorPrice> getFloorPriceMap();

}
