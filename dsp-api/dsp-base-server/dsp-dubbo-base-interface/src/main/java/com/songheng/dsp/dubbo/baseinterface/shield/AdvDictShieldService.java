package com.songheng.dsp.dubbo.baseinterface.shield;

import com.songheng.dsp.model.shield.AdvDictShield;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 17:59
 * @description: 屏蔽信息缓存接口
 */
public interface AdvDictShieldService {

    /**
     * 获取 以qid 维度所有的AdvDictShield
     * @return
     */
    Map<String, AdvDictShield> getAdvDictShieldMap();

    /**
     * 获取 指定qid 的AdvDictShield
     * @param qid
     * @return
     */
    AdvDictShield getAdvDictShieldByQid(String qid);

}
