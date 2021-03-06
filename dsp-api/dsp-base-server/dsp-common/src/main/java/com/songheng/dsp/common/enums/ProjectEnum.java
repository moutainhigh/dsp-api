package com.songheng.dsp.common.enums;

import lombok.Getter;

/**
 * @author zhangshuai@021.com
 * 项目信息
 * */
@Getter
public enum ProjectEnum {
    /**
     * h5项目
     * */
    H5("partner","h5",new String[]{"CLUSTER_B"}, new String[]{"advertise","ctrmodel"},"mobile",""),
    /**
     * app项目
     * */
    APP("admethod","app",new String[]{"CLUSTER_B"},new String[]{"advertise"},"mobile",""),
    /**
     * pc项目
     * */
    PC("dfpcitv","pc",new String[]{"CLUSTER_E"},new String[]{"advertise"},"computer",""),

    /**
     * dspdatalog项目
     */
    DSPDATALOG("dspdatalog","log",new String[]{"CLUSTER_B","CLUSTER_E"},new String[]{"advertise"},"",""),
    /**
     * datacenter项目
     */
    DATACENTER("datacenter","dc",new String[]{"CLUSTER_B","CLUSTER_E"},new String[]{"advertise","ctrmodel"},"",""),
    /**
     * dsp-web项目
     */
    DSPWEB("dspweb","web",new String[]{"CLUSTER_B","CLUSTER_E"},new String[]{"advertise"},"",""),
    /**
     * cloudcontrol 项目
     */
    CLOUDCONTROL("cloudcontrol", "cloudcontrol", new String[]{"CLUSTER_B"}, new String[]{"cloudcontrol"},"", "");

    /**
     * 项目名称
     * */
    private String projectName;
    /**
     * 项目终端
     * */
    private String terminal;
    /**
     * 所属集群
     */
    private String[] cluster;
    /**
     * 数据源
     */
    private String[] ds;
    /**
     * 设备类型
     * */
    private String deviceType;

    /**
     * 项目描述
     * */
    private String describe;

    ProjectEnum(String projectName, String terminal, String[] cluster, String[] ds, String deviceType, String describe){
        this.projectName = projectName;
        this.terminal = terminal;
        this.cluster = cluster;
        this.ds = ds;
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
     * 是否app项目
     * */
    public static boolean isApp(String terminal){
        return APP.getTerminal().equalsIgnoreCase(terminal);
    }
    /**
     * 是否pc项目
     * */
    public static boolean isPc(String terminal){
        return PC.getTerminal().equalsIgnoreCase(terminal);
    }

}
