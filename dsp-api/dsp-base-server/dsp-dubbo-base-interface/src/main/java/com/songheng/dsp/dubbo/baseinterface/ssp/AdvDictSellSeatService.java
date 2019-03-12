package com.songheng.dsp.dubbo.baseinterface.ssp;

import com.songheng.dsp.model.ssp.AdvDictSellSeat;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 10:20
 * @description: dsp售卖位置缓存接口
 */
public interface AdvDictSellSeatService {

    /**
     *  获取所有 sellSeatId映射 dsp售卖位置
     * @return
     */
    Map<String, AdvDictSellSeat> getSellSeatIdMap();

    /**
     * 获取所有 priority映射 dsp售卖位置
     * @return
     */
    Map<Integer, AdvDictSellSeat> getPriorityMap();

    /**
     * 获取指定 sellSeatId 映射 dsp售卖位置
     * @param sellSeatId
     * @return
     */
    AdvDictSellSeat getAdvDictSellSeat(String sellSeatId);

    /**
     * 获取指定 priority映射 dsp售卖位置
     * @param priority
     * @return
     */
    AdvDictSellSeat getAdvDictSellSeat(int priority);

}
