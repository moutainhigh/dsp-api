package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description: 物料的定向信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 20:48
 **/
@Getter
@Setter
public class MaterielDirect implements java.io.Serializable{

    public static final long serialVersionUID = 4862048213773124168L;
    /**
     * 素材投放Id
     * */
    private String deliveryId;
    /**
     * 竞价模式 iscustomtime
     * 0:自由竞价 1:优先竞价 2:打底竞价
     * */
    private int bidModel;

    /**
     * 广告等级 isgrayav
     * */
    private int adlever;

    /**
     * 广告分类 图书、音像,金融
     * */
    private String typeName;

    /***
     * 投放的省份/城市
     * 如果包含 "all" 则表示投放所有地域
     * */
    private String province;
    /**
     * 屏蔽的地域
     * */
    private String shieldArea;
    /**
     * 投放的操作系统
     * */
    private String os;
    /**
     * 投放的年龄组
     * */
    private String ageGroup;

    /**
     * 性别
     * */
    private String sex;

    /**
     * 应用、站点
     * */
    private String apptypeId;

    /**
     * 投放的渠道列表 channelnames
     * */
    private String qids;
    /**
     * 投放的新闻栏位 extra5
     * */
    private String newsClassifys;

    /**
     * 投放已经安装的应用
     * */
    private String installs;
    /**
     * 投放没有安装的应用
     * */
    private String notinstalls;

    /**
     * 手机型号
     * */
    private String vendor;

    /**
     * 运营商
     * */
    private String operator;

    /**
     * 兴趣趋向
     * */
    private String interesttendency;

    /**
     * 点击的历史标签
     * */
    private String clickHisLabel;

    /**
     * 网络情况（extra2)  -> wifi/4g
     * */
    private String network;


    /**
     * 是否点击重定向
     * */
    private int redirect;

    /**
     * 间隔时间
     * */
    private long intervalTime;

    /**
     * 单用户展现次数
     * */
    private int limitshow;

    /**
     * 单用户点击次数
     * */
    private int limitclick;

    /**
     * 限制的广告位
     * */
    private String limitPosition;
    /**
     * 投放兴趣
     * */
    private String interest;

    /**
     * 是否隐退广告 针对app开屏
     * */
    private int isretreatad;
    /**
     * 是否首刷广告 针对app开屏
     * */
    private int isfirstbrush;

    /**
     * 挂单Id
     * */
    private String orderId;
    /**
     * 第三方监测曝光url extra3
     * */
    private String showRep;

    /**
     * 第三方点击曝光url extra4
     * */
    private String clickRep;

    /**
     * 是否自动优化点击率
     * */
    private int putinway;

    /**
     * 高德标签
     * */
    private String gdLable;

    /**
     * 拆分的投放id个数
     * */
    private String subHisIdNum;


}
