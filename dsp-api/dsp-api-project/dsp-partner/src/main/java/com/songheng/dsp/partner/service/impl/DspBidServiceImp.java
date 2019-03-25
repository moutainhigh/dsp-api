package com.songheng.dsp.partner.service.impl;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import com.songheng.dsp.partner.service.DspBidService;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @description: 自家DSP竞标请求服务实现
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 18:44
 **/
@Service
public class DspBidServiceImp implements DspBidService {


    @Override
    public Set<ReqSlotInfo> getReqSlotInfos(BaseFlow baseFlow) {
        return baseFlow.getReqSlotInfos();
    }
}
