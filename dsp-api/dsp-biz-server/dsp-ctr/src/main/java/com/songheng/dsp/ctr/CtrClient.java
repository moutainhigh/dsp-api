package com.songheng.dsp.ctr;

import com.songheng.dsp.ctr.ctr.CtrService;
import com.songheng.dsp.ctr.ctr.impl.DefaultCtrService;
import com.songheng.dsp.model.client.CtrClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 点击率预测及转化
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 09:49
 **/
public class CtrClient {
    private static Map<String, CtrService> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("default",new DefaultCtrService());
    }
    /**
     * 根据不同流量获取不同的服务
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }
    /**
     * 执行点击率预测转化服务
     * */
    public static void execute(CtrClientRequest request){
        String key = getDispatchKey(request.getBaseFlow());
        CtrService service = realize.containsKey(key) ? realize.get(key) : new DefaultCtrService();
        service.predictCtr(request);
    }
}
