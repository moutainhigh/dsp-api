package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 物料图片信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 20:44
 **/
@Getter
@Setter
public class MaterielImgInfo implements java.io.Serializable{

    public static final long serialVersionUID = 586204821434534168L;
    private String src;
    private int width;
    private int height;
    //图片类型 0:主要图片 1:辅助图片 2:icon图标
    private int type;

    public MaterielImgInfo(){}

    public MaterielImgInfo(String src,int type) {
        this.src = src;
        this.type = type;
    }

    public MaterielImgInfo(String src, int width, int height,int type){
        this.src = src;
        this.width = width;
        this.height = height;
        this.type = type;
    }
}
