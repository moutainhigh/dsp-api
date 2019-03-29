package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.shield.ShieldAreaService;
import com.songheng.dsp.model.shield.ShieldArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 20:45
 * @description: ShieldArea DC远程RPC调用
 */
@Service
public class ShieldAreaInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * shieldAreaService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    ShieldAreaService shieldAreaService;

    /**
     * getShieldAreaList
     * @param terminal
     * @param site
     * @param qid
     * @return
     */
    @Cacheable(value ="shield_area", key = "#terminal.concat('_').concat(#site).concat('_').concat(#qid)", condition = "#result != null and #result.size() > 0")
    public List<ShieldArea> getShieldAreaList(String terminal, String site, String qid){
        return shieldAreaService.getShieldAreaList(terminal, site, qid);
    }

    /**
     * getShieldAreaList
     * @param terminal
     * @param shieldType
     * @return
     */
    @Cacheable(value ="shield_area", key = "#terminal.concat('_').concat(#shieldType)", condition = "#result != null and #result.size() > 0")
    public List<ShieldArea> getShieldAreaList(String terminal, String shieldType){
        return shieldAreaService.getShieldAreaList(terminal, shieldType);
    }

    /**
     * getSectorByCode
     * @param code
     * @return
     */
    @Cacheable(value ="shield_area", key = "#code", condition = "#result != null")
    public String getSectorByCode(String code){
        return shieldAreaService.getSectorByCode(code);
    }

    /**
     * getMobileVendorByName
     * @param name
     * @return
     */
    @Cacheable(value ="shield_area", key = "#name", condition = "#result != null")
    public String getMobileVendorByName(String name){
        return shieldAreaService.getMobileVendorByName(name);
    }

    /**
     * getOtherVendorByName
     * @param name
     * @return
     */
    @Cacheable(value ="shield_area", key = "#name", condition = "#result != null")
    public String getOtherVendorByName(String name){
        return shieldAreaService.getOtherVendorByName(name);
    }

    /**
     * 更新缓存
     */
    public void updateShieldArea(){
        Map<String, List<ShieldArea>> tmlSiteQidMap = shieldAreaService.getTmlSiteQidMap();
        Cache cache = cacheManager.getCache("shield_area");
        if (null != tmlSiteQidMap && tmlSiteQidMap.size() > 0){
            for (String key : tmlSiteQidMap.keySet()){
                cache.put(key, tmlSiteQidMap.get(key));
            }
        }

        Map<String, List<ShieldArea>> tmlShieldTypeMap = shieldAreaService.getTmlShieldTypeMap();
        if (null != tmlShieldTypeMap && tmlShieldTypeMap.size() > 0){
            for (String key : tmlShieldTypeMap.keySet()){
                cache.put(key, tmlShieldTypeMap.get(key));
            }
        }

        Map<String, String> sectorMap = shieldAreaService.getSectorMap();
        if (null != sectorMap && sectorMap.size() > 0){
            for (String key : sectorMap.keySet()){
                cache.put(key, sectorMap.get(key));
            }
        }

        Map<String, String> mobileVendorMap = shieldAreaService.getMobileVendorMap();
        if (null != mobileVendorMap && mobileVendorMap.size() > 0){
            for (String key : mobileVendorMap.keySet()){
                cache.put(key, mobileVendorMap.get(key));
            }
        }

        Map<String, String> otherVendorMap = shieldAreaService.getOtherVendorMap();
        if (null != otherVendorMap && otherVendorMap.size() > 0){
            for (String key : otherVendorMap.keySet()){
                cache.put(key, otherVendorMap.get(key));
            }
        }
    }
}
