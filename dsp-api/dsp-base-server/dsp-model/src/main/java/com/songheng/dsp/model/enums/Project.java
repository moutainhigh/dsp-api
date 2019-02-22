package com.songheng.dsp.model.enums;

import lombok.Getter;

/**
 * @author zhangshuai@021.com
 * 项目信息
 * */
@Getter
public enum Project {
    /**
     * h5项目
     * */
    H5("partner","h5","mobile",""),
    /**
     * app项目
     * */
    APP("admethod","app","mobile",""),
    /**
     * pc项目
     * */
    PC("dfpcitv","pc","computer","");

    /**
     * 项目名称
     * */
    private String projectName;
    /**
     * 项目终端
     * */
    private String terminal;

    /**
     * 设备类型
     * */
    private String deviceType;

    /**
     * 项目描述
     * */
    private String describe;

    Project(String projectName,String terminal,String deviceType,String describe){
        this.projectName = projectName;
        this.terminal = terminal;
        this.deviceType = deviceType;
        this.describe = describe;
    }
    /**
     * 是否h5项目
     * */
    public static boolean isH5(String terminal){
        return H5.getTerminal().equalsIgnoreCase(terminal);
    }
    /**
     * 是否h5项目
     * */
    public static boolean isApp(String terminal){
        return APP.getTerminal().equalsIgnoreCase(terminal);
    }
    /**
     * 是否h5项目
     * */
    public static boolean isPc(String terminal){
        return PC.getTerminal().equalsIgnoreCase(terminal);
    }
}
