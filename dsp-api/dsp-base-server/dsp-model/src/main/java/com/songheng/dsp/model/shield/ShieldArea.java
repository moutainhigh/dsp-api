package com.songheng.dsp.model.shield;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/19 15:50
 * @description: 地域屏蔽
 */
@Getter
@Setter
@ToString
public class ShieldArea implements Serializable {

    private static final long serialVersionUID = 3602059365758341070L;

    private String shielId;
    private String terminal;
    private String shielType;
    private String site;
    private String qid;
    private String adPosition;
    private String adLevel;
    private String adStyle;
    private String pageType;
    private String pageNum;
    private String area;
    private String areaTime;
    private String cycleStartTime;
    private String cycleEndTime;
    private String planStartTime;
    private String planEndTime;
    private String qidState;
    private String siteState;
    private String creatTime;
    private String updateTime;
    private String operator;
    private String other;
    private String isReverse;
    private String sectors;

}
