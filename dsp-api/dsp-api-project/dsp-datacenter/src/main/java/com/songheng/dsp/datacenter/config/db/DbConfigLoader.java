package com.songheng.dsp.datacenter.config.db;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/2/28 11:40
 * @description: DBConfig缓存数据
 */
@Slf4j
public class DbConfigLoader {

    /**
     * dbConfigMap
     * key : app/h5/pc/logs+"_"+dspkey
     * value : dspvalue
     */
    private volatile static Map<String, String> dbConfigMap = new ConcurrentHashMap<>(16);

    /**
     * tmlDbConfigMap
     * key : app/h5/pc/logs
     * value : Map<String, String>
     */
    private volatile static Map<String, Map<String, String>> tmlDbConfigMap = new ConcurrentHashMap<>(16);

    /**
     * 获取指定 key 的 value
     * @param key
     * @return
     */
    public static String getDbConfigValue(String key){
        return dbConfigMap.get(key);
    }

    /**
     * 获取指定 key 的 Map<String, String>
     * key : app/h5/pc/logs
     * @param key
     * @return
     */
    public static Map<String, String> getDbConfigMap(String key){
        return tmlDbConfigMap.get(key);
    }

    /**
     * 加载DBCONFIG
     */
    public static void loadAllDBConfig(){
        Map<String, String> dbConfigMapTmp = new ConcurrentHashMap<>(1024);
        String sql = SqlMapperLoader.getSql("DbConfig","getAllDbConfig");
        if (StringUtils.isBlank(sql)){
            log.error("loadAllDBConfig error sql is null, namespace: DbConfig, id: getAllDbConfig");
            return;
        }
        DbUtils.query2Map(sql, dbConfigMapTmp);
        if (dbConfigMapTmp.size() > 0){
            dbConfigMap = dbConfigMapTmp;
        }
        Map<String, String> appDbConf = new HashMap<>(256);
        Map<String, String> h5DbConf = new HashMap<>(256);
        Map<String, String> pcDbConf = new HashMap<>(256);
        Map<String, String> logsDbConf = new HashMap<>(256);
        for (String key : dbConfigMapTmp.keySet()){
            if (key.startsWith("app_")){
                appDbConf.put(key.substring(4), dbConfigMapTmp.get(key));
            } else if (key.startsWith("h5_")){
                h5DbConf.put(key.substring(3), dbConfigMapTmp.get(key));
            } else if (key.startsWith("pc_")){
                pcDbConf.put(key.substring(3), dbConfigMapTmp.get(key));
            } else if (key.startsWith("logs_")){
                logsDbConf.put(key.substring(5), dbConfigMapTmp.get(key));
            }
        }
        if (appDbConf.size() > 0){
            tmlDbConfigMap.put("app", appDbConf);
        }
        if (h5DbConf.size() > 0){
            tmlDbConfigMap.put("h5", h5DbConf);
        }
        if (pcDbConf.size() > 0){
            tmlDbConfigMap.put("pc", pcDbConf);
        }
        if (logsDbConf.size() > 0){
            tmlDbConfigMap.put("logs", logsDbConf);
        }
        log.debug("loadAllDBConfig successful, size: {}", dbConfigMapTmp.size());
    }

}
