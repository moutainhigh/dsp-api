package com.songheng.dsp.ssp.riskcontrol.riskchain;

import com.songheng.dsp.model.client.ClientResponse;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.enums.ClientReason;
import com.songheng.dsp.model.flow.BaseFlow;

/**
 * @description: 风控
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public abstract class RiskControl {

    /**
     * 获取成功结果：成功的方法不需要子类重写
     * */
    protected static final ClientResponse getSuccessResult(BaseFlow baseFlow){
        return new ClientResponse(true, ClientReason.SSP_SUCESS,baseFlow);
    }
    /**
     * 风控验证,以及风控的转发
     * @param baseFlow 流量信息
     * @param riskControl 具体的风控业务
     * @return  true : 风控验证通过  false:风控验证失败
     **/
    public ClientResponse verification(BaseFlow baseFlow,SspClientRequest request, RiskControl riskControl){
        ClientResponse riskControlResult = doVerification(baseFlow,request);
        if(null == riskControlResult){
            throw new NullPointerException("风控业务模块未正确设置风控结果返回值,请检查风控业务模块是否异常!");
        }
        if(!riskControlResult.isSuccess()){
            return riskControlResult;
        }else{
            return riskControl.verification(baseFlow,request,riskControl);
        }
    }


    /**
     * 具体实现风控的验证方法
     * @param baseFlow 流量信息
     * @return  true : 风控验证通过  false:风控验证失败
     **/
    protected abstract ClientResponse doVerification(BaseFlow baseFlow,SspClientRequest request);
}
