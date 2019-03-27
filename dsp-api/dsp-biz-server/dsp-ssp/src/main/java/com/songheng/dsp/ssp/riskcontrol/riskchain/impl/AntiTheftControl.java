package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;

import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

import java.util.List;

/**
 * @description: 反防盗验证
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class AntiTheftControl extends RiskControl{
    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow,SspClientRequest request) {
        List<String> appIds =  StringUtils.strToList(baseFlow.getAppId());
        for(String appId : appIds ){
            if(appId.contains("http")){
                String url = appId.replace("http://","").replace("https://","");
                if(baseFlow.getReferer().contains(url)){
                    return getSuccessResult(baseFlow);
                }
            }else{
                if(appIds.contains(baseFlow.getPackageName())){
                    return getSuccessResult(baseFlow);
                }
            }
        }
        return new RiskControlResult(false,"10005","刷量请求",baseFlow);
    }
}
