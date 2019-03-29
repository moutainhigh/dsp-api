package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.dict.AdxPositionService;
import com.songheng.dsp.model.dict.AdPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 18:16
 * @description: AdxPosition DC远程RPC调用
 */
@Service
public class AdxPositionInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * adxPositionService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdxPositionService adxPositionService;

    /**
     * getAdPositionList
     * @param terminal
     * @return
     */
    @Cacheable(value ="ad_position", key = "#terminal", condition = "#result != null and #result.size() > 0")
    public List<AdPosition> getAdPositionList(String terminal){
        return adxPositionService.getAdPositionList(terminal);
    }

    /**
     * getAdPositionById
     * @param terminal
     * @param locationId
     * @return
     */
    @Cacheable(value ="ad_position", key = "#terminal.concat('_').concat(#locationId)", condition = "#result != null")
    public AdPosition getAdPositionById(String terminal, String locationId) {
        return adxPositionService.getAdPositionById(terminal, locationId);
    }

    /**
     * getAdPositionByName
     * @param terminal
     * @param locationName
     * @return
     */
    @Cacheable(value ="ad_position", key = "#terminal.concat('_').concat(#locationName)", condition = "#result != null")
    public AdPosition getAdPositionByName(String terminal, String locationName){
        Cache cache = cacheManager.getCache("ad_position");
        if(null != locationName && (locationName.split("_").length == 5
                || locationName.split("_").length == 6)){
            String tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
            //具体站点_具体渠道
            Object position = cache.get(tml_locationName).get();
            if(null == position){
                tml_locationName = String.format("%s%s%s", terminal, "_",
                        locationName.replace(locationName.split("_")[1], "other"));
                //具体站点_other
                position = cache.get(tml_locationName).get();
            }
            if(null == position){
                locationName = locationName.replace(locationName.split("_")[0], "default");
                tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
                //default_具体渠道
                position = cache.get(tml_locationName).get();
            }
            if(null == position){
                locationName = locationName.replace(locationName.split("_")[1], "other");
                tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
                //default_other
                position = cache.get(tml_locationName).get();
            }
            return null == position ? new AdPosition() : (AdPosition) position;
        }
        return adxPositionService.getAdPositionByName(terminal, locationName);
    }

    /**
     * 更新缓存
     */
    public void updateAdxPosition(){
        Map<String, List<AdPosition>> adPositionListMap = adxPositionService.getAdPositionListMap();
        Cache cache = cacheManager.getCache("ad_position");
        if (null != adPositionListMap && adPositionListMap.size() > 0){
            for (String key : adPositionListMap.keySet()){
                cache.put(key, adPositionListMap.get(key));
            }
        }

        Map<String, AdPosition> adPositionMap = adxPositionService.getAdPositionMap();
        if (null != adPositionMap && adPositionMap.size() > 0){
            for (String key : adPositionMap.keySet()){
                cache.put(key, adPositionMap.get(key));
            }
        }
    }
}
