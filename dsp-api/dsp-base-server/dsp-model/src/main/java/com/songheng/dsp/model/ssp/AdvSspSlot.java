package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:03
 * @description:
 */
@Getter
@Setter
@ToString
public class AdvSspSlot extends AdvSspApplication implements Serializable {
    private static final long serialVersionUID = 8342668456742305518L;
    /**
     * 广告位Id,随机ID
     */
    private String slotId;
    /**
     * 所属应用Id;ssp_application.id
     */
    private int sspAppId;
    /**
     * 售卖位置id,随机ID
     */
    private String sellSeatId;
    /**
     * 广告位名称:信息流,详情页,开屏,视频信息流,头条站_内页_左1
     */
    private String slotName;
    /**
     * 页面类型,list/detail
     */
    private String pgtype;
    /**
     * 广告位描述
     */
    private String slotDesc;
    /**
     * 广告位图片示列
     */
    private String slotImgs;
    /**
     * 广告位一次请求的默认广告数量,针对于信息流类型广告
     */
    private int adnum;
    /**
     * 位置的排序
     */
    private int slotSort;
    /**
     * 支持的样式Id
     */
    private String styleIds;
    /**
     * 默认底价
     */
    private double floorPrice;
    /**
     * 是否信息流广告
     * */
    private boolean isFeeds;
}
