package com.songheng.dsp.shield;

import com.songheng.dsp.common.utils.serialize.FastJsonUtils;
import com.songheng.dsp.model.client.ShieldClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.shield.ShieldService;
import com.songheng.dsp.shield.shield.impl.DefaultShieldService;
import com.songheng.dsp.shield.shield.impl.NoneShieldService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 屏蔽模块
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:06
 **/
public class ShieldClient {
    private static Map<String, ShieldService> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("default",new DefaultShieldService());
        realize.put("pc_ny",new NoneShieldService());
    }
    /**
     * 根据不同流量获取不同垄断广告策略key
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }
    /**
     * 执行屏蔽服务
     * */
    public static void execute(ShieldClientRequest request){
        String key = getDispatchKey(request.getBaseFlow());
        ShieldService shieldServer = realize.containsKey(key) ? realize.get(key) : new DefaultShieldService();
        shieldServer.shield(request);
    }


    public static void main(String[] args) {
        ShieldClientRequest request = new ShieldClientRequest();

        BaseFlow baseFlow = new BaseFlow();
        baseFlow.setProvince("上海");
        baseFlow.setCity("上海");
        baseFlow.setTerminal("pc");
        baseFlow.setPgType("ny");
        request.setBaseFlow(baseFlow);

        List<MaterielDirect> advList = new ArrayList<>();
        MaterielDirect adv1 = new MaterielDirect();
        adv1.setSectorName("网赚");
        adv1.setAdlever(1);
        advList.add(adv1);

        MaterielDirect adv2 = new MaterielDirect();
        adv2.setSectorName("网游");
        adv2.setAdlever(1);
        advList.add(adv2);

        String json = "{\"D\":{\"0\":{\"网赚\":{\"time\":\"10:00:00-23:59:00\",\"area\":\"上海,北京\"}},\"1\":{\"网赚\":{\"time\":\"08:00:00-10:00:00\",\"area\":\"上海,北京\"}}},\"C\":{\"0\":{\"all\":{\"time\":\"10:00:00-23:59:00\",\"area\":\"上海,北京\"}},\"1\":{\"网赚\":{\"time\":\"08:00:00-10:00:00\",\"area\":\"上海,北京\"}}}}";
        System.out.println(FastJsonUtils.toJsonObject(json));
        request.setShiledJson(json);
        request.setAdvList(advList);

        execute(request);

        System.out.println(advList.size());
    }

}
