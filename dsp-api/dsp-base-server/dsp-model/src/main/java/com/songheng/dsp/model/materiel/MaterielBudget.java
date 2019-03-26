package com.songheng.dsp.model.materiel;

/**
 * @description: 物料的预算信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-26 11:52
 **/
public class MaterielBudget {
    /**
     * 素材投放Id
     * */
    private String deliveryId;
    /**
     * 广告组id
     * */
    private int groupId;
    /**
     * 广告主Id
     * */
    private int userId;
    /**
     * 广告组预算
     * */
    private double groupBudget;
    /**
     * 广告计划总预算/日预算
     * money
     * */
    private double planBudget;
    /**
     * 预算类型 1:总预算 0:日预算
     * */
    private int yusuanType;
    /**
     * 收费方式
     * */
    private String chargeway;

    /**
     * 广告主 出价 unitprice
     * */
    private long bidPrice;
    /**
     * 售价
     * */
    private long sellPrice;
    /**
     * 二价
     * */
    private long secondPrice;

    /**
     * 理论pv
     * */
    private long calshow;


}