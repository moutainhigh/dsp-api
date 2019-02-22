package com.songheng.dsp.match.advmatch.matchchain.impl;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.match.advmatch.AdvMatchResult;
import com.songheng.dsp.match.advmatch.matchchain.AdvMatch;
import com.songheng.dsp.model.enums.OS;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description: 操作系统匹配
 * @author: zhangshuai@021.com
 **/
@Slf4j
public class OsMatch extends AdvMatch {

    /**
     *重写风控验证失败的原因
     **/
    @Override
    protected AdvMatchResult getFailResult(BaseFlow baseFlow) {
        return new AdvMatchResult(false,"os","操作系统不匹配",baseFlow);
    }

    @Override
    protected AdvMatchResult doVerification(BaseFlow baseFlow, DspAdvInfo dspAdvInfo) {
        String deliveryOs = StringUtils.replaceInvalidString(dspAdvInfo.getDeliveryOs(), OS.Unknown.getName()).toLowerCase();
        if(OS.Unknown.getName().equalsIgnoreCase(deliveryOs)){
            log.error("[广告素材操作系统异常],hisId:{}",dspAdvInfo.getDeliveryid());
            return getFailResult(baseFlow);
        }
        List<String> os =  StringUtils.strToList(deliveryOs);
        if(os.contains(baseFlow.getOs().toLowerCase())) {
            return getSuccessResult(baseFlow);
        }else{
            return getFailResult(baseFlow);
        }
    }
}
