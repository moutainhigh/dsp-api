package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

/**
 * @description: 防刷验证
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class AntiBrushRiskControl extends RiskControl{
    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow) {
        if(baseFlow.isBrushFlow()){
            return new RiskControlResult(false,"10004","防刷流量",baseFlow);
        }else{
            return getSuccessResult(baseFlow);
        }
    }
}
