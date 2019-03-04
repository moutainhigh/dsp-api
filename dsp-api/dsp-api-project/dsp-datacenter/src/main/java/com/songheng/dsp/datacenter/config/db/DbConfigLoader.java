package com.songheng.dsp.datacenter.config.db;

import com.songheng.dsp.common.db.DbUtils;
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
        DbUtils.query2Map("SELECT CONCAT(conftype,'_',(CASE \n" +
                "      WHEN userId = -1  AND hisId = -1 AND qid = '' THEN GROUP_CONCAT(dspkey) \n" +
                "      WHEN qid <> '' THEN GROUP_CONCAT(qid,'_',dspkey)  \n" +
                "      WHEN userId<>-1 AND hisId <>-1 THEN GROUP_CONCAT(userId,'_',hisId,'_',dspkey)\n" +
                "      WHEN userId=-1 THEN GROUP_CONCAT(hisId,'_',dspkey)\n" +
                "      WHEN hisId=-1 THEN GROUP_CONCAT(userId,'_',dspkey)\n" +
                "      ELSE GROUP_CONCAT(qid,'_',userId,'_',hisId,'_',dspkey) END)) dspkey\n" +
                "  , dspvalue\n" +
                "  FROM adplatform_dsp_sysconf  \n" +
                "  WHERE flag = 1\n" +
                "  GROUP BY conftype,dspkey,userId,hisId,qid,dspvalue\n" +
                "  UNION ALL \n" +
                "  SELECT CONCAT('pc','_',b.dspkey) dspkey, b.dspvalue FROM (\n" +
                "     (SELECT 'positionkey' dspkey, group_concat(a.dspkey ORDER BY a.remark = '', a.remark+0, a.id) dspvalue " +
                "           FROM (SELECT id, dspkey, remark " +
                "               FROM adplatform_dsp_sysconf " +
                "               WHERE flag = 1 AND conftype = 'pcposition' AND confgroup = 'positionstyle' ORDER BY remark = '', remark+0, id) a) #广告位\n" +
                "      UNION ALL\n" +
                "     (SELECT 'news_site_type' dspkey, group_concat(substring(dspkey,8)) dspvalue " +
                "           FROM adplatform_dsp_sysconf " +
                "           WHERE flag = 1 AND conftype='pcposition' AND confgroup = 'sitepagetype' ORDER BY remark,id) # 站点\n" +
                "      UNION ALL\n" +
                "     (SELECT dspkey,dspvalue FROM adplatform_dsp_sysconf WHERE flag = 1 AND conftype='pcposition')\n" +
                "  ) b ", dbConfigMapTmp);
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
