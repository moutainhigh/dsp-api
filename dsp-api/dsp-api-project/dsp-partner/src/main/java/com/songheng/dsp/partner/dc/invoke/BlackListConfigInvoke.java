package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.config.blacklist.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * @description 黑名单实现类
 * @author zhangshuai@021.com
 * @date 2019/4/01 18:16
 */
@Service
public class BlackListConfigInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * propsConfigService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    BlackListService blackListService;


    /**
     * getProperty
     * @return
     */
    @Cacheable(value ="blacklist_config", condition = "#result != null")
    public Map<String, List<String>> getBlackList(){
        return blackListService.getAllBlackList();
    }


    /**
     * 更新缓存
     */
    public void updateBlackListConfig(){
        Map<String, List<String>> propsConfigMap = blackListService.getAllBlackList();
        Cache cache = cacheManager.getCache("blacklist_config");
        if (null != propsConfigMap && propsConfigMap.size() > 0){
            for (String key : propsConfigMap.keySet()){
                cache.put(key, propsConfigMap.get(key));
            }
        }
    }
}
