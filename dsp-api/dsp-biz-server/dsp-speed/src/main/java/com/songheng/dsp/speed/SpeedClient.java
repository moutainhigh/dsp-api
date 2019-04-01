package com.songheng.dsp.speed;

import com.songheng.dsp.model.client.SpeedClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.speed.speed.SpeedService;
import com.songheng.dsp.speed.speed.impl.DefaultSpeedService;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 限速服务客户端
 * @author: zhangshuai@021.com
 * @date: 2019-03-29 20:12
 **/
public class SpeedClient {

    private static Map<String, SpeedService> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("default",new DefaultSpeedService());
    }
    /**
     * 根据不同流量获取不同的服务实现
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }
    /**
     * 执行限速服务
     * */
    public static boolean execute(SpeedClientRequest request){
        String key = getDispatchKey(request.getBaseFlow());
        SpeedService service = realize.containsKey(key) ? realize.get(key) : new DefaultSpeedService();
        return service.speed(request);
    }
}
