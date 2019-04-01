package com.songheng.dsp.model.client;

import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description: ADX客户端请求对象
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 16:12
 **/
@Getter
@Setter
public class AdxRtbClientRequest {
    /**
     * 流量信息
     */
    private BaseFlow baseFlow;

    /**
     * 竞价响应信息
     */
    private List<ResponseBean> responseBeans;
}
