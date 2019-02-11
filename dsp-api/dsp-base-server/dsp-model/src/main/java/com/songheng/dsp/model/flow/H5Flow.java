package com.songheng.dsp.model.flow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class H5Flow extends BaseFlow{
    /**站点**/
    private String site;
    /**渠道**/
    private String qid;
    /**栏位**/
    private String type;
    /**用户标识**/
    private String uid;
    /**imei**/
    private String imei;
    /**第几页**/
    private String pgnum;
    /**广告数**/
    private String adnum;
    /**广告样式**/
    private String adtype;
    /**阅读历史**/
    private String readhistory;
    /**个像信息*/
    private String hbasejson;

}
