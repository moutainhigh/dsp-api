package com.songheng.dsp.model.enums;

import lombok.Getter;

/**
 * @author zhangshuai@021.com
 * 项目描述
 * */
@Getter
public enum Project {

    WAP("partner","wap","h5项目"),
    APP("admethod","app","app项目"),
    PC("dfpcitv","pc","pc项目");

    /**
     * 项目名称
     * */
    private String projectName;
    /**
     * 项目别名
     * */
    private String alis;

    /**
     * 项目描述
     * */
    private String describe;

    Project(String projectName,String alis,String describe){
        this.projectName = projectName;
        this.alis = alis;
        this.describe = describe;
    }


}
