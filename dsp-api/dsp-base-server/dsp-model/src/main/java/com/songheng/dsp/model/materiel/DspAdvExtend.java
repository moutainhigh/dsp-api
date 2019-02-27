package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 15:26
 * @description: DspAdvInfo扩展类
 */
@Getter
@Setter
public class DspAdvExtend extends DspAdvInfo {

    private String dspId;

    private Integer imgWidth;

    private Integer imgHeight;

    private String imgPath;

    private String img1Path,img2Path,img3Path;
    /**
     * 广告针对用户的性别;0:女 ,1：男 ,-1：不限
     */
    private int sex;
    /**
     * 允许投放地域
     */
    private String allowStations;
    /**
     * 是否包位置广告
     */
    private boolean ismonopolyad;
    /**
     * 下载类apk
     */
    private String downloadurl;
    /**
     * 是否包位置
     */
    private int iscustomtime;
    /**
     * deeplink连接
     */
    private String deeplink;
    /**
     * appstoreid
     */
    private String appstoreid;
    /**
     * 广告展示时间
     */
    private int showtime;
    /**
     * 广告等级
     */
    private int isgrayav;
    /**
     * 终端
     */
    private String terminal;
    /*
     * 进屏上报
     */
    private List<String> inviewrep;
    /*
     * 点击上报
     */
    private List<String> clickrep;
    /*
     * 展现上报
     */
    private List<String> showrep;
    /*
     * dsp下载安装上报
     */
    private List<String> downloadrep;

}
