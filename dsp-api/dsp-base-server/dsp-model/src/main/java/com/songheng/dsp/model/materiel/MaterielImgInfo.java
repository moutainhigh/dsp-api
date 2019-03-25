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
public class MaterielImgInfo {
    private String src;
    private int width;
    private int height;

    public MaterielImgInfo(){}

    public MaterielImgInfo(String src){
        this.src = src;
    }

    public MaterielImgInfo(String src, int width, int height){
        this.src = src;
        this.width = width;
        this.height = height;
    }
}
