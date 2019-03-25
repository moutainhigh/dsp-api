package com.songheng.dsp.partner.service;

import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 业务逻辑接口
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 15:13
 **/
public interface BizService {
    /**
     * 初始化SSP的模块的请求参数
     * @param request
     * @param apiArg api请求的参数
     * @return ssp客户端请求对象
     * */
    SspClientRequest initSspClientRequestObj(HttpServletRequest request, BaseFlow apiArg);

    /**
     * 执行风控逻辑
     * @return 风控执行结果
     * */
    RiskControlResult execute(SspClientRequest request);

    /**
     * 获取adx用户信息集合
     * */
    List<DspUserInfo> getAdxUserInfoList(String terminal);


}
