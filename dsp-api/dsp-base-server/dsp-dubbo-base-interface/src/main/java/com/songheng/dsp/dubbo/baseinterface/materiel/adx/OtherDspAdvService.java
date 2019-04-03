package com.songheng.dsp.dubbo.baseinterface.materiel.adx;

import com.songheng.dsp.model.materiel.MaterielDirect;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 16:31
 * @description: 第三方广告池缓存接口
 */
public interface OtherDspAdvService {

    /**
     * 根据terminal 获取 List<MaterielDirect>
     * @param terminal
     * @return
     */
    List<MaterielDirect> getDspAdvInfos(String terminal);

    /**
     * 根据terminal,hisId,dspId 获取 MaterielDirect
     * @param terminal
     * @param hisId 投放id
     * @param dspId
     * @return
     */
    MaterielDirect getDspAdvByHisIdDspId(String terminal, String hisId, String dspId);

    /**
     * 根据terminal,advId,dspId 获取 MaterielDirect
     * @param terminal
     * @param advId 物料id
     * @param dspId
     * @return
     */
    MaterielDirect getDspAdvByAdvIdDspId(String terminal, String advId, String dspId);

    /**
     * 获取所有 List<MaterielDirect>
     * @return
     */
    Map<String, List<MaterielDirect>> getDspAdvListMap();

    /**
     * 获取所有 MaterielDirect
     * @return
     */
    Map<String, MaterielDirect> getDspAdvByHisIdMap();

    /**
     * 获取所有 MaterielDirect
     * @return
     */
    Map<String, MaterielDirect> getDspAdvByAdvIdMap();

}
