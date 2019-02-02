package com.songheng.dsp.ssp.riskcontrol.riskchain;

import com.songheng.dsp.ssp.model.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;

/**
 * @description: 风控
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public abstract class RiskControl {

    /**
     * 获取成功结果：成功的方法不需要子类重写
     * */
    protected static final RiskControlResult getSuccessResult(BaseFlow baseFlow){
        return new RiskControlResult(true,"success","验证通过",baseFlow);
    }
    /**
     *获取失败结果
     **/
    protected RiskControlResult getFailResult(BaseFlow baseFlow){
        return new RiskControlResult(false,"failed","验证失败",baseFlow);
    }

    /**
     * 风控验证,以及风控的转发
     * @param baseFlow 流量信息
     * @param riskControl 具体的风控业务
     * @return  true : 风控验证通过  false:风控验证失败
     **/
    public RiskControlResult verification(BaseFlow baseFlow, RiskControl riskControl){
        RiskControlResult riskControlResult = doVerification(baseFlow);
        if(null == riskControlResult){
            throw new NullPointerException("风控业务模块未正确设置风控结果返回值,请检查风控业务模块是否异常!");
        }
        if(!riskControlResult.isSuccess()){
            return getFailResult(baseFlow);
        }else{
            return riskControl.verification(baseFlow,riskControl);
        }
    }


    /**
     * 具体实现风控的验证方法
     * @param baseFlow 流量信息
     * @return  true : 风控验证通过  false:风控验证失败
     **/
    protected abstract RiskControlResult doVerification(BaseFlow baseFlow);
}
