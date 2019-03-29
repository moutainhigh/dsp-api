package com.songheng.dsp.dubbo.baseinterface.dict;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 18:01
 * @description: Adv字典缓存接口
 */
public interface AdvDictService {

    /**
     * getSectorMap
     * @return
     */
    Map<String, String> getSectorMap();

    /**
     * getSectorByCode
     * @param code
     * @return
     */
    String getSectorByCode(String code);

    /**
     * getMobileVendorMap
     * @return
     */
    Map<String, String> getMobileVendorMap();

    /**
     * getMobileVendorByName
     * @param name
     * @return
     */
    String getMobileVendorByName(String name);

    /**
     * getOtherVendorMap
     * @return
     */
    Map<String, String> getOtherVendorMap();

    /**
     * getOtherVendorByName
     * @param name
     * @return
     */
    String getOtherVendorByName(String name);

}
