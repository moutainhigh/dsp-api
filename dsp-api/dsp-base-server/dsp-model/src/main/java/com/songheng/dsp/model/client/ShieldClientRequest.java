package com.songheng.dsp.model.client;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @description: 屏蔽模块客户端请求参数
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:10
 **/
@Getter
@Setter
public class ShieldClientRequest {
    /**
     * 需要屏蔽的广告
     * */
    private List<MaterielDirect> advList;
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
     * 屏蔽的json政策
     * */
    private String shiledJson;
}
