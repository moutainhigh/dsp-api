package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author: luoshaobing
 * @date: 2019/3/6 15:16
 * @description: ExtendNews
 */
@Getter
@Setter
@ToString
public class ExtendNews {

    /**
     * 广告ID
     */
    private String adId;
    private int startIdx;
    private int endIdx;
    private int isLoop;
    private int loopInterval;
    private String allowTypes;
    private String url;
    private String imgJson;
    private int isBigImgNews;
    private String bigImgJson;
    private String title;
    private String source;
    private int isExtend;
    private String qidStr;
    private String os;
    private int rate;
    private double stRate;
    private double edRate;
    /**
     * 平台
     */
    private String platform;
    /**
     * 允许投放区域
     */
    private String allowStations;
    /**
     * 投放ID
     */
    private String deliveryId;
    /**
     * pgtype list/detail/ny_y1...
     */
    private String pgtype;
    /**
     * channel app/h5/pc
     */
    private String channel;
    /**
     * 最大展现次数
     */
    private long maxShowTime;
    /**
     * 广告价格（cpm：千次展现价格）
     */
    private double money;
    /**
     * 计费方式
     */
    private String chargeway;
    /**
     * 图片高度
     */
    private int imgHeight;
    /**
     * 图片宽度
     */
    private int imgWidth;
    /**
     * 是否是下载类型的广告
     */
    private int isdownload;
    /**
     * 是否精准投放
     */
    private int isaccurate;
    /**
     * 是否是推广广告
     */
    private int isAdv;
    /**
     * 是否包位置
     */
    private int iscustomtime;
    /**
     * 在第几个位置
     */
    private int position;
    /**
     * 是否设置时间限制
     */
    private int islimittime;
    /**
     * 总价*10,0000（为了保证总价为整数，在原价基础上乘以十万）
     */
    private long totalmoney;
    /**
     * 允许投放的app类型id
     */
    private String apptypeid;
    /**
     * 是否时疑似假货
     */
    private int isfake;
    /**
     * 是否时灰色广告(可后台控价)
     */
    private int isgrayav;
    /**
     * 屏蔽区域
     */
    private String shieldarea;
    /**
     * 调控价格开始时间
     */
    private String stdate;
    /**
     * 调控价格结束时间
     */
    private String eddate;
    /**
     * 调控的价格
     */
    private double realunitprice;
    /**
     * appstoreid
     */
    private String appstoreid;
    /**
     * 投放的渠道
     */
    private String channelnames;
    /**
     * 视频url
     */
    private String video_link;
    /**
     * 视频时长
     */
    private int videoalltime;
    /**
     * 是否全屏
     */
    private int isfullscreen;
    /**
     * pc广告位置
     */
    private String pcadposition;
    /**
     * apk下载url
     */
    private String downloadurl;
    /**
     * 是否隐退广告
     */
    private int isretreatad;
    /**
     * 广告摘要
     */
    private String summary;
    /**
     * 安卓包名
     */
    private String packagename;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 兴趣、爱好
     */
    private String interest;
    /**
     * 计划结束时间
     */
    private String realendTime;
    /**
     * 0:普通广告 1:视频游戏广告  2:gif动图 -1:文字链
     */
    private String adType;
    /**
     * CPM价格
     */
    private double cpm;

