package com.songheng.dsp.speed.speed;

import com.songheng.dsp.common.utils.MathUtils;
import com.songheng.dsp.model.client.SpeedClientRequest;
import com.songheng.dsp.model.dsp.AdvertiserInfo;
import com.songheng.dsp.model.materiel.MaterielDirect;
import java.util.Random;

/**
 * @description: 限速服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:08
 **/
public abstract class SpeedService {

    public boolean speed(SpeedClientRequest request){
        MaterielDirect adv = request.getAdv();
        AdvertiserInfo advertiser = request.getAdvertiserInfoMap().get(adv.getUserId());
        //账户余额少于终止余额，则进行终止
        if(accountMoneyStop(advertiser)){
            return true;
        }
        //账户余额 少于 限速余额，则进行限速
        if(MathUtils.compareTo(advertiser.getAdvLimitBalance(),advertiser.getTotalBalance())>=0){
            if(limit(request)){
                return true;
            }
        }



        return false;
    }


    /**
     *  账户余额不足 终止投放
     * @param advertiser
     * @return
     */
    protected boolean accountMoneyStop(AdvertiserInfo advertiser){
        //广告主不存在
        if(advertiser == null){
            return true;
        }
        //终止投放的余额阀值 超过 用户总余额 则 不展现广告
        if(MathUtils.compareTo(advertiser.getAdvStopBalance(),advertiser.getTotalBalance())>=0){
            return true;
        }
        return false;
    }

    /**
     * 计算展现的概率
     * @return
     */
    protected static boolean limit(SpeedClientRequest request){
        //当前服务总QPS
        long QPS = request.getBaseFlow().getCurrQps();
        //广告信息
        MaterielDirect adv = request.getAdv();
        //广告广告主信息
        AdvertiserInfo advertiser = request.getAdvertiserInfoMap().get(adv.getUserId());

        //单次曝光或者点击时 用户的出价
        double unitPrice = MathUtils.div(adv.getBidPrice(),100000);
        //广告主维度的平均上报时间
        double advertiserAvgReportTime = "CPM".equalsIgnoreCase(adv.getChargeway()) ? advertiser.getCpmAvgReportTime()
                : advertiser.getCpcAvgReportTime();
        //平均上报时长
        double avgReportTime = adv.getAdvReportTime() != 0 ? adv.getAdvReportTime() : (
               advertiserAvgReportTime != 0 ? advertiserAvgReportTime : 5
        );
        //单位时间内（s）该用户最大可能消耗金额
        double maxCostByUnitTime = MathUtils.mul(MathUtils.mul(avgReportTime,unitPrice),advertiser.getDeliveryIngAdNum());
        //能够允许最大的损耗金额
        double lossMoney = advertiser.getLossMoney();
        double probability = MathUtils.div(lossMoney,maxCostByUnitTime,5);
        // 概率为0则按1/100000的概率出
        probability = probability == 0 ?  0.00001 : probability;
        long seedNum = MathUtils.doubleToLong(probability,100000);
        long randNum = new Random().nextInt(100000)+1;
        return (randNum < seedNum) ? false : true;
    }
}
