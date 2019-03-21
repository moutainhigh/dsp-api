package com.songheng.dsp.partner.bizflow.impl;

import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.partner.bizflow.BizFlow;
import com.songheng.dsp.partner.service.BizService;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 业务流程组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 16:46
 **/
@Service
public class BizFlowImpl implements BizFlow {

    @Autowired
    BizService bizService;
    /**
     * 组装业务流
     * */
    @Override
    public RiskControlResult assemble(HttpServletRequest request,BaseFlow baseFlow){
        //初始化风控请求对象
        SspClientRequest sspClientRequest = bizService.initSspClientRequestObj(request,baseFlow);
        //风控
        return bizService.execute(sspClientRequest);
    }

}
