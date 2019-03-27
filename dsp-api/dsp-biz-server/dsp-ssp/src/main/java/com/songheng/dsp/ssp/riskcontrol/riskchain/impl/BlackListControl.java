package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.context.RpcServiceContext;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;
import com.songheng.dsp.ssp.service.BlackListLocalService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @description: 黑名单验证
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class BlackListControl extends RiskControl {
    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow,SspClientRequest request) {
        //获取有哪些黑名单 以及需要验证的数据
        Map<String,String> blackListMap = BlackListLocalService.getBlackListKey(baseFlow);
        //流量验证
        for (Map.Entry<String, String> entry : blackListMap.entrySet()) {
            if(BlackListLocalService.isInBlackList(request.getBlackListMap(),entry.getKey(),entry.getValue())){
                return new RiskControlResult(false,"10004","黑名单;"+entry.getKey()+":"+entry.getValue(),baseFlow);
            }
        }
        return getSuccessResult(baseFlow);
    }
}
