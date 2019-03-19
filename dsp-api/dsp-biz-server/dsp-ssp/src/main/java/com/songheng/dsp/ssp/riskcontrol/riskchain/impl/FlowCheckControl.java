package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

/**
 * @description: 流量校验
 * @author: zhangshuai@021.com
 * @date: 2019-03-18 19:44
 **/
public class FlowCheckControl extends RiskControl {
    @Override
    protected RiskControlResult doVerification(BaseFlow flow) {
        if(null==flow){
            return new RiskControlResult(false,"10001","流量信息为空",flow);
        }
        //验证广告位信息
        if(flow.getReqSlotInfos().size()==0){
            return new RiskControlResult(false,"10002","广告位信息为空",flow);
        }
        //验证必要参数信息
        int idx = isInvalidArgs(flow.getAppId(),flow.getAppName(),flow.getTerminal(),flow.getQid(),
                flow.getUserId(),flow.getReferer(),flow.getUa(),flow.getPgnum()+"",flow.getUserIdType()+"",
                flow.getUserIdType()+"",flow.getPgType());
        if(idx!=-1){
            return new RiskControlResult(false,"10003","参数无效,idx:"+idx,flow);
        }
        return getSuccessResult(flow);

    }

    /**
     * 验证是否无效参数
     * @return 返回无效参数的下标,如果参数全部有效则返回-1
     * */
    private int isInvalidArgs(String... args){
        for(int i=0;i<args.length;i++){
            if(StringUtils.isInvalidString(args[i])){
                return i;
            }
        }
        return -1;
    }
}
