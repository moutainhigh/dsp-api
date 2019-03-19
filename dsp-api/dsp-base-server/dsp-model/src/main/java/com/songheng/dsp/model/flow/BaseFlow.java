package com.songheng.dsp.model.flow;

import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.common.utils.DeviceUtils;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.DeviceType;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import java.io.Serializable;
import java.util.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
     * 请求的广告位ID集合
     * (api-arg)
     * */
    private Set<String> reqSlotIds;
    /**
     * app版本号  10403 = 1.4.3
     * (api-arg)
     * */
    private String appver;
    /**
     * 新闻分类:新闻栏位  toutiao,yule
     * (api-arg)
     * */
    private String newsClassify;
    /**
     * 新闻访问历史记录(最后访问三条)
     * (api-arg)
     * */
    private String visitHistory;
    /**
     * 广告所在页数
     * (api-arg) --无--> （默认第一页）
     * */
    private Integer pgnum;
    /**
     * 广告数量
     * (api-arg) --无--> (db-arg)
     * */
    private Integer adnum;
    /**
     * 渠道号
     *
     * （api-arg）
     * */
    private String qid;
    /**
     * 当前访问的新闻url
     *
     * （api-arg）
     * */
    private String newsurl;
    /**
     * 应用的用户唯一标识 (app的accid , h5,pc的新闻用户id)
     *
     * （api-arg）
     * */
    private String appUserId;

    /**
     * https://www.jianshu.com/p/38f4d1a4763b
     * 广告的用户唯一标识
     *
     *   |------|-----------|------------|
     *   |优先级 |android    |ios         |
     *   |------|-----------|------------|
     *   |    1 |ime        |idfa        |
     *   |------|-----------|------------|
     *   |    2 |UDID       |UDID        |
     *   |------|-----------|------------|
     *   |    3 |           |idfv        |
     *   |------|-----------|------------|
     *   |    4 |cookie     |cookie      |
     *   |------|-----------|------------|
     *   备注：UDID:deviceId
     *
     * （api-arg）
     * */
    private String userId;

    /**
     * 广告的用户唯一标识优先级
     *
     * (api-arg）
     * */
    private Integer userIdType;

    /**
     * 网络连接类型
     * [ CONNECTION_UNKNOWN(无法探测当前网络状态) : 0,
     * CELL_UNKNOWN( 蜂窝数据接入,未知网络类型) : 1,
     * CELL_2G(蜂窝数据2G网络) : 2,
     * CELL_3G(蜂窝数据3G网络) : 3,
     * CELL_4G(蜂窝数据4G网络) : 4,
     * CELL_5G(蜂窝数据5G网络) : 5,
     * WIFI(Wi-Fi网络接入) : 100,
     * ETHERNET(以太网接入) : 101,
     * NEW_TYPE(未知新类型) : 999 ]
     *
     * (api-arg)
     * */
    private String network;
    /**
     * 互联网服务提供商
     * [ UNKNOWN_OPERATOR(未知的运营商) : 0,
     *  CHINA_MOBILE(中国移动) : 1,
     *  CHINA_TELECOM(中国电信) : 2,
     *  CHINA_UNICOM(中国联通) : 3,
     *  OTHER_OPERATOR(其他运营商) : 99 ]
     *
     * (api-arg)
     * */
    private String isp;

    /**
     * 网卡MAC地址
     * (api-arg)
     * */
    private String mac;
    /**
     *  屏幕分辨率 例：360*640
     *  (api-arg)
     * */
    private String pixel;

    /**
     * referer
     * (api-arg)
     **/
    private String referer;
    /**
     * 安装时间 默认20180101
     * (api-arg)
     **/
    private String installTime;
    /**
     * 是否首刷(针对开屏)
     * (api-arg)
     * */
    private boolean isFirstBrush;

    /**
     * 设备型号 MI 2S
     * (api-arg)
     * */
    private String model;
    /**
     * 设备厂商名称 HUAWEI、xiaomi
     * (api-arg)
     * */
    private String vendor;

    /**
     * 操作系统
     * (ua-arg) --无--> (api-arg)
     **/
    private String os;
    /**
     *操作系统+版本
     * (ua-arg) --无--> (api-arg)
     **/
    private String osAndVersion;



    /**
     *客户端ip
     * (service-arg)
     **/
    private String remoteIp;
    /**
     *地域信息：省份
     * (service-arg)
     **/
    private String province;
    /**
     *地域信息：地级市
     * (service-arg)
     **/
    private String city;
    /**
     * 请求Id
     * (service-arg)
     **/
    private String reqId;
    /**
     * 当前流量QPS
     * (service-arg)
     * */
    private long currQps;



    /**
     * userAgent
     * (head-arg)
     **/
    private String ua;
    /**
     *userAgent 唯一标识
     * (ua-arg)
     **/
    private int userAgentId;

    /**
     *浏览器名称
     * (ua-arg)
     **/
    private String browserName;
    /**
     *浏览器版本
     * (ua-arg)
     **/
    private String browserVersion;




    /**
     * 包名或网页地址;com.songheng.eastnews , http://mini.eastday.com
     * (db-arg)
     * */
    private String appId;
    /**
     * 应用名称(站点) ;ttz/ittz/DFTT
     *  (db-arg)
     * */
    private String appName;
    /**
     *  终端:h5/app/pc
     *  (db-arg)
     **/
    private String terminal;
    /**
     * 流量类型:Mobile,Computer
     * (ua-arg) --无--> 默认Mobile
     **/
    private String deviceType;
    /**
     *页面类型 list/detail/open/sy/ny...
     * (db-arg)
     **/
    private String pgType;







    /**
     * 性别 -1 : 未知 1：男  0：女
     * (hbase-arg)
     *
     * */
    private String gender;
    /**
     * 年龄 0-17 18-24 25-34 35-44 45+
     * (hbase-arg)
     * */
    private String age;


    /**
     * 是否是测试流量
     **/
    private boolean isTestFlow;
    /**
     *是否是刷量流量
     **/
    private boolean isBrushFlow;


    /**
     * 请求的广告位信息
     * (parse)
     * */
    private Set<ReqSlotInfo> reqSlotInfos;




