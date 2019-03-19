package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 16:43
 * @description:
 */
@Getter
@Setter
@ToString
public class AdvSspApplication implements java.io.Serializable{
    public static final long serialVersionUID = 2862012382137774168L;
    /**
     * 包名或网页地址;com.songheng.eastnews , http://mini.eastday.com
     */
    private String appId;
    /**
     * 应用名称(站点) ;ttz/ittz/DFTT
     */
    private String appName;
    /**
     * 应用类型;新闻资讯,金融,p2p
     */
    private String appType;
    /**
     * 终端:h5/app/pc
     */
    private String terminal;
    /**
     * 对接负责人
     */
    private String bosshead;
    /**
     * 是否进行渠道白名单验证 1:验证 0:不验证
     */
    private int validateQid;
    /**
     * 备注信息
     */
    private String remark;

}
