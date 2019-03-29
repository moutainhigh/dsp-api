package com.songheng.dsp.ssp.riskcontrol.support;

import com.songheng.dsp.model.flow.BaseFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 风控服务支持类
 * @author: zhangshuai@021.com
 * @date: 2019-03-19 20:31
 **/
public class RiskControlSupport {

    private final static String[] blackListKey = {"ip","advuid","appuid"};

    public static Map<String,String> getBlackListKey(BaseFlow baseFlow){
        Map<String,String> result = new HashMap<>(3);
        result.put(blackListKey[0],baseFlow.getRemoteIp());
        result.put(blackListKey[1],baseFlow.getUserId());
        result.put(blackListKey[2],baseFlow.getAppUserId());
        return result;
    }

    /**
     * 验证 data 是否在黑名单列表中
     */
    public static boolean isInBlackList(Map<String, List<String>> blackListMap,String type,String data){
        return blackListMap.containsKey(type) ? blackListMap.get(type).contains(data) : false;
    }
}
