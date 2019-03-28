package com.songheng.dsp.shield.shield.impl;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.ShiledClientRequest;
import com.songheng.dsp.model.enums.AdLevel;
import com.songheng.dsp.model.enums.ShieldStrategy;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.shield.ShieldServer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @description: 默认的屏蔽服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:28
 **/
public class DefaultShieldServer extends ShieldServer {
    @Override
    protected void shieldSpecial(ShiledClientRequest request, JSONObject json) {
        BaseFlow baseFlow = request.getBaseFlow();
        List<MaterielDirect> advList = request.getAdvList();
        Iterator<MaterielDirect> iterator = advList.iterator();
        while(iterator.hasNext()){
            MaterielDirect next = iterator.next();
            String sectorName = next.getSectorName();
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

    @Override
    protected void shieldPublic(ShiledClientRequest request,JSONObject json) {
        this.shieldSpecial(request,json);
    }


    /**
     * 正向屏蔽
     * @param levelJson
     * @param sectorName
     * @param baseFlow
     * @return
     */
    private boolean positiveShield(JSONObject levelJson,String sectorName,BaseFlow baseFlow){
        //正向屏蔽策略:针对行业
        String positiveKey = ShieldStrategy.getPositiveKey(levelJson);
        if(!positiveKey.equalsIgnoreCase(ShieldStrategy.UNKNOW.getName())){
            JSONObject positive = levelJson.getJSONObject(positiveKey);
            //获取行业的屏蔽
            JSONObject sectorJson = positive.containsKey(sectorName) ?
                    positive.getJSONObject(sectorName) : positive.getJSONObject("all");
            if(null!=sectorJson){
                //验证时间和地域
                if(validateTime(sectorJson.getString("time")) && validateArea(sectorJson.getString("area"),baseFlow)){
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
    private boolean oppositionShield(JSONObject levelJson,String sectorName,BaseFlow baseFlow){
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
                    if(validateTime(sectorJson.getString("time")) && validateArea(sectorJson.getString("area"),baseFlow)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
