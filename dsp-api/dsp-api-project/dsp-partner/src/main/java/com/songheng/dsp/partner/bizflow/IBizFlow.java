package com.songheng.dsp.partner.bizflow;

import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 业务流程组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 16:46
 **/
public interface IBizFlow {
    /**
     * 组装业务流
     * */
    List<ResponseBean> assemble(HttpServletRequest request, BaseFlow baseFlow);

}
