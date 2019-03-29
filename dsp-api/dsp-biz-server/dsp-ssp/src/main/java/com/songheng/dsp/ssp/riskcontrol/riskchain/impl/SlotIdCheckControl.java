package com.songheng.dsp.ssp.riskcontrol.riskchain.impl;


import com.google.common.collect.Sets;
import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.model.client.ClientResponse;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.enums.ClientReason;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;

import java.util.Iterator;
import java.util.Set;

/**
 * @description: 过滤屏蔽掉的广告位
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 10:05
 **/
public class SlotIdCheckControl extends RiskControl {
    @Override
    protected ClientResponse doVerification(BaseFlow baseFlow, SspClientRequest request) {
        //如果有白名单列表,则取白名单和请求有效的广告位id的交集
        if(request.getTagIdWhitelist().size()>0){
            baseFlow.setValidTagIds(CollectionUtils.intersection(baseFlow.getValidTagIds(),request.getTagIdWhitelist()));
            //TODO log
        }
        //如果有黑名单列表,则排除黑名单数据
        Set<String> tagIdBlacklist = request.getTagIdBlacklist();
        if(tagIdBlacklist.size()>0){
            Iterator<String> iterator = tagIdBlacklist.iterator();
            while(iterator.hasNext()){
                baseFlow.getValidTagIds().remove(iterator.next());
            }
            //TODO log
        }
        if(baseFlow.getValidTagIds().size()>0) {
            return getSuccessResult(baseFlow);
        }else{
            return new ClientResponse(ClientReason.SSP_SLOT_SHIELD,baseFlow);
        }
    }
}