    private double cpcfactor;
    /**
     * 是否首刷开屏广告 1: 首刷  0: 非首刷
     */
    private int isfirstbrush;
    /**
     * 附属字段1
     */
    private String extra1;
    /**
     * 附属字段2
     */
    private String extra2;
    /**
     * 附属字段3
     */
    private String extra3;
    /**
     * 附属字段4
     */
    private String extra4;
    /**
     * 附属字段5
     */
    private String extra5;
    /**
     * 性别;0:女 ,1：男 ,-1：不限
     */
    private int sex;
    /**
     * 年龄段人群
     */
    private String ageGroup;
    /**
     * 用户画像兴趣倾向
     */
    private String interestTendency;
    /**
     * 广告展现的间隔时间 , 0:不做限制,default:10(分钟)
     */
    private long intervalTime;
    /**
     * 限制展现次数  , 0:不做限制,default:0
     */
    private int limitshowtime;
    /**
     * 限制点击次数   0:不做限制,default:0
     */
    private int limitclicktime;
    /**
     * 累计刷新次数
     */
    private String totalnum;
    /**
     * 全量刷新次数
     */
    private String refreshnum;
    /**
     * 包断广告位 pc信息流
     */
    private String monopolyposition;
    /**
     * 广告组id
     */
    private String groupid;
    /**
     * 广告组的日预算
     */
    private long budget;
    /**
     * ocpc出价
     */
    private double ocpcunitprice;
    /**
     * 行业分类
     */
    private String sectors;
    /**
     * 投放拆分子id数
     */
    private int subhisidnum;
    /**
     * 子投放ID末尾4位
     */
    private String subHisIdEnd4;
    /**
     * 三图广告新样式
     */
    private String subadstyle ;
    /**
     * 是否优化点击率
     */
    private String putinway ;
    /**
     * 新版广告标
     */
    private int switchTag ;
    /**
     * 已安装app
     */
    private String installIds;
    /**
     * 未安装app
     */
    private String notInstallIds;
    /**
     * 标记是否喜欢联盟广告
     */
    private String remark;

    private String vender;

    private String operator;
    /**
     * h5针对app刷量; 1：刷量,0：不刷量
     */
    private int issupplement;
    /**
     * 刷量比例：0-1
     */
    private double supplementrate;
    /**
     * 历史点击标签
     */
    private String clickHisLabel;
    /**
     * 高德标签
     */
    private String gdLabel;
    /**
     * 预算类型
     */
    private int yusuanType;
    /**
     * 点击重定向(1)    扩展相似人群(2)
     */
    private int redirect;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单创建时间
     */
    private String orderCreateTime;
    /**
     * 排行
     */
    private int rank;
    /**
     * 精准投放url
     */
    private String accurateurl;
    /**
     * 点击回调url
     */
    private String clickbackurl;
    /**
     * 展现回调url
     */
    private String showbackurl;
    /**
     * 百度价格增加比率
     */
    private BigDecimal baiduadd;
    /**
     * 下载类广告转化上报url
     */
    private String conversionbackurl;

    public ExtendNews(){
        this.startIdx=-1;
        this.endIdx=-1;
        this.isLoop=-1;
        this.loopInterval=-1;
        this.allowTypes="";
        this.url="";
        this.imgJson="";
        this.bigImgJson="";
        this.title="";
        this.source="";
        this.isExtend=-1;
        this.qidStr="";
        this.os="";
        this.platform="dongfang";

        this.allowStations="";
        this.chargeway="";
        this.isaccurate=1;
        this.accurateurl="";
        this.rank=1;
        this.islimittime=1;
        this.clickbackurl  = "";
        this.showbackurl = "";
        this.baiduadd = new BigDecimal("1");

        this.shieldarea = "";
        this.channelnames = "";
        this.pcadposition = "";
        this.cpcfactor = 1.0D;
        this.pgtype = "";
        this.interest="";
        this.extra1 = "0";
        this.apptypeid = "";
        this.groupid = "";
        //十分钟
        this.intervalTime = 1000 * 60 * 10;
        this.summary = "";
        this.totalnum = "";
        this.refreshnum = "";
        this.extra5 = "";
        this.switchTag = -1;
        this.conversionbackurl = "";
        this.packagename = "";
        this.sectors = "0";
        this.subadstyle = "0";
        this.appstoreid = "";
    }

    @Override
    public int hashCode() {
        return deliveryId.hashCode();
    }

    /**
     * deliveryId 相同则为同一对象
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtendNews that = (ExtendNews) o;
        return Objects.equals(deliveryId, that.deliveryId);
    }
}
