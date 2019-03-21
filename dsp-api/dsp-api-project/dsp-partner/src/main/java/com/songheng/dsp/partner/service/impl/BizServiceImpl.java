package com.songheng.dsp.partner.service.impl;

import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.partner.dc.DictDc;
import com.songheng.dsp.partner.service.BizService;
import com.songheng.dsp.ssp.RiskControlClient;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description: 业务逻辑组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 15:13
 **/
@Service
public class BizServiceImpl implements BizService {

    @Autowired
    DictDc dictDc;

    @Override
    public RiskControlResult execute(){
        String ua = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36";
        String reqSlotIds = "list";
        String remoteIp = "10.9.119.12";
        BaseFlow reqArgBaseFlow = new BaseFlow();
        reqArgBaseFlow.setNewsClassify("yule");
        reqArgBaseFlow.setGender("1");
        reqArgBaseFlow.setQid("test");
        reqArgBaseFlow.setUserId("121212");
        reqArgBaseFlow.setUserIdType(4);
        reqArgBaseFlow.setPgnum(1);
        reqArgBaseFlow.setReferer("http://www.biadu.com");
        reqArgBaseFlow.setMac("02:00:00:00");
        Map<String, AdvSspSlot> slotMap = dictDc.getAdvSspSlotMap();
        return RiskControlClient.verification(new SspClientRequest(ua,remoteIp,reqSlotIds,slotMap,reqArgBaseFlow));
    }
}
