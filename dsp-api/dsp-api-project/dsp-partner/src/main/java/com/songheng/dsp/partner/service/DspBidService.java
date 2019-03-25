package com.songheng.dsp.partner.service;

/**
 * @description: DSP竞标请求
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 18:45
 **/
public interface DspBidService {
    /**
     * qps流量显示
     * @return qps流量限制
     * */
    boolean limitOverQPS();
}
