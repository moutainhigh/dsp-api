package com.songheng.dsp.model.output;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 输出的物料信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 16:08
 **/
@Getter
@Setter
public class OutPutAdv {
    /**
     * 物料下发时间
     * */
    private String time;
    /**
     * 素材类型 0:展示类 1:下载类 2:小程序类
     * */
    private String type;
    /**
     * 素材基本样式 big,one,group,video
     * */
    private String baseStyle;
    /**
     * 素材扩展样式 : 针对组图不同的展示形式
     *  0:普通样式
     *  1:新样式
     * */
    private String subStyle;

    /**
     * 广告主来源
     * */
    private String source;

    /**
     * 广告落地页url
     * */
    private String url;

    /**
     * 主图片列表
     * */
    private List<Img> mainImgs;
    /**
     * 辅图片列表：大图广告补充素材图片
     * */
    private List<Img> subImgs;


    private List<String> video;

    /**
     * 素材主文本内容
     * */
    private String mainContent;

    /**
     * 素材副文本内容
     * */
    private String subContent;

    /**
     * 下载地址
     * */
    private String downloadurl;

    /**
     * iOS 应用商店id
     * */
    private String appstoreId;
    /**
     * Android 应用包名
     * */
    private String packagename;

    /**
     * 广告位Id
     * */
    private String slotId;

    /**
     * 加载上报url
     * */
    private List<String> showRep;
    /**
     * 进屏上报url
     * */
    private List<String> inviewRep;
    /**
     * 点击上报
     * */
    private List<String> clickRep;
    /**
     * 转化上报
     * 下载类广告转化上报url，返回的url中参数status={{status}}，需根据下载状态进行宏替换
     * status=1，表示开始下载
     * status=2，表示下载完成
     * status=3，表示开始安装
     * status=4，表示安装完成
     * */
    private List<String> conversionRep;
    /**
     * 素材所属行业
     * */
    private String sectors;

    public OutPutAdv(){
        this.showRep = new ArrayList<>();
        this.inviewRep = new ArrayList<>();
        this.clickRep = new ArrayList<>();
        this.conversionRep = new ArrayList<>();
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
