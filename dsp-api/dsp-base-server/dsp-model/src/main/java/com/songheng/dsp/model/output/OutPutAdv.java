package com.songheng.dsp.model.output;

import com.songheng.dsp.model.materiel.MaterielBaseInfo;
import com.songheng.dsp.model.materiel.MaterielImgInfo;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * @description: 输出的物料信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 16:08
 **/
@Getter
@Setter
public class OutPutAdv extends MaterielBaseInfo implements java.io.Serializable{

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
    /**
     * 最大可缓存的时间(分钟)
     * */
    private int cacheTime;

    public OutPutAdv(){
        this.showRep = new ArrayList<>();
        this.inviewRep = new ArrayList<>();
        this.clickRep = new ArrayList<>();
        this.conversionRep = new ArrayList<>();
        this.cacheTime = 30;
        this.sectors = "";
        this.sectors = "";
    }
}
