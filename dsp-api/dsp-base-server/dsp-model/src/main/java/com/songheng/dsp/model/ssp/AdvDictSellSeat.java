package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:13
 * @description:
 */
@Getter
@Setter
@ToString
public class AdvDictSellSeat implements Serializable {
    private static final long serialVersionUID = 4596367528052697665L;
    /**
     * 售卖位置id,随机ID
     */
    private String sellSeatId;
    /**
     * 售卖位置名称 信息流/内页
     */
    private String shellSeatName;
    /**
     * 广告位Ids ","拼接
     */
    private String slotIds;
    /**
     * 所属终端:h5,app/pc
     */
    private String terminals;
    /**
     * 售卖优先级 0:打底竞价 1:自动竞价 2:优先竞价
     */
    private int priority;
    /**
     * 支持的样式Ids
     */
    private String styleIds;
    /**
     * 是否设置权限；0:没有权限 1:有权限
     */
    private int perType;
    /**
     * 权限code
     */
    private String perCode;

}
