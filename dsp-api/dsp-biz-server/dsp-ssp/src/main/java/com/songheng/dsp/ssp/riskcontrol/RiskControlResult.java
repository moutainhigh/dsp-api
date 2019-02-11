package com.songheng.dsp.ssp.riskcontrol;

import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 风控结果
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 17:25
 **/
@Getter
@Setter
@ToString
public class RiskControlResult {
    /**
     *风控结果:
     *      true 有效流量
     *      false 无效流量
     **/
    private boolean success;
    /**
     * 无效原因
     **/
    private String reason;
    /**
     *具体原因
     **/
    private String detail;
    /**
     *流量信息
     **/
    private BaseFlow baseFlow;
    /**
     * 风控业务执行的链数
     **/
    private int rcNum;

    public RiskControlResult(boolean success, String reason, String detail,BaseFlow baseFlow){
        this.success = success;
        this.reason = reason;
        this.detail = detail;
        this.baseFlow = baseFlow;
    }
    /**
     *设置风控业务执行的链数
     **/
    public RiskControlResult setRcNum(int rcNum){
        this.rcNum = rcNum;
        return this;
    }
}
