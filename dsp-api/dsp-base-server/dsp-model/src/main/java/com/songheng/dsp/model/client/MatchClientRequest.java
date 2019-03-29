package com.songheng.dsp.model.client;

import com.songheng.dsp.model.consume.ConsumeInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @description: 广告匹配的客户端请求参数
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 11:08
 **/
@Getter
@Setter
public class MatchClientRequest {

    /**
     * 需要匹配的广告
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
     * 广告计划的消耗情况
     * */
    private Map<String, ConsumeInfo> consumeInfo;

}
