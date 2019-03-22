package com.songheng.dsp.partner.controller;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.partner.bizflow.BizFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 广告素材请求入口
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 13:59
 **/
@RestController
public class AdvReqController {

   @Autowired
   BizFlow bizFlow;

    @RequestMapping(value="/api/list")
    public RiskControlResult partner(HttpServletRequest request, @RequestBody BaseFlow baseFlow){
        System.out.println(baseFlow);
        return bizFlow.assemble(request,baseFlow);
    }

}
