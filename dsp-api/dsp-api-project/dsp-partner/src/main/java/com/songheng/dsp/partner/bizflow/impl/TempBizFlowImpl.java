package com.songheng.dsp.partner.bizflow.impl;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.output.OutPutAdv;
import com.songheng.dsp.partner.bizflow.BaseBizFlow;
import com.songheng.dsp.partner.bizflow.IBizFlow;
import com.songheng.dsp.partner.service.BizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 业务流程组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 16:46
 **/
@Service("temp")
public class TempBizFlowImpl extends BaseBizFlow implements IBizFlow {

    @Autowired
    BizService bizService;
    /**
     * 组装业务流
     * */
    @Override
    public List<OutPutAdv> assemble(HttpServletRequest request, BaseFlow baseFlow){
        System.out.println("temp-biz-flow");
        return groupBizFlow(request,baseFlow);
    }
}
