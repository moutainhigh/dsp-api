package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:00
 * @description:
 */
@Getter
@Setter
@ToString
public class AdvSspQid extends AdvSspApplication {
    /**
     * 所属应用Id;ssp_application.id
     */
    private int sspAppId;
    /**
     * 渠道号
     */
    private String qid;
    /**
     * 渠道code
     */
    private String qidCode;
}
