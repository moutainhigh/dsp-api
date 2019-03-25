package com.songheng.dsp.partner.bizflow;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.output.OutPutAdv;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 业务流程组装
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 16:46
 **/
public interface IBizFlow {

    /**
     * 业务组装
     * @param request 请求
     * @param baseFlow 流量信息
     * @return 素材信息
     */
    List<OutPutAdv> assemble(HttpServletRequest request, BaseFlow baseFlow);

}
