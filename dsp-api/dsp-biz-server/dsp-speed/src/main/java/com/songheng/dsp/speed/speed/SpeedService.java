package com.songheng.dsp.speed.speed;

import com.songheng.dsp.model.client.SpeedClientRequest;
import com.songheng.dsp.model.materiel.MaterielDirect;

/**
 * @description: 限速服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:08
 **/
public abstract class SpeedService {

    public boolean speed(SpeedClientRequest request){
        MaterielDirect adv = request.getAdv();
        //账户少于一定的余额停止投放
        if(accountMoneyStop(request)){
            return true;
        }
        //账户少于一定余额限速
        if(accountMoneyLimit(request)){
            return true;
        }
        //TODO 其他限速...


        return false;
    }

    /**
     * 账户余额不足 限速
     * @param request
     * @return
     */
    protected abstract boolean accountMoneyLimit(SpeedClientRequest request);

    /**
     * 账户余额不足 终止投放
     * @param request
     * @return
     */
    protected abstract  boolean accountMoneyStop(SpeedClientRequest request);
}
