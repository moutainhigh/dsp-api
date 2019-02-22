package com.songheng.dsp.match.advmatch.matchchain;

import com.songheng.dsp.match.advmatch.AdvMatchResult;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.DspAdvInfo;

/**
 * @description: 广告匹配
 * @author: zhangshuai@021.com
 **/
public abstract class AdvMatch {

    /**
     * 获取成功结果：成功的方法不需要子类重写
     * */
    protected static final AdvMatchResult getSuccessResult(BaseFlow baseFlow){
        return new AdvMatchResult(true,"success","验证通过",baseFlow);
    }
    /**
     *获取失败结果
     **/
    protected AdvMatchResult getFailResult(BaseFlow baseFlow){
        return new AdvMatchResult(false,"failed","验证失败",baseFlow);
    }

    /**
     * 广告匹配,以及匹配业务的转发
     * @param baseFlow 流量信息
     * @param advMatch 具体的广告匹配业务
     * @return  true : 风控验证通过  false:风控验证失败
     **/
    public AdvMatchResult verification(BaseFlow baseFlow, DspAdvInfo dspAdvInfo, AdvMatch advMatch){
        AdvMatchResult riskControlResult = doVerification(baseFlow,dspAdvInfo);
        if(null == riskControlResult){
            throw new NullPointerException("广告匹配业务模块未正确设置匹配结果返回值,请检查广告匹配业务模块是否异常!");
        }
        if(!riskControlResult.isSuccess()){
            return getFailResult(baseFlow);
        }else{
            return advMatch.verification(baseFlow,dspAdvInfo,advMatch);
        }
    }


    /**
     * 具体实现广告匹配的验证方法
     * @param baseFlow 流量信息
     * @return  true : 广告匹配通过
     *          false:广告匹配失败
     **/
    protected abstract AdvMatchResult doVerification(BaseFlow baseFlow,DspAdvInfo dspAdvInfo);
}
