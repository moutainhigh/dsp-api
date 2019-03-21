package com.songheng.dsp.model.ssp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author: luoshaobing
 * @date: 2019/3/8 17:22
 * @description:
 */
@Getter
@Setter
@ToString
public class AdvDictAdStyle implements Serializable {
    private static final long serialVersionUID = 2786879525949684430L;
    /**
     * 样式Id
     */
    private String styleId;
    /**
     * 样式类型:1.图文广告,2.软文广告,3.视频广告,4.组合广告
     */
    private String styleType;
    /**
     * 样式名称;大图,单图,三图
     */
    private String styleName;
    /**
     * api参数:100,101,102
     */
    private String styleValue;
    /**
     * 图片宽
     */
    private int width;
    /**
     * 图片高
     */
    private int height;
    /**
     * 图片数量
     */
    private int number;
    /**
     * icon宽
     */
    private int iconwidth;
    /**
     * icon高
     */
    private int iconheight;
    /**
     * 是否设置权限；0:没有权限 1:有权限
     */
    private int perType;
    /**
     * 权限code
     */
    private String perCode;
    /**
     * 正文字符长度
     */
    private int txtlen;
    /**
     * 副文字字符长度
     */
    private int subtxtlen;
    /**
     * 支持的格式;jpg,gif,png,swf,mp4
     */
    private String imgFormat;
    /**
     * 该样式是否支持adx 1:支持 0:不支持
     */
    private int supportadx;
    /**
     * 备注信息
     */
    private String remark;
}
