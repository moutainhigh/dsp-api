package com.songheng.dsp.model.dsp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 广告主信息
 * @author: zhangshuai@021.com
 * @date: 2019-04-03 17:44
 **/
@Getter
@Setter
@ToString
public class AdvertiserInfo {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户手机号
     */
    private String userName ;

    /**
     * 现金余额
     */
    private double cashBalance;

    /**
     * 返点余额
     */
    private double rebateBalance;

    /**
     * 总余额
     */
    private double totalBalance;

    /**
     * 行业code
     */
    private String sectorCode;

    /**
     * 终止广告余额阀值 200
     * */
    private double advStopBalance;

    /**
     * 广告限速余额阀值 1000
     */
    private double advLimitBalance;

    /**
     * 广告允许最大损耗金额 0.5
     * */
    private double lossMoney;

    /**
     * 投放中的广告数
     */
    private int deliveryIngAdNum;
    /**
     * cpm平均收费上报的时间(单位s)
     * 用于限速 ,值越大 则限速更严
     */
    private double cpmAvgReportTime;
    /**
     * cpc平均收费上报的时间(单位s)
     * 用于限速 ,值越大 则限速更严
     */
    private double cpcAvgReportTime;

    public AdvertiserInfo(){
        this.advStopBalance = 200;
        this.advLimitBalance = 1000;
        this.lossMoney = 0.5;
        this.deliveryIngAdNum = 1;
        this.cpmAvgReportTime = 30;
        this.cpcAvgReportTime = 5;
    }

}
