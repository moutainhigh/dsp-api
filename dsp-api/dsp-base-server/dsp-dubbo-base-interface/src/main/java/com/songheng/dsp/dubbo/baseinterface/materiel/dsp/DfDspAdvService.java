package com.songheng.dsp.dubbo.baseinterface.materiel.dsp;

import com.songheng.dsp.model.materiel.ExtendNews;

import java.util.Map;
import java.util.Set;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 10:16
 * @description: DfDsp广告池缓存接口
 */
public interface DfDspAdvService {

    /**
     * 获取以 terminal+pgtype 为key的 ExtendNews Map
     * @return
     */
    Map<String, Set<ExtendNews>> getTmlPgTypeExtendNewsMap();

    /**
     * 根据指定 terminal,pgtype 获取 Set<ExtendNews>
     * @param terminal
     * @param pgtype
     * @return
     */
    Set<ExtendNews> getExtendNewsSet(String terminal, String pgtype);

}
