package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspQidService;
import com.songheng.dsp.model.ssp.AdvSspQid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 21:16
 * @description: AdvSspQid DC远程RPC调用
 */
@Service
public class AdvSspQidInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advSspQidService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvSspQidService advSspQidService;

    /**
     * getAdvSspQid
     * @param terminal
     * @param appName
     * @param qid
     * @return
     */
    @Cacheable(value ="adv_ssp_qid", key = "#terminal.concat('_').concat(#appName).concat('_').concat(#qid)", condition = "#result != null")
    public AdvSspQid getAdvSspQid(String terminal, String appName, String qid){
        return advSspQidService.getAdvSspQid(terminal, appName, qid);
    }

    /**
     * 更新缓存
     */
    public void updateAdvSspQid(){
        Map<String, AdvSspQid> advSspQidMap = advSspQidService.getAdvSspQidMap();
        Cache cache = cacheManager.getCache("adv_ssp_qid");
        if (null != advSspQidMap && advSspQidMap.size() > 0){
            for (String key : advSspQidMap.keySet()){
                cache.put(key, advSspQidMap.get(key));
            }
        }
    }

}
