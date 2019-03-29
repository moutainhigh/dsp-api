package com.songheng.dsp.shield.shield;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.model.client.ShieldClientRequest;
import com.songheng.dsp.model.enums.AdLevel;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.support.ShieldSupport;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 屏蔽服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:19
 **/
public abstract class ShieldService {


    public void shield(ShieldClientRequest request){
        JSONObject json = ShieldSupport.parseJsonStr(request.getShiledJson());
        BaseFlow baseFlow = request.getBaseFlow();
        List<MaterielDirect> advList = request.getAdvList();
        Iterator<MaterielDirect> iterator = advList.iterator();
        while(iterator.hasNext()){
            MaterielDirect next = iterator.next();
            String sectorName = next.getSectorName();
            //广告属性中屏蔽地域
            if(ShieldSupport.validateArea(next.getShieldArea(),baseFlow)){
                iterator.remove();
                continue;
            }
            //获取广告等级名
            String levelName = AdLevel.getLevelNameByValue(next.getAdlever());
            if(json.containsKey(levelName)){
                //获取该等级下的屏蔽策略
                JSONObject levelJson = json.getJSONObject(levelName);
                //正向屏蔽策略:针对行业
                if(positiveShield(levelJson,sectorName,baseFlow)){
                    iterator.remove();
                    continue;
                }
                //反向屏蔽策略 :针对行业
                if(oppositionShield(levelJson,sectorName,baseFlow)){
                    iterator.remove();
                    continue;
                }
            }
        }
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
