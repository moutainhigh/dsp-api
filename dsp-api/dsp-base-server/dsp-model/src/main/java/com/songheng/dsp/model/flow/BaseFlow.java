package com.songheng.dsp.model.flow;

import com.songheng.dsp.common.utils.DeviceUtils;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.enums.DeviceType;
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
     * 当前流量QPS
     * */
    private long currQps;

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
        this.reqId = RandomUtils.generateRandString("r",10);
        this.flowPositions = new ArrayList<>(20);
        this.bidPositions = new ArrayList<>(15);
        this.monopolyPositions = new HashSet<>(5);
        this.net = "wifi";
        this.age = "";
        this.gender = "";
        this.isp = "";
    }
    public BaseFlow(String ua){
        this();
        this.ua = ua;
        this.os = DeviceUtils.getOsName(ua);
        this.osAndVersion = DeviceUtils.getOs(ua);
        this.browserName = DeviceUtils.getBrowserName(ua);
        this.browserVersion = DeviceUtils.getBrowserVersion(ua);
        this.deviceType = DeviceUtils.getDeviceType(ua);
        this.userAgentId = DeviceUtils.getUserAgentId(ua);
        this.model = DeviceUtils.getPhoneModel(ua);
        if(DeviceType.isComputer(this.deviceType)){
            this.terminal = "pc";
        }
    }
    /**
     * 测试流量-测试用
     * */
    public static BaseFlow getTestBaseFlow(){
        String ua = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36";
        BaseFlow baseFlow = new BaseFlow(ua);
        baseFlow.setPgType("list");
        baseFlow.setTerminal("h5");
        baseFlow.setBrushFlow(true);
        baseFlow.setTestFlow(true);
        baseFlow.setProvince("上海");
        baseFlow.setCity("上海");
        baseFlow.setNewsType("toutiao");
        baseFlow.setDeviceId("87125454657653121");
        baseFlow.setUserId("1512121381123513211");
        baseFlow.getFlowPositions().addAll(AdvPositions.getAdvPositionsByJsonStr(getTestAdvPositionJson(baseFlow,""),baseFlow));
        baseFlow.getBidPositions().addAll(AdvPositions.getAdvPositionsByJsonStr(getTestAdvPositionJson(baseFlow,""),baseFlow));
        return baseFlow;
    }
    /**
     * 获取测试的广告位
     * */
    public static String getTestAdvPositionJson(BaseFlow baseFlow,String jsonStr){
        if(baseFlow.isTestFlow() && baseFlow.isBrushFlow()){
            if(DeviceType.isMobile(baseFlow.getDeviceType())) {
                return "[{\"pid\":\"detail_-2_1\",\"style\":\"one,group,full\"},"
                        + "{\"pid\":\"detail_-1_1\",\"style\":\"one,big,group\"},"
                        + "{\"pid\":\"detail_-1_2\",\"style\":\"one,big,group\"},"
                        + "{\"pid\":\"detail_-6_1\",\"style\":\"big\"},"
                        + "{\"pid\":\"detail_1_1\",\"style\":\"one,big,group\"},"
                        + "{\"pid\":\"detail_1_2\",\"style\":\"one,big,group\"},"
                        + "{\"pid\":\"detail_1_3\",\"style\":\"one,big,group\"}]";
            }else if(DeviceType.isComputer(baseFlow.getDeviceType())){
                return "[{\"pid\":\"sy_nyxf\"},"
                        + "{\"pid\":\"sy_y5\"}]";
            }else{
                return null;
            }
        }else{
            return jsonStr;
        }
    }
}
