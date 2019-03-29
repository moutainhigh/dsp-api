package com.songheng.dsp.model.client;

import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.consume.ConsumeInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

/**
 * @description: DSP竞价模板请求对象
 * @author: zhangshuai@021.com
 * @date: 2019-03-23 18:58
 **/
@Getter
@Setter
@ToString
public class DspBidClientRequest {

    /**
     * 流量信息
     */
    private BaseFlow baseFlow;
    /**
     * 用户信息
     */
    private DspUserInfo user;
    /**
     * 广告计划的消耗情况
     * */
    private Map<String,ConsumeInfo> consumeInfo;

    /**
     * 广告位对应的素材信息
     * */
    private Map<String, Set<MaterielDirect>> materiedInfoByTagIds;

    /**
     * 屏蔽的json政策
     * */
    private String shiledJson;

}
