package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: luoshaobing
 * @date: 2019/4/1 10:42
 * @description: AdvSspFloorPrice
 */
@Getter
@Setter
@ToString
public class AdvSspFloorPrice {

    /**
     * 广告位ID
     */
    private String slotId;
    /**
     * 渠道ID
     */
    private String qid;
    /**
     * 页码
     */
    private Integer pgnum;
    /**
     * idx
     */
    private Integer idx;
    /**
     * 白天推荐底价
     */
    private Long minCpmDay;
    /**
     * 晚上推荐底价
     */
    private Long minCpmNight;
    /**
     * k值
     */
    private double krate;


}
