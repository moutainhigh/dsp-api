package com.songheng.dsp.shield.shield.impl;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.enums.ShieldStrategy;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.shield.shield.ShieldService;
import com.songheng.dsp.shield.support.ShieldSupport;
import java.util.Iterator;
import java.util.Set;

/**
 * @description: 默认的屏蔽服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:28
 **/
public class DefaultShieldService extends ShieldService {

    /**
     * 正向屏蔽
     * @param levelJson
     * @param sectorName
     * @param baseFlow
     * @return
     */
    @Override
    protected boolean positiveShield(JSONObject levelJson,String sectorName,BaseFlow baseFlow){
        //正向屏蔽策略:针对行业
        String positiveKey = ShieldStrategy.getPositiveKey(levelJson);
        if(!positiveKey.equalsIgnoreCase(ShieldStrategy.UNKNOW.getName())){
            JSONObject positive = levelJson.getJSONObject(positiveKey);
            //获取行业的屏蔽
            JSONObject sectorJson = positive.containsKey(sectorName) ?
                    positive.getJSONObject(sectorName) : positive.getJSONObject("all");
            if(null!=sectorJson){
                //验证时间和地域
                if(ShieldSupport.validateTime(sectorJson.getString("time"))
                        && ShieldSupport.validateArea(sectorJson.getString("area"),baseFlow)){
                   return true;
                }
            }
        }
        return false;
    }

    /**
     * 反向屏蔽
     * @param levelJson
     * @param sectorName
     * @param baseFlow
     * @return
     */
    @Override
    protected boolean oppositionShield(JSONObject levelJson,String sectorName,BaseFlow baseFlow){
        String oppositionKey = ShieldStrategy.getOppositionKey(levelJson);
        if(!oppositionKey.equalsIgnoreCase(ShieldStrategy.UNKNOW.getName())){
            JSONObject opposition = levelJson.getJSONObject(oppositionKey);
            Set<String> keys = opposition.keySet();
            Iterator<String> sectorIterator = keys.iterator();
            while(sectorIterator.hasNext()){
                String sector = sectorIterator.next();
                if(StringUtils.isNullOrEmpty(sector) || StringUtils.isNullOrEmpty(sectorName)){
                    continue;
                }
                //行业不相同则进行屏蔽
                if(!sector.equals(sectorName)){
                    JSONObject sectorJson = opposition.getJSONObject(sector);
                    //验证时间和地域
                    if(ShieldSupport.validateTime(sectorJson.getString("time"))
                            && ShieldSupport.validateArea(sectorJson.getString("area"),baseFlow)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
