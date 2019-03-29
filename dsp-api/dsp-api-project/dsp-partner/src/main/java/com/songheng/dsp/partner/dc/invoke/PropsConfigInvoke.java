package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.config.props.PropsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 16:59
 * @description: PropsConfig DC远程RPC调用
 */
@Service
public class PropsConfigInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * propsConfigService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    PropsConfigService propsConfigService;


    /**
     * getProperty
     * @param name
     * @return
     */
    @Cacheable(value ="props_config", key = "#name", condition = "#result != null")
    public String getProperty(String name){
        return propsConfigService.getProperty(name);
    }


    /**
     * 更新缓存
     */
    public void updatePropsConfig(){
        Map<String, String> propsConfigMap = propsConfigService.getAllProperty();
        Cache cache = cacheManager.getCache("props_config");
        if (null != propsConfigMap && propsConfigMap.size() > 0){
            for (String key : propsConfigMap.keySet()){
                cache.put(key, propsConfigMap.get(key));
            }
        }
    }
}
