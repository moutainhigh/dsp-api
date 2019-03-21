package com.songheng.dsp.model.client;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Map;

/**
 * @description: SSP模块的请求对象
 * @author: zhangshuai@021.com
 * @date: 2019-03-20 21:58
 **/
@Getter
@Setter
@ToString
public class SspClientRequest {
    /**
     * userAgent
     * */
    private String ua;
    /**
     * 客户端ip
     * */
    private String remoteIp;
    /**
     * 请求的广告位Id
     * */
    private String reqSlotIds;
    /**
     * 广告位映射数据 -> dc/local
     * */
    private Map<String, AdvSspSlot> advSspSlot;
    /**
     * 请求参数的基本流量信息
     * */
    private BaseFlow argBaseFlow;

    public SspClientRequest(String ua, String remoteIp, String reqSlotIds,
        Map<String, AdvSspSlot> advSspSlot,BaseFlow argBaseFlow){
        this.ua = ua;
        this.remoteIp = remoteIp;
        this.reqSlotIds = reqSlotIds;
        this.advSspSlot = advSspSlot;
        this.argBaseFlow = argBaseFlow;
    }

}
