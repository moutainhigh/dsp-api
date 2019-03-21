package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/26 15:26
 * @description: DspAdvInfo扩展类
 */
@Getter
@Setter
@ToString
public class DspAdvExtend extends DspAdvInfo implements Serializable {
    private static final long serialVersionUID = 2531129782125563638L;
    /**
     * dspId
     */
    private String dspId;
    /**
     * 图片宽度
     */
    private Integer imgWidth;
    /**
     * 图片高度
     */
    private Integer imgHeight;
    /**
     * 图片路径 imgPath
     */
    private String imgPath;
    /**
     * 图片路径 img1Path, img2Path, img3Path
     */
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
    /**
     * 点击上报
     */
    private List<String> clickrep;
    /**
     * 展现上报
     */
    private List<String> showrep;
    /**
     * dsp下载安装上报
     */
    private List<String> downloadrep;
    /**
     * 0:普通广告 1:视频游戏广告  2:gif动图 -1:文字链
     */
    private int adtype;
    /**
     * 推广样式类型
     */
    private String bigpic;
    /**
     * 控价
     */
    private double realunitprice;

    /**
     * 获取控价
     * 返回 原值*100
     * @return
     */
    public long getRealUnitPrice(){
        //(cpm/1000)*100000——为保证单次展现价格为整数，扩大倍数乘以十万
        return Double.valueOf(this.realunitprice*100).longValue();
    }
}
