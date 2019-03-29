package com.songheng.dsp.shield.shield;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.model.client.ShieldClientRequest;
import com.songheng.dsp.model.enums.AdLevel;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.support.ShieldSupport;

/**
 * @description: 屏蔽服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:19
 **/
public abstract class ShieldService {


    /**
     * 验证这个广告是否需要屏蔽
     * @param request
     * @return
     */
    public boolean shield(ShieldClientRequest request){
        JSONObject json = ShieldSupport.parseJsonStr(request.getShiledJson());
        BaseFlow baseFlow = request.getBaseFlow();

        MaterielDirect next = request.getAdv();
        String sectorName = next.getSectorName();
        //广告属性中屏蔽地域
        if(ShieldSupport.validateArea(next.getShieldArea(),baseFlow)){
           return true;
        }
        //获取广告等级名
        String levelName = AdLevel.getLevelNameByValue(next.getAdlever());
        if(json.containsKey(levelName)){
            //获取该等级下的屏蔽策略
            JSONObject levelJson = json.getJSONObject(levelName);
            //正向屏蔽策略:针对行业
            if(positiveShield(levelJson,sectorName,baseFlow)){
                return true;
            }
            //反向屏蔽策略 :针对行业
            if(oppositionShield(levelJson,sectorName,baseFlow)){
                return true;
            }
        }
        return false;
    }

    /**
     * 反向屏蔽策略 :针对行业
     * @param levelJson
     * @param sectorName
     * @param baseFlow
     * @return
     */
    protected abstract boolean oppositionShield(JSONObject levelJson, String sectorName, BaseFlow baseFlow);

    /**
     * 正向屏蔽策略:针对行业
     * @param levelJson
     * @param sectorName
     * @param baseFlow
     * @return
     */
    protected abstract boolean positiveShield(JSONObject levelJson, String sectorName, BaseFlow baseFlow);


}
