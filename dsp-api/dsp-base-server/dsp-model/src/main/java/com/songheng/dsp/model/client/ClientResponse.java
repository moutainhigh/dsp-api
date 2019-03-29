package com.songheng.dsp.model.client;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.ClientReason;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 客户端响应结果
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 09:52
 **/
@Getter
@Setter
@ToString
public class ClientResponse {
    /**
     *响应结果:
     *      true 成功
     *      false 失败
     **/
    private boolean success;
    /**
     * 失败原因code
     **/
    private String code;
    /**
     * 失败关键信息详情
     **/
    private String reason;
    /**
     * 业务执行的链数
     **/
    private int rcNum;
    /**
     *流量信息
     **/
    private BaseFlow baseFlow;

    public ClientResponse(boolean flag ,ClientReason clientReason,BaseFlow baseFlow){
        this.success = flag;
        this.code = clientReason.getCode();
        this.reason = clientReason.getReason();
        this.baseFlow = baseFlow;
    }

    public ClientResponse(String code,String reason,BaseFlow baseFlow){
        this.success = false;
        this.code = code;
        this.reason = reason;
        this.baseFlow = baseFlow;
    }

    public ClientResponse(ClientReason clientReason, String reason, BaseFlow baseFlow){
        this.success = false;
        this.code = clientReason.getCode();
        this.reason = StringUtils.isBlank(reason) ? clientReason.getReason() :
                String.format("%s%s%s",clientReason.getReason(),":",reason);
        this.baseFlow = baseFlow;
    }

    public ClientResponse(ClientReason clientReason, BaseFlow baseFlow){
        this.success = false;
        this.code = clientReason.getCode();
        this.reason = clientReason.getReason();
        this.baseFlow = baseFlow;
    }
    /**
     *设置业务执行的链数
     **/
    public ClientResponse setRcNum(int rcNum){
        this.rcNum = rcNum;
        return this;
    }
}
