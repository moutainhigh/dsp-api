package com.songheng.dsp.partner.service;

import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;

/**
 * @description: 业务逻辑接口
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 15:13
 **/
public interface BizService {
    /**
     * 执行风控逻辑
     * @return 风控执行结果
     * */
    RiskControlResult execute();
}
