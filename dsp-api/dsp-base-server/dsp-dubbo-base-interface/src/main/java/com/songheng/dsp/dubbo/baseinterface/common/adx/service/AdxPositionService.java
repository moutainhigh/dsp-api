package com.songheng.dsp.dubbo.baseinterface.common.adx.service;

import com.songheng.dsp.model.adx.AdPosition;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 11:58
 * @description: DSP广告位置缓存接口
 */
public interface AdxPositionService {

    /**
     * 根据终端 app/h5/pc 获取 List<AdPosition>
     * @param terminal
     * @return
     */
    List<AdPosition> getAdPositionList(String terminal);

    /**
     * 根据终端 app/h5/pc, locationId 获取 AdPosition
     * @param terminal
     * @param locationId
     * @return
     */
    AdPosition getAdPositionById(String terminal, String locationId);

    /**
     * 根据终端 app/h5/pc, locationName 获取 AdPosition
     * @param terminal
     * @param locationName
     * @return
     */
    AdPosition getAdPositionByName(String terminal, String locationName);

}
