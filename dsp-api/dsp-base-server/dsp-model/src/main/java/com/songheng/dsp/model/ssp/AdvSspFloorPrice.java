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
     * 请求个数
     */
    private String pgnum;
    /**
     * idx
     */
    private String idx;
    /**
     * 白天推荐底价
     */
    private String minCpmDay;
    /**
     * 晚上推荐底价
     */
    private String minCpmNight;
    /**
     * k值
     */
    private String krate;


}
