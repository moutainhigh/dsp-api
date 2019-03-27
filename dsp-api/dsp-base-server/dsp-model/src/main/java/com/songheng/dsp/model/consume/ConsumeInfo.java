package com.songheng.dsp.model.consume;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @description: 广告消耗数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-27 10:34
 **/
@Getter
@Setter
public class ConsumeInfo implements Serializable {

    /**
     * 当前计划消耗的总金额
     * */
    private long totalAmount;

    /**
     * 当前计划消耗的日金额
     * */
    private long dayAmount;

    /**
     * 当前计划总浏览量
     * */
    private long totalPv;
    /**
     * 当前计划总点击量
     * */
    private long totalClick;
    /**
     * 当前用户对该计划最后的展现时间(ms)
     * */
    private long lastshowtime;
    /**
     * 当前用户对该计划浏览的次数
     * */
    private long userPv;
    /**
     * 当前用户对该计划点击的次数
     * */
    private long userClick;

    /**
     * 当前用户对该计划所属行业浏览的次数
     * */
    private long userHyPv;

    /**
     * 当前用户对该计划所属行业的点击的次数
     * */
    private long userHyClick;

    public ConsumeInfo(){
        this.lastshowtime = System.currentTimeMillis();
    }

}
