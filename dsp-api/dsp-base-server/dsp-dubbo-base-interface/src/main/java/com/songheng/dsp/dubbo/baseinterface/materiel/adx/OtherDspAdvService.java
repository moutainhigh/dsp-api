package com.songheng.dsp.dubbo.baseinterface.materiel.adx;

import com.songheng.dsp.model.materiel.DspAdvExtend;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 16:31
 * @description: 第三方广告池缓存接口
 */
public interface OtherDspAdvService {

    /**
     * 根据terminal 获取 List<DspAdvExtend>
     * @param terminal
     * @return
     */
    List<DspAdvExtend> getDspAdvInfos(String terminal);

    /**
     * 根据terminal,hisId,dspId 获取 DspAdvExtend
     * @param terminal
     * @param hisId 投放id
     * @param dspId
     * @return
     */
    DspAdvExtend getDspAdvByHisIdDspId(String terminal, String hisId, String dspId);

    /**
     * 根据terminal,advId,dspId 获取 DspAdvExtend
     * @param terminal
     * @param advId 物料id
     * @param dspId
     * @return
     */
    DspAdvExtend getDspAdvByAdvIdDspId(String terminal, String advId, String dspId);

    /**
     * 获取所有 List<DspAdvExtend>
     * @return
     */
    Map<String, List<DspAdvExtend>> getDspAdvListMap();

    /**
     * 获取所有 DspAdvExtend
     * @return
     */
    Map<String, DspAdvExtend> getDspAdvByHisIdMap();

    /**
     * 获取所有 DspAdvExtend
     * @return
     */
    Map<String, DspAdvExtend> getDspAdvByAdvIdMap();

}
