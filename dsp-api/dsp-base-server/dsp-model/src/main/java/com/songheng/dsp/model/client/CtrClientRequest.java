package com.songheng.dsp.model.client;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;

import java.util.List;

/**
 * @description: ctr模型请求数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:03
 **/
public class CtrClientRequest {
    /**
     * 需要转化的ctr数据
     * */
    private List<MaterielDirect> adv;
    /**
     * 流量信息
     * */
    private BaseFlow baseFlow;

}
