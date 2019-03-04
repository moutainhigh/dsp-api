package com.songheng.dsp.model.dict;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 10:35
 * @description: DSP广告位置
 */
@Getter
@Setter
@ToString
public class AdPosition {

    /**
     * 广告位ID,375AF838BA5611E796A46C92BF16AE06
     */
    private String location_id;
    /**
     * 广告位名称,360osapp_360osapp_detail_1_1
     */
    private String location_name;
    /**
     * 原始广告位名称,detail_1_1
     */
    private String location_name_original;
    /**
     * A,B类广告底价
     */
    private float cpm_start_price;
    /**
     * C,D,E类广告底价
     */
    private float cpm_start_price_1;
    /**
     * C,D,E类广告底价(时间范围)
     */
    private float cpm_start_price_2;
    /**
     * F类广告底价
     */
    private float cpm_start_price_f;
    /**
     * 图片描述
     */
    private String describ;
    /**
     * 广告位上限制的行业
     */
    private String block_vocation;
    /**
     * 广告位上支持的素材(物料)的文件格式["jpg","gif","swf","png"]
     */
    private String allow_file;
    /**
     * 广告位的屏次 U 未设置，Y 首屏，N 非首屏，T discuz弹窗
     */
    private String screen;

    private String bigstyle;

    private String smallstyle;

    private String groupstyle;

    private String bannerstyle;

    private String wzlstyle;

    private String iconstyle;
    /**
     * 是否是打底广告？ 0不是, 1是
     */
    private String isbottomad;

    private float c_ad_price;
    /**
     * K值
     */
    private float k_rate;
    /**
     * app/h5/pc
     */
    private String type;

}
