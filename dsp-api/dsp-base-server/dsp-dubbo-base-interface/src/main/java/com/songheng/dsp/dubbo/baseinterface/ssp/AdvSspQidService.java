package com.songheng.dsp.dubbo.baseinterface.ssp;

import com.songheng.dsp.model.ssp.AdvSspQid;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 10:22
 * @description: 渠道白名单缓存接口
 */
public interface AdvSspQidService {

    /**
     * 获取所有 渠道白名单信息
     * @return
     */
    Map<String, AdvSspQid> getAdvSspQidMap();

    /**
     * 获取指定 terminal, appName, qid 渠道白名单信息
     * @param terminal
     * @param appName
     * @param qid
     * @return
     */
    AdvSspQid getAdvSspQid(String terminal, String appName, String qid);

}
