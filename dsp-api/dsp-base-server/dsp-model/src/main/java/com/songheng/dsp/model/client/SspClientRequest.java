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
     * 广告位映射数据 -> dc/local
     * */
    private Map<String, AdvSspSlot> advSspSlot;
    /**
     * 请求参数的基本流量信息
     * */
    private BaseFlow argBaseFlow;

    public SspClientRequest(Map<String, AdvSspSlot> advSspSlot,BaseFlow apiArg){
        this.advSspSlot = advSspSlot;
        this.argBaseFlow = apiArg;
    }

}
