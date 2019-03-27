package com.songheng.dsp.model.flow;

import com.google.common.collect.Sets;
import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.common.utils.DeviceUtils;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.DeviceType;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.model.client.SspClientRequest;
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
     * 请求的广告位id,以","隔开
     * */
    private String slotIds;
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
     * 国际移动用户识别码
     * (api-arg)
     */
    private String imsi;

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
     * 应用包名 : app应用将会和注册的包名进行验证
     * （api-arg）
     * */
    private String packageName;



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
     * referer 将会与注册填的url进行验证
     * (head-arg)
     **/
    private String referer;
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
    /**
     * 请求的真实有效的广告位ID集合
     * (parse)
     * */
    private Set<String> validTagIds;

    /**
     *jsonp 回调参数
     * */
    private String jsonpcallback;

    public BaseFlow(){
        this.reqId = RandomUtils.generateRandString("r",19);
        this.reqSlotInfos = new TreeSet<>();
        this.validTagIds = new TreeSet<>();
    }
    /**
     * @param baseFlow 需要赋值的流量对象
     * @param ua req_head
     * */
    private static BaseFlow setUserAgentInfo(BaseFlow baseFlow,String ua){
        //如果 是有效ua,则直接解析ua,否则从参数中获取
        if(StringUtils.isNotBlank(ua) && DeviceUtils.getUserAgentId(ua)!=DeviceUtils.INVALID_UA_ID) {
            baseFlow.ua = ua;
            baseFlow.os = DeviceUtils.getOsName(ua);
            baseFlow.osAndVersion = DeviceUtils.getOs(ua);
            baseFlow.browserName = DeviceUtils.getBrowserName(ua);
            baseFlow.browserVersion = DeviceUtils.getBrowserVersion(ua);
            baseFlow.deviceType = DeviceUtils.getDeviceType(ua);
            baseFlow.userAgentId = DeviceUtils.getUserAgentId(ua);
            baseFlow.model = DeviceUtils.getPhoneModel(ua);
        }
        return baseFlow;

    }
    /**
     * @param baseFlow 需要赋值的流量对象
     * @param remoteIp ip
     * */
    private static BaseFlow setAreaInfo(BaseFlow baseFlow,String remoteIp){
        baseFlow.remoteIp = remoteIp;
        //TODO
        baseFlow.province = "上海";
        baseFlow.city = "上海";
        return baseFlow;

    }
    /**
     * @param baseFlow 需要赋值的流量对象
     * @param reqSlotIds 广告位Id
     * @param advSspSlotMap 所有广告位数据Map
     * */
    private static BaseFlow setReqSlotIdInfo(BaseFlow baseFlow,Map<String, AdvSspSlot> advSspSlotMap){
        //添加广告位信息
        if(null!=advSspSlotMap && advSspSlotMap.size()>0){
            for(String slotId:advSspSlotMap.keySet()){
                AdvSspSlot advSspSlot = advSspSlotMap.get(slotId);
                //如果广告数量没有通过参数传过来则从ssp配置中取
                if(null == baseFlow.getAdnum()) {
                    baseFlow.setAdnum(advSspSlot.getAdnum()<=0?1:advSspSlot.getAdnum());
                }
                //非信息流广告位,强制设置pgnum=1,adnum=1
                if(!advSspSlot.isFeeds()) {
                    baseFlow.setPgnum(1);
                    baseFlow.setAdnum(1);
                }
                //生成具体的广告位信息
                for(int idx=1;idx<=baseFlow.getAdnum();idx++){
                    baseFlow.reqSlotInfos.add(ReqSlotInfo.buildReqSlotInfo(advSspSlot,baseFlow,idx));
                }
            }
        }
        return baseFlow;
    }

    /**
     * @param baseFlow api参数设置的广告流量信息
     * */
    private static BaseFlow dealApiArg(BaseFlow baseFlow){
        if(null == baseFlow){
            throw new NullPointerException("[SSP]:封装的请求参数有误:BaseFlow为空");
        }
        //API-ARG
        baseFlow.setAdnum(baseFlow.getAdnum());
        baseFlow.qid = StringUtils.replaceInvalidString(baseFlow.getQid(),"");
        baseFlow.network = StringUtils.replaceInvalidString(baseFlow.getNetwork(),"0"); //未知
        baseFlow.age = StringUtils.replaceInvalidString(baseFlow.getAge(),"-1"); //未知
        baseFlow.gender = StringUtils.replaceInvalidString(baseFlow.getGender(),"-1");
        baseFlow.isp = StringUtils.replaceInvalidString(baseFlow.getIsp(),"0");
        baseFlow.age = StringUtils.replaceInvalidString(baseFlow.getAge(),"0-100");
        baseFlow.installTime = StringUtils.replaceInvalidString(baseFlow.getInstallTime(),"20180101");
        baseFlow.appver = StringUtils.replaceInvalidString(baseFlow.getAppver(),"10000");
        baseFlow.newsClassify = StringUtils.replaceInvalidString(baseFlow.getNewsClassify(),"");
        baseFlow.visitHistory = StringUtils.replaceInvalidString(baseFlow.getVisitHistory(),"");
        baseFlow.newsurl = StringUtils.replaceInvalidString(baseFlow.getNewsurl(),"");
        baseFlow.appUserId = StringUtils.replaceInvalidString(baseFlow.getAppUserId(),"");
        baseFlow.userId = StringUtils.replaceInvalidString(baseFlow.getUserId(),"");
        baseFlow.mac = StringUtils.replaceInvalidString(baseFlow.getMac(),"");
        baseFlow.pixel = StringUtils.replaceInvalidString(baseFlow.getPixel(),"");
        baseFlow.referer = StringUtils.replaceInvalidString(baseFlow.getReferer(),"");
        baseFlow.vendor = StringUtils.replaceInvalidString(baseFlow.getVendor(),"");
        baseFlow.pgnum = baseFlow.getPgnum();
        baseFlow.newsurl = baseFlow.getNewsurl();
        baseFlow.userIdType = baseFlow.getUserIdType();
        baseFlow.imsi =  StringUtils.replaceInvalidString(baseFlow.getImsi(),"");
        baseFlow.packageName = StringUtils.replaceInvalidString(baseFlow.getPackageName(),"");
        return baseFlow;
    }

    /**
     * 生成流量对象信息
     * */
    public static BaseFlow generateBaseFlow(SspClientRequest clientRequest){
        BaseFlow baseFlow = clientRequest.getArgBaseFlow();
        //处理请求参数
        dealApiArg(baseFlow);
        //设置ua信息
        setUserAgentInfo(baseFlow,baseFlow.getUa());
        //设置地域信息
        setAreaInfo(baseFlow,baseFlow.getRemoteIp());
        //设置广告位信息
        setReqSlotIdInfo(baseFlow,clientRequest.getAdvSspSlot());
        return baseFlow;
    }
}
