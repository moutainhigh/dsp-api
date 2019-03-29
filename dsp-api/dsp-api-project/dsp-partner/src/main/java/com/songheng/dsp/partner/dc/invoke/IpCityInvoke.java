package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.dict.IpCityService;
import com.songheng.dsp.model.dict.IpCityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 20:40
 * @description: IpCity DC远程RPC调用
 */
@Service
public class IpCityInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * ipCityService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    IpCityService ipCityService;

    /**
     * getIpCityInfo
     * @param ip
     * @return
     */
    @Cacheable(value ="ip_city", key = "#ip", condition = "#result != null")
    public IpCityInfo getIpCityInfo(String ip){
        return ipCityService.getIpCityInfo(ip);
    }

    /**
     * 更新缓存
     */
    public void updateIpCity(){
        Map<String, IpCityInfo> ipCityMap = ipCityService.getAllIpCity();
        Cache cache = cacheManager.getCache("ip_city");
        if (null != ipCityMap && ipCityMap.size() > 0){
            for (String key : ipCityMap.keySet()){
                cache.put(key, ipCityMap.get(key));
            }
        }
    }
}
