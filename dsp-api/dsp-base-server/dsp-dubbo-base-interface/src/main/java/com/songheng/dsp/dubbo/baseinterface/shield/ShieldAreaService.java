package com.songheng.dsp.dubbo.baseinterface.shield;

import com.songheng.dsp.model.shield.ShieldArea;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/19 16:38
 * @description: 地域屏蔽信息缓存接口
 */
public interface ShieldAreaService {

    /**
     * 获取所有 以terminal+site+qid 维度的 ShieldAreaMap
     * @return
     */
    Map<String, List<ShieldArea>> getTmlSiteQidMap();

    /**
     * 获取 以terminal+site+qid 维度的 List<ShieldArea>
     * @param terminal
     * @param site
     * @param qid
     * @return
     */
    List<ShieldArea> getShieldAreaList(String terminal, String site, String qid);

    /**
     * 获取所有 以terminal+shieldType 维度的 ShieldAreaMap
     * @return
     */
    Map<String, List<ShieldArea>> getTmlShieldTypeMap();

    /**
     * 获取 以terminal+shieldType 维度的 List<ShieldArea>
     * @param terminal
     * @param shieldType
     * @return
     */
    List<ShieldArea> getShieldAreaList(String terminal, String shieldType);

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
