package com.songheng.dsp.partner.service.impl;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.partner.dc.DictDc;
import com.songheng.dsp.partner.service.BizService;
import com.songheng.dsp.partner.utils.RemoteIpUtil;
import com.songheng.dsp.ssp.RiskControlClient;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 初始化流量信息
     * */
    @Override
    public SspClientRequest initSspClientRequestObj(HttpServletRequest request, BaseFlow apiArg){
        //获取客户端IP
        String remoteIp = RemoteIpUtil.getRemoteIp(request);
        //获取客户端ua
        String ua = request.getHeader("user-agent");
        //获取客户端referer
        String referer = request.getHeader("referer");
        apiArg.setRemoteIp(remoteIp);
        apiArg.setUa(ua);
        apiArg.setReferer(referer);
        apiArg.setSlotIds(StringUtils.replaceInvalidString(apiArg.getSlotIds(),""));
        //获取广告位集合数据
        List<String> slotIds = StringUtils.strToList(apiArg.getSlotIds());
        Map<String, AdvSspSlot> slotMap = new HashMap<>(slotIds.size());
        for(String slotId : slotIds){
            AdvSspSlot advSspSlot = dictDc.getAdvSspSlotMap(slotId);
            if(advSspSlot!=null) {
                slotMap.put(slotId,advSspSlot);
            }
        }
        return new SspClientRequest(slotMap,apiArg);
    }
    /**
     * 执行风控业务
     * */
    @Override
    public RiskControlResult execute(SspClientRequest request){
        return RiskControlClient.verification(request);
    }
}
