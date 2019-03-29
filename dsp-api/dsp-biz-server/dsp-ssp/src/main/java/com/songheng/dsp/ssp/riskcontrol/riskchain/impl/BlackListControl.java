package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


import com.songheng.dsp.model.client.ClientResponse;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.enums.ClientReason;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;
import com.songheng.dsp.ssp.riskcontrol.support.RiskControlSupport;
import java.util.Map;

/**
 * @description: 黑名单验证
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class BlackListControl extends RiskControl {
    @Override
    protected ClientResponse doVerification(BaseFlow baseFlow, SspClientRequest request) {
        //获取有哪些黑名单 以及需要验证的数据
        Map<String,String> blackListMap = RiskControlSupport.getBlackListKey(baseFlow);
        //流量验证
        for (Map.Entry<String, String> entry : blackListMap.entrySet()) {
            if(RiskControlSupport.isInBlackList(request.getBlackListMap(),entry.getKey(),entry.getValue())){
                return new ClientResponse(ClientReason.SSP_BLACK_LIST,entry.getKey()+"-"+entry.getValue(),baseFlow);
            }
        }
        return getSuccessResult(baseFlow);
    }
}
