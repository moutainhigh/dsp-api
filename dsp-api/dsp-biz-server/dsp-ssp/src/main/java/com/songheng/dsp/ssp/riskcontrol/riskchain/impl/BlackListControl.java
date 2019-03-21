package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


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
    protected RiskControlResult doVerification(BaseFlow baseFlow) {
        BlackListLocalService blackListLocalService = RpcServiceContext.getLocalService(BlackListLocalService.class);
        Map<String,String> blackListMap = blackListLocalService.getBlackListKey(baseFlow);
        for (Map.Entry<String, String> entry : blackListMap.entrySet()) {
            if(blackListLocalService.isInBlackList(entry.getKey(),entry.getValue())){
                return new RiskControlResult(false,"10004","黑名单;"+entry.getKey()+":"+entry.getValue(),baseFlow);
            }
        }
        return getSuccessResult(baseFlow);
    }
}
