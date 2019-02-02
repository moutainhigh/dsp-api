package com.songheng.dsp.monopoly;


import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.monopoly.monoply.Monopoly;
import com.songheng.dsp.monopoly.monoply.impl.DefaultMonopoly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 包位置客户端
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 14:25
 **/
public class MonopolyClient {

    private static Map<String, Monopoly> targets = new HashMap<>();

    static{
        targets.put("h5-list",new DefaultMonopoly());
        targets.put("pc",new DefaultMonopoly());
        targets.put("app",new DefaultMonopoly());
    }
    /**
     * 根据不同流量获取不同垄断广告策略key
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }


    public static List<ResponseBean> execute(BaseFlow baseFlow) {
        Monopoly monopoly;
        String key = getDispatchKey(baseFlow);
        if(targets.containsKey(key)){
            monopoly = targets.get(key);
        }else{
            monopoly = new DefaultMonopoly();
        }
        return monopoly.getMonopolyAdvResponseBean(baseFlow);
    }

    public static void main(String[] args) {
        BaseFlow baseFlow = new BaseFlow();
        baseFlow.setTerminal("h5");
        baseFlow.setPgType("list");
        baseFlow.setFlowPositions(StringUtils.strToList("list_1_1,list_1_2,list_1_3,list_1_4"));
        System.out.println("\n========================垄断广告获取到的广告=================================\n"
                +execute(baseFlow));
        System.out.println(baseFlow);
    }
}
