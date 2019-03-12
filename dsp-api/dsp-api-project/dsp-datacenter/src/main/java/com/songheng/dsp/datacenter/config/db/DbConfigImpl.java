package com.songheng.dsp.datacenter.config.db;

import com.songheng.dsp.dubbo.baseinterface.config.db.DbConfigService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/4 14:35
 * @description: DbConfig缓存接口实现类
 */
@Component
public class DbConfigImpl implements DbConfigService {

    /**
     * 获取指定 key 的 value
     * @param terminal
     * @param dspkey
     * @return
     */
    @Override
    public String getDbConfigValue(String terminal, String dspkey) {
        String key = String.format("%s%s%s", terminal, "_", dspkey);
        return DbConfigLoader.getDbConfigValue(key);
    }

    /**
     * 获取指定 key 的 Map<String, String>
     * key : app/h5/pc/logs
     * @param terminal
     * @return
     */
    @Override
    public Map<String, String> getDbConfigMap(String terminal) {
        Map<String, String> result = DbConfigLoader.getDbConfigMap(terminal);
        return null != result ? result : new HashMap<String, String>(16);
    }

}
