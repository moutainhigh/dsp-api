package com.songheng.dsp.dubbo.baseinterface.dict;

import com.songheng.dsp.model.dict.AdPosition;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 11:58
 * @description: ADX广告位置缓存接口
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

    /**
     * 获取所有 List<AdPosition>
     * @return
     */
    Map<String, List<AdPosition>> getAdPositionListMap();

    /**
     * 获取所有 AdPosition
     * @return
     */
    Map<String, AdPosition> getAdPositionMap();

}
