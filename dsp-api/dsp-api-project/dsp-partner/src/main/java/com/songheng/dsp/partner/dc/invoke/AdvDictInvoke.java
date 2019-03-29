package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.dict.AdvDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 18:18
 * @description: AdvDict DC远程RPC调用
 */
@Service
public class AdvDictInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advDictService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvDictService advDictService;

    /**
     * getSectorByCode
     * @param code
     * @return
     */
    @Cacheable(value ="adv_dict", key = "#code", condition = "#result != null")
    public String getSectorByCode(String code){
        return advDictService.getSectorByCode(code);
    }

    /**
     * getMobileVendorByName
     * @param name
     * @return
     */
    @Cacheable(value ="adv_dict", key = "#name", condition = "#result != null")
    public String getMobileVendorByName(String name){
        return advDictService.getMobileVendorByName(name);
    }

    /**
     * getOtherVendorByName
     * @param name
     * @return
     */
    @Cacheable(value ="adv_dict", key = "#name", condition = "#result != null")
    public String getOtherVendorByName(String name){
        return advDictService.getOtherVendorByName(name);
    }

    /**
     * 更新缓存
     */
    public void updateAdvDict(){
        Cache cache = cacheManager.getCache("adv_dict");
        Map<String, String> sectorMap = advDictService.getSectorMap();
        if (null != sectorMap && sectorMap.size() > 0){
            for (String key : sectorMap.keySet()){
                cache.put(key, sectorMap.get(key));
            }
        }

        Map<String, String> mobileVendorMap = advDictService.getMobileVendorMap();
        if (null != mobileVendorMap && mobileVendorMap.size() > 0){
            for (String key : mobileVendorMap.keySet()){
                cache.put(key, mobileVendorMap.get(key));
            }
        }

        Map<String, String> otherVendorMap = advDictService.getOtherVendorMap();
        if (null != otherVendorMap && otherVendorMap.size() > 0){
            for (String key : otherVendorMap.keySet()){
                cache.put(key, otherVendorMap.get(key));
            }
        }
    }
}