//    /**
//     *所有流量广告位 list_1_1,list_1_2
//     **/
//    private List<AdvPositions> flowPositions;
//
//    /**
//     * 竞价的广告位
//     * */
//    private List<AdvPositions> bidPositions;
//    /**
//     *垄断流量占用的广告位
//     **/
//    private Set<AdvPositions> monopolyPositions;

    public BaseFlow(){
        this.reqId = RandomUtils.generateRandString("r",19);
        this.reqSlotInfos = new HashSet<>(10);
        this.reqSlotIds = new HashSet<>(10);
    }
    /**
     * @param ua req_head
     * */
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

    }
    /**
     * @param ua req_head
     * @param remoteIp 客户端ip
     * */
    public BaseFlow(String ua,String remoteIp){
        this(ua);
        this.remoteIp = remoteIp;
        //TODO
        this.province = "上海";
        this.city = "上海";

    }
    /**
     * @param ua req_head
     * @param remoteIp ip
     * @param reqSlotIds 广告位Id
     * @param advSspSlotMap 所有广告位数据Map
     * */
    public BaseFlow(String ua,String remoteIp,String reqSlotIds,Map<String, AdvSspSlot> advSspSlotMap){
        this(ua,remoteIp);
        //添加广告位信息
        if(StringUtils.isNotNullOrEmpty(reqSlotIds) && null!=advSspSlotMap){
            this.reqSlotIds = CollectionUtils.listToSet(StringUtils.strToList(reqSlotIds));
            Iterator<String> iterator = this.reqSlotIds.iterator();
            while(iterator.hasNext()){
                String slotId = iterator.next();
                if(advSspSlotMap.containsKey(slotId)) {
                    reqSlotInfos.add(new ReqSlotInfo(advSspSlotMap.get(slotId), this));
                }
            }
        }
    }

    /**
     * @param ua req_head
     * @param remoteIp ip
     * @param reqSlotIds 广告位Id
     * @param advSspSlotMap 所有广告位数据Map
     * */
    public BaseFlow(String ua,String remoteIp,String reqSlotIds,Map<String, AdvSspSlot> advSspSlotMap,BaseFlow baseFlow){
        this(ua,remoteIp,reqSlotIds,advSspSlotMap);
        //API-ARG
        this.qid = StringUtils.replaceInvalidString(baseFlow.getQid(),"");
        this.network = StringUtils.replaceInvalidString(baseFlow.getNetwork(),"0"); //未知
        this.age = StringUtils.replaceInvalidString(baseFlow.getAge(),"-1"); //未知
        this.gender = StringUtils.replaceInvalidString(baseFlow.getGender(),"-1");
        this.isp = StringUtils.replaceInvalidString(baseFlow.getIsp(),"0");
        this.age = StringUtils.replaceInvalidString(baseFlow.getAge(),"0-100");
        this.installTime = StringUtils.replaceInvalidString(baseFlow.getInstallTime(),"20180101");
        this.appver = StringUtils.replaceInvalidString(baseFlow.getAppver(),"10000");
        this.newsClassify = StringUtils.replaceInvalidString(baseFlow.getNewsClassify(),"");
        this.visitHistory = StringUtils.replaceInvalidString(baseFlow.getVisitHistory(),"");
        this.newsurl = StringUtils.replaceInvalidString(baseFlow.getNewsurl(),"");
        this.appUserId = StringUtils.replaceInvalidString(baseFlow.getAppUserId(),"");
        this.userId = StringUtils.replaceInvalidString(baseFlow.getUserId(),"");
        this.mac = StringUtils.replaceInvalidString(baseFlow.getMac(),"");
        this.pixel = StringUtils.replaceInvalidString(baseFlow.getPixel(),"");
        this.referer = StringUtils.replaceInvalidString(baseFlow.getReferer(),"");
        this.vendor = StringUtils.replaceInvalidString(baseFlow.getVendor(),"");
        this.pgnum = baseFlow.getPgnum();
        this.newsurl = baseFlow.getNewsurl();
        this.userIdType = baseFlow.getUserIdType();

    }
}
