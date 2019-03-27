package com.songheng.dsp.model.materiel;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 物料的预算信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-26 11:52
 **/
@Getter
@Setter
public class MaterielBudget extends MaterielBaseInfo implements java.io.Serializable{

    public static final long serialVersionUID = 386204821357724168L;

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
