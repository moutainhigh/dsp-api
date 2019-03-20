package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:22
 * @description:
 */
@Getter
@Setter
@ToString
public class DspAdvInfo implements Serializable {
    private static final long serialVersionUID = -5077716995034385669L;
    /**
     * 具体的广告样式 1:大图|2:单图|3:三图|4:开屏广告|5:视频贴片广告|11:游戏视频广告|13:文字链广告|14:share广告|15:主页悬浮广告
     */
    private String type;
    /**
     * 标题
     */
    private String topic;
    /**
     * 来源
     */
    private String source;
    /**
     * 广告推广时间
     */
    private String date;
    /**
     * 广告链接
     */
    private String url;
    /**
     * -1:无图；0：小图；1：大图；2：图片新闻
     */
    private String ispicnews;
    /**
     * 小图
     */
    private List<Img> miniimg;
    /**
     * 小图数量
     */
    private String miniimg_size;
    /**
     * 大图数量
     */
    private String picnums;
    /**
     * 大图
     */
    private List<Img> lbimg;
    /**
     * 是否是推广广告
     */
    private String isadv;
    /**
     * 广告id adId
     */
    private transient String adv_id;
    /**
     * 广告投放id
     */
    private String deliveryid;
    /**
     * 精准投放的url
     */
    private String accurateurl;
    /**
     * 广告竞价的下标 0,1,2,3
     */
    private int idx;
    /**
     * 实际展现消耗价格
     */
    private long realcost;
    /**
     * 始投放指定单次展现消耗价格
     */
    private long originalcost;
    /**
     * 点击回调url
     */
    private String clickbackurl;
    /**
     * 展现回调url
     */
    private String showbackurl;
    /**
     * 进屏回调url
     */
    private String inviewbackurl;
    /**
     * 平台【dongfang,yuansheng】
     */
    private String platform;
    /**
     * 计费方式
     */
    private String chargeway;
    /**
     * 广告订单号
     */
    private String adorder;
    /**
     * 广告样式
     */
    private String adStyle;
    /**
     * 是否是下载类型的广告
     */
    private int isdownload;
    /**
     * 广告摘要 广告副标题
     */
    private String summary;
    /**
     * 累计刷新次数
     */
    private transient String totalnum;
    /**
     * 全量刷新次数
     */
    private transient String refreshnum;
    /**
     * 附属字段5 tuijian caijing tiyu
     */
    private transient String extra5;
    /**
     * 下载类广告转化上报url
     */
    private String conversionbackurl;
    /**
     * 包名
     */
    private String packagename;
    /**
     * 三图广告新样式
     */
    private String subadstyle;
    /**
     * 新版标记 开关
     */
    private int switchTag;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 投放的操作系统
     * ***/
    private String deliveryOs;
    /**
     * 广告位置
     **/
    private String pid;

    @Override
    public String toString(){
        return this.deliveryid;
    }

    @Getter
    @Setter
    public class Img {
        private String src;
        private int imgwidth;
        private int imgheight;

        public Img(){}

        public Img(String src){
            this.src = src;
        }

        public Img(String src, int imgwidth, int imgheight){
            this.src = src;
            this.imgwidth = imgwidth;
            this.imgheight = imgheight;
        }
    }
}