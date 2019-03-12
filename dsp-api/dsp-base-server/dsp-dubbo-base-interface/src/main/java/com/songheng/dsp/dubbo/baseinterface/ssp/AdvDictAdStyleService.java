package com.songheng.dsp.dubbo.baseinterface.ssp;

import com.songheng.dsp.model.ssp.AdvDictAdStyle;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 10:19
 * @description: 广告样式缓存接口
 */
public interface AdvDictAdStyleService {

    /**
     * 获取所有 styleId 映射 广告样式信息
     * @return
     */
    Map<String, AdvDictAdStyle> getAdvDictAdStyleMap();

    /**
     * 获取指定 styleId 映射 广告样式信息
     * @param styleId
     * @return
     */
    AdvDictAdStyle getAdvDictAdStyle(String styleId);

}
