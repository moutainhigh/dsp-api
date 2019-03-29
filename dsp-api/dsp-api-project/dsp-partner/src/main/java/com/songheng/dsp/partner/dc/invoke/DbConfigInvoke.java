package com.songheng.dsp.partner.dc.invoke;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.cluster.support.FailoverCluster;
import com.songheng.dsp.dubbo.baseinterface.config.db.DbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/28 16:49
 * @description: DbConfig DC远程RPC调用
 */
@Service
public class DbConfigInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * dbConfigService
     */
    @Reference(cluster = FailoverCluster.NAME, retries = 2, timeout = 1000, check = false, mock = "return null")
    DbConfigService dbConfigService;

    /**
     * getDbConfigValue
     * @param terminal
     * @param dspkey
     * @return
     */
    @Cacheable(value ="db_config", key = "#terminal.concat('_').concat(#dspkey)", condition = "#result != null")
    public String getDbConfigValue(String terminal, String dspkey){
        return dbConfigService.getDbConfigValue(terminal, dspkey);
    }

    /**
     * getDbConfigMap
     * @param terminal
     * @return
     */
    @Cacheable(value ="db_config", key = "#terminal", condition = "#result != null and #result.size() > 0")
    public Map<String, String> getDbConfigMap(String terminal){
        return dbConfigService.getDbConfigMap(terminal);
    }

    /**
     * 更新缓存
     */
    public void updateDbConfig(){
        Map<String, String> dbConfigMap = dbConfigService.getDbConfigMap();
        Cache cache = cacheManager.getCache("db_config");
        if (null != dbConfigMap && dbConfigMap.size() > 0){
            for (String key : dbConfigMap.keySet()){
                cache.put(key, dbConfigMap.get(key));
            }
        }
    }
}
