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
     *流量终端:h5/app/pc
     **/
    private String terminal;

    /**
     * 站点/应用名称:
     * h5/pc :site
     * app:apptypeid
     * */
    private String siteName;
    /**
     * 渠道号
     * */
    private String qid;
    /**
     * 流量的新闻url
     * */
    private String page;
    /**
     * 流量新闻栏位
     * */
    private String newsType;
    /**
     * 阅读的历史新闻
     * */
    private String readHistory;
    /**
     * 用户唯一标识
     * h5/pc:uid
     * app:ime
     * */
    private String userId;

    /**
     * 设备Id
     * */
    private String deviceId;

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
    private String osAndVersion;
    /**
     * 操作系统
     **/
    private String os;
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
     * 设备型号
     * */
    private String model;
    /**
     * 网络类型:默认wifi
     * wifi,5g,4g,3g,2g,unknown
     * */
    private String net;
    /**
     * 运营商
     * */
    private String isp;
    /**
     * 性别
     * */
    private String gender;
    /**
     * 年龄
     * */
    private String age;
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
     *所有流量广告位 list_1_1,list_1_2
     **/
    private List<AdvPositions> flowPositions;

    /**
     * 竞价的广告位
     * */
    private List<AdvPositions> bidPositions;
    /**
     *垄断流量占用的广告位
     **/
    private Set<AdvPositions> monopolyPositions;

    public BaseFlow(){
        this.flowPositions = new ArrayList<>(20);
        this.bidPositions = new ArrayList<>(15);
        this.monopolyPositions = new HashSet<>(5);
        //默认wifi
        this.net = "wifi";
        this.age = "";
        this.gender = "";
        this.isp = "";
    }

}
