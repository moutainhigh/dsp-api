package com.songheng.dsp.partner.bizflow;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.adxbid.AdxBidClient;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.partner.service.BizService;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 业务流模板
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 17:03
 **/
public abstract class BaseBizFlow{

    @Autowired
    BizService bizService;


    /**
     * 组装业务流
     * */
    public List<ResponseBean> groupBizFlow(HttpServletRequest request, BaseFlow baseFlow){
        List<ResponseBean> responseBeans = new ArrayList<>();
        //初始化风控请求对象
        SspClientRequest sspClientRequest = bizService.initSspClientRequestObj(request,baseFlow);
        //风控验证
        RiskControlResult riskControlResult = bizService.execute(sspClientRequest);
        if(riskControlResult.isSuccess()){
            //风控验证成功,发送第三方ADX竞标请求
            List<DspUserInfo> dspUserInfos = bizService.getAdxUserInfoList(baseFlow.getTerminal());
            responseBeans = AdxBidClient.execute(dspUserInfos,baseFlow);
            //发送DSP竞标请求


        }
        return responseBeans;
    }
}
