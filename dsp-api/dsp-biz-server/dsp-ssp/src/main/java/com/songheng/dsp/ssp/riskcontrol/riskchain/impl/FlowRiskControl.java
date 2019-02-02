package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


import com.songheng.dsp.ssp.model.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

/**
 * @description: 流量风控,验证流量是否正常
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class FlowRiskControl extends RiskControl {

    /**
     *重写风控验证失败的原因
     **/
    @Override
    protected RiskControlResult getFailResult(BaseFlow baseFlow) {
        return new RiskControlResult(false,"testFlow","测试流量",baseFlow);
    }

    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow) {
        if(baseFlow.isTestFlow()){
            return getFailResult(baseFlow);
        }else{
            return getSuccessResult(baseFlow);
        }
    }
}
