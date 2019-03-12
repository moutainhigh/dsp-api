package com.songheng.dsp.datacenter.materiel.dsp;

import com.songheng.dsp.dubbo.baseinterface.materiel.dsp.DfDspAdvService;
import com.songheng.dsp.model.materiel.ExtendNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 15:55
 * @description: DfDsp广告池缓存接口实现类
 */
@Component
public class DfDspAdvImpl implements DfDspAdvService {

    /**
     * dfDspAdvCache
     */
    @Autowired
    private DfDspAdvCache dfDspAdvCache;

    /**
     * 获取以 terminal+pgtype 为key的 ExtendNews Map
     * @return
     */
    @Override
    public Map<String, Set<ExtendNews>> getTmlPgTypeExtendNewsMap() {
        return dfDspAdvCache.getTmlPgTypeExtendNewsMap();
    }

    /**
     * 根据指定 terminal,pgtype 获取 Set<ExtendNews>
     * @param terminal
     * @param pgtype
     * @return
     */
    @Override
    public Set<ExtendNews> getExtendNewsSet(String terminal, String pgtype) {
        Set<ExtendNews> result = dfDspAdvCache.getExtendNewsSet(terminal, pgtype);
        return null != result ? result : new HashSet<ExtendNews>();
    }
}
