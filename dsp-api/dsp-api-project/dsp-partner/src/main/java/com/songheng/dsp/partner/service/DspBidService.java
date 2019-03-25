package com.songheng.dsp.partner.service;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;

import java.util.Set;

/**
 * @description: DSP竞标服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 18:45
 **/
public interface DspBidService {

    Set<ReqSlotInfo> getReqSlotInfos(BaseFlow baseFlow);

}
