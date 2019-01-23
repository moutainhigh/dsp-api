package com.songheng.dsp.common.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author: luoshaobing
 * @date: 2019/1/23 11:54
 * @description:
 */
public class AdplatformAdShowHistory {

    private String hisId;

    private String adId;

    private Timestamp startTime;

    private Date endTime;

    private Integer statusflag;

    private Long money;

    private Double unitprice;

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatusflag() {
        return statusflag;
    }

    public void setStatusflag(Integer statusflag) {
        this.statusflag = statusflag;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    @Override
    public String toString() {
        return "AdplatformAdShowHistory{" +
                "hisId='" + hisId + '\'' +
                ", adId='" + adId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", statusflag=" + statusflag +
                ", money=" + money +
                ", unitprice=" + unitprice +
                '}';
    }
}
