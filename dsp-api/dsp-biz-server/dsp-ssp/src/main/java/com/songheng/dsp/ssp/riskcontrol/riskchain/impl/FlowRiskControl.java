package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

/**
 * @description: 流量风控,验证流量是否正常
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class FlowRiskControl extends RiskControl {

    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow) {
        if(baseFlow.isTestFlow()){
            return new RiskControlResult(false,"10003","流量信息为空",baseFlow);
        }else{
            return getSuccessResult(baseFlow);
        }
    }
}
