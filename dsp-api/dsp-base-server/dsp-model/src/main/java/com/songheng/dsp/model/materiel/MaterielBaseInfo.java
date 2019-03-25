package com.songheng.dsp.model.materiel;

import java.util.List;

/**
 * @description: 物料基础信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 20:37
 **/
public class MaterielBaseInfo {
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
    private List<MaterielImgInfo> mainImgs;
    /**
     * 辅图片列表：大图广告补充素材图片
     * */
    private List<MaterielImgInfo> subImgs;


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

}
