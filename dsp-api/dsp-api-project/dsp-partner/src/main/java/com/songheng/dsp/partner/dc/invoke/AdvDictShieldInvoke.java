package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.dubbo.baseinterface.shield.AdvDictShieldService;
import com.songheng.dsp.model.shield.AdvDictShield;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 18:23
 * @description: AdvDictShield DC远程RPC调用
 */
@Service
public class AdvDictShieldInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * advDictShieldService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    AdvDictShieldService advDictShieldService;


    /**
     * getAdvDictShieldByQid
     * @param qid
     * @return
     */
    public AdvDictShield getAdvDictShieldByQid(String qid){
        Cache cache = cacheManager.getCache("adv_dict_shield");
        if (StringUtils.isBlank(qid)){
            return new AdvDictShield();
        }
        Object result = cache.get(qid).get();
        if (null == result){
            result = cache.get("all").get();
        }
        return null == result ? new AdvDictShield() : (AdvDictShield) result;
    }

    /**
     * 更新缓存
     */
    public void updateAdvDictShield(){
        Map<String, AdvDictShield> advDictShieldMap = advDictShieldService.getAdvDictShieldMap();
        Cache cache = cacheManager.getCache("adv_dict_shield");
        if (null != advDictShieldMap && advDictShieldMap.size() > 0){
            for (String key : advDictShieldMap.keySet()){
                cache.put(key, advDictShieldMap.get(key));
            }
        }
    }
}
