package com.songheng.dsp.model.shield;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 16:02
 * @description: AdvDictShield
 */
@Getter
@Setter
@ToString
public class AdvDictShield implements Serializable {

    private static final long serialVersionUID = -1349207762122191119L;

    /**
     * 渠道号
     */
    private String qid;

    /**
     * 渠道标签
     */
    private String qidFlag;

    /**
     * 站点 ,拼接
     */
    private String appIds;

    /**
     * 广告位 ,拼接
     */
    private String tagIds;

    /**
     * 屏蔽政策 json
     */
    private String shieldJson;

    /**
     * 备注信息
     */
    private String remark;

}
