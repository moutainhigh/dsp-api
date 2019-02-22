package com.songheng.dsp.match.advmatch;

import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 广告匹配结果
 * @author: zhangshuai@021.com
 **/
@Getter
@Setter
@ToString
public class AdvMatchResult {
    /**
     *风控结果:
     *      true 有效广告
     *      false 无效广告
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
     * 广告匹配业务执行的链数
     **/
    private int rcNum;

    public AdvMatchResult(boolean success, String reason, String detail, BaseFlow baseFlow){
        this.success = success;
        this.reason = reason;
        this.detail = detail;
        this.baseFlow = baseFlow;
    }
    /**
     *设置风控业务执行的链数
     **/
    public AdvMatchResult setRcNum(int rcNum){
        this.rcNum = rcNum;
        return this;
    }
}
