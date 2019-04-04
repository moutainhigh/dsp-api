package com.songheng.dsp.model.client;

import com.songheng.dsp.model.consume.ConsumeInfo;
import com.songheng.dsp.model.dsp.AdvertiserInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @description: 限速模块请求数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 18:30
 **/
@Getter
@Setter
public class SpeedClientRequest {
    /**
     * 验证限速广告
     * */
    private MaterielDirect adv;
    /**
     * 流量信息
     * */
    private BaseFlow baseFlow;
    /**
     * 当前广告位
     * */
    private String tagId;
    /**
     * 竞价模式
     * */
    private int bidModel;

    /**
     * 广告主信息
     */
    private Map<Integer, AdvertiserInfo> advertiserInfoMap;

    /**
     * 广告计划的消耗情况
     * */
    private Map<String, ConsumeInfo> consumeInfo;
}
