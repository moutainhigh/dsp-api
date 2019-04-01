package com.songheng.dsp.model.client;

import com.songheng.dsp.model.consume.ConsumeInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @description: ctr模型请求数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:03
 **/
@Getter
@Setter
public class CtrClientRequest {
    /**
     * 需要转化的ctr数据
     * */
    private List<MaterielDirect> advList;
    /**
     * 流量信息
     * */
    private BaseFlow baseFlow;
    /**
     * 广告位ID
     * */
    private String tagId;

     /**
     * 广告计划的消耗情况
     * */
    private Map<String, ConsumeInfo> consumeInfo;
    /**
     * 竞价模式
     * */
    private int bidModel;

}
