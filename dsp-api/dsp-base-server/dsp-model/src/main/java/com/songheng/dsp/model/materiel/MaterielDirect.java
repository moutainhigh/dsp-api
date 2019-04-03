package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * @description: 物料的定向信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 20:48
 **/
@Getter
@Setter
public class MaterielDirect extends MaterielBudget implements java.io.Serializable{

    public static final long serialVersionUID = 4862048213773124168L;

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
     * 行业code
     * */
    private String sectorCode;
    /**
     * 行业名称
     * */
    private String sectorName;

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
     * 挂单创建时间
     */
    private String orderCreateTime;
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
    private String gdLabel;

    /**
     * 拆分的投放id个数
     * */
    private String subHisIdNum;
    /**
     * 终端
     */
    private String terminal;
    /**
     * 图片路径 img1Path, img2Path, img3Path
     */
    private String img1Path,img2Path,img3Path;
    /**
     * imgPath 以，分割
     */
    private String imgJson;
    /**
     * 图片高度
     */
    private int imgHeight;
    /**
     * 图片宽度
     */
    private int imgWidth;
    /**
     * pgtype list/detail/ny_y1...
     */
    private String pgtype;
    /**
     * channel app/h5/pc
     */
    private String channel;
    /**
     * 是否精准投放
     */
    private int isaccurate;
    /**
     * 新版广告标
     */
    private int switchTag ;
    /**
     * 投放时间段
     */
    private String timeQuantum;


    public MaterielDirect(){
        //十分钟
        this.intervalTime = 1000 * 60 * 10;
        this.switchTag = -1;
    }

    @Override
    public int hashCode() {
        return getDeliveryId().hashCode();
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
        MaterielDirect that = (MaterielDirect) o;
        return Objects.equals(getDeliveryId(), that.getDeliveryId());
    }


}
