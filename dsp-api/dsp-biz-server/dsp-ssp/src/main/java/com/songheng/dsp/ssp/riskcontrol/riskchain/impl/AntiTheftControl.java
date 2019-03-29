package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;

import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.ClientResponse;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.enums.ClientReason;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

import java.util.List;

/**
 * @description: 反防盗验证
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class AntiTheftControl extends RiskControl{
    @Override
    protected ClientResponse doVerification(BaseFlow baseFlow, SspClientRequest request) {
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
        return new ClientResponse(ClientReason.SSP_REFERER_ERROR,baseFlow.getReferer()+"-"+baseFlow.getPackageName(),baseFlow);
    }
}
