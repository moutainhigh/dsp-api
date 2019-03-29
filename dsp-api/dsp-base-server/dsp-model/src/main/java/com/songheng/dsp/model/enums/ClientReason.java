package com.songheng.dsp.model.enums;

import lombok.Getter;

/**
 * @description: 客户端返回的结果枚举信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 10:10
 **/
@Getter
public enum ClientReason {
    SSP_SUCESS("10000","验证成功"),
    SSP_FLOW_ISNULL("10001","流量为空"),
    SSP_SLOT_ISNULL("10002","广告位为空"),
    SSP_ARG_ERROR("10003","参数错误"),
    SSP_BLACK_LIST("10004","黑名单"),
    SSP_REFERER_ERROR("10005","刷量请求(referer/应用包名)"),
    SSP_SLOT_SHIELD("10006","广告位屏蔽"),



    MATCH_SUCESS("20000","匹配成功"),
    MATCH_OS("20001","操作系统不匹配")
    ;

    /**
     * code码
     * */
    private String code;
    /**
     * 原因
     * */
    private String reason;

    ClientReason(String code,String reason){
        this.code = code;
        this.reason = reason;
    }

}
