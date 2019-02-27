package com.songheng.dsp.dubbo.baseinterface.common.adx.service;

import com.songheng.dsp.model.materiel.DspAdvExtend;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 16:31
 * @description: DspAdv缓存接口
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

}
