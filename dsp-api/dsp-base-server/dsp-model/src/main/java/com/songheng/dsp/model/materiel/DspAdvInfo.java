package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DspAdvInfo {
    private String type;
    private String topic;
    private String source;
    private String date;
    private String url;
    private String ispicnews;
    private List<Img> miniimg;
    private String miniimg_size;
    private String picnums;
    private List<Img> lbimg;
    private String isadv;
    private transient String adv_id;
    private String deliveryid;
    private String accurateurl;
    private int idx;
    private long realcost;
    private long originalcost;
    private String clickbackurl;
    private String showbackurl;
    private String inviewbackurl;
    private String platform;
    private String chargeway;
    private String adorder;
    private String adStyle;
    private int isdownload;
    private String summary;
    private transient String totalnum;
    private transient String refreshnum;
    private transient String extra5;
    private String conversionbackurl;
    private String packagename;
    private String subadstyle;
    private int switchTag;
    private int userId;
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
    }
}