package com.songheng.dsp.model.client;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.ssp.AdvSspFloorPrice;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @description: dsp实时竞价模块请求数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:06
 **/
@Getter
@Setter
@ToString
public class DspRtbClientRequest {
    /**
     * 需要rtb竞价的广告
     * */
    private List<MaterielDirect> advList;
    /**
     * 流量信息
     * */
    private BaseFlow baseFlow;

    /**
     * 广告位置的底价
     */
    private AdvSspFloorPrice advSspFloorPrice;


    /**
     * 竞价模式
     */
    private int bidModel;
}
