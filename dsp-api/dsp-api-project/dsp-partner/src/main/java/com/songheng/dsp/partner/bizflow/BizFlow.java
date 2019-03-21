package com.songheng.dsp.partner.bizflow;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: 业务流程组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 16:46
 **/
public interface BizFlow {
    /**
     * 组装业务流
     * */
    RiskControlResult assemble(HttpServletRequest request,BaseFlow baseFlow);

}
