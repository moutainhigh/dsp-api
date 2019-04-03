package com.songheng.dsp.model.dsp;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: 广告主信息
 * @author: zhangshuai@021.com
 * @date: 2019-04-03 17:44
 **/
@Getter
@Setter
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


}
