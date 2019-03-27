package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.user.adx.DspUserService;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/27 15:19
 * @description: DspUser DC远程RPC调用
 */
@Service
public class DspUserInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * dspUserService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    DspUserService dspUserService;


    /**
     * getDspUsers
     * @param terminal
     * @return
     */
    @Cacheable(value ="adx_user", key = "#terminal", condition = "#result != null and #result.size() > 0")
    public List<DspUserInfo> getDspUsers(String terminal){
        return dspUserService.getDspUsers(terminal);
    }

    /**
     * getDspUsersByDspId
     * @param terminal
     * @param dspId
     * @return
     */
    @Cacheable(value ="adx_user", key = "#terminal.concat('_').concat(#dspId)", condition = "#result != null and #result.size() > 0")
    public List<DspUserInfo> getDspUsersByDspId(String terminal, String dspId){
        return dspUserService.getDspUsersByDspId(terminal, dspId);
    }

    /**
     * getDspUsersByPriority
     * @param terminal
     * @param priority
     * @return
     */
    @Cacheable(value ="adx_user", key = "#terminal.concat('_').concat(#priority)", condition = "#result != null and #result.size() > 0")
    public List<DspUserInfo> getDspUsersByPriority(String terminal, String priority){
        return dspUserService.getDspUsersByPriority(terminal, priority);
    }


    /**
     * 更新缓存
     */
    public void updateDspUsers(){
        Map<String, List<DspUserInfo>> dspUsers = dspUserService.getDspUsersListMap();
        Cache cache = cacheManager.getCache("adx_user");
        if (null != dspUsers && dspUsers.size() > 0){
            for (String key : dspUsers.keySet()){
                cache.put(key, dspUsers.get(key));
            }
        }
        Map<String, List<DspUserInfo>> dspUsersByDspId = dspUserService.getDspUsersByDspIdListMap();
        if (null != dspUsersByDspId && dspUsersByDspId.size() > 0){
            for (String key : dspUsersByDspId.keySet()){
                cache.put(key, dspUsersByDspId.get(key));
            }
        }
        Map<String, List<DspUserInfo>> dspUsersByPriority = dspUserService.getDspUsersByPriorityListMap();
        if (null != dspUsersByPriority && dspUsersByPriority.size() > 0){
            for (String key : dspUsersByPriority.keySet()){
                cache.put(key, dspUsersByPriority.get(key));
            }
        }
    }


}
