package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvDictAdStyleService;
import com.songheng.dsp.model.ssp.AdvDictAdStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 20:58
 * @description: AdvDictAdStyle DC远程RPC调用
 */
@Service
public class AdvDictAdStyleInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advDictAdStyleService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvDictAdStyleService advDictAdStyleService;

    /**
     * getAdvDictAdStyle
     * @param styleId
     * @return
     */
    @Cacheable(value ="adv_dict_adstyle", key = "#styleId", condition = "#result != null")
    public AdvDictAdStyle getAdvDictAdStyle(String styleId){
        return advDictAdStyleService.getAdvDictAdStyle(styleId);
    }

    /**
     * 更新缓存
     */
    public void updateAdvDictAdStyle(){
        Map<String, AdvDictAdStyle> advDictAdStyleMap = advDictAdStyleService.getAdvDictAdStyleMap();
        Cache cache = cacheManager.getCache("adv_dict_adstyle");
        if (null != advDictAdStyleMap && advDictAdStyleMap.size() > 0){
            for (String key : advDictAdStyleMap.keySet()){
                cache.put(key, advDictAdStyleMap.get(key));
            }
        }
    }
}
