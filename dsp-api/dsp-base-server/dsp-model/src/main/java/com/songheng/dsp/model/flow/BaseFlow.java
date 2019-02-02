package com.songheng.dsp.model.flow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description 基础流量信息
 * @author zhangshuai@021.com
 *
 */
@Setter
@Getter
@ToString
public class BaseFlow implements Serializable {

    private static final long serialVersionUID = 1983712738291293847L;

    /**
     *流量终端:h5/app/union/pc
     **/
    private String terminal;
    /**
     *页面类型 list/detail/open/sy/ny....
     **/
    private String pgType;
    /**
     *流量类型:Mobile,Computer
     **/
    private String deviceType;
    /**
     *客户端ip
     **/
    private String remoteIp;
    /**
     *userAgent
     **/
    private String ua;
    /**
     *userAgent 唯一标识
     **/
    private int userAgentId;
    /**
     *操作系统+版本
     **/
    private String os;
    /**
     * 操作系统
     **/
    private String osName;
    /**
     *浏览器名称
     **/
    private String browserName;
    /**
     *浏览器版本
     **/
    private String browserVersion;
    /**
     *referer
     **/
    private String referer;
    /**
     * 安装时间
     **/
    private String installTime;
    /**
     *地域信息：省份
     **/
    private String province;

    /**
     *地域信息：地级市
     **/
    private String city;

    /**
     * 是否是测试流量
     **/
    private boolean isTestFlow;
    /**
     *是否是刷量流量
     **/
    private boolean isBrushFlow;

    /**
    * 请求Id
    **/
    private String reqId;

    /**
     *流量广告位 list_1_1,list_1_2
     **/
    private List<String> flowPositions;
    /**
     *垄断流量占用的广告位
     **/
    private Set<String> monopolyPositions;

    public BaseFlow(){
        this.flowPositions = new ArrayList<>(20);
        this.monopolyPositions = new HashSet<>(20);
    }

}
