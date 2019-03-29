package com.songheng.dsp.match;

import com.songheng.dsp.match.match.MatchService;
import com.songheng.dsp.match.match.impl.DefaultMatchService;
import com.songheng.dsp.match.match.impl.NoneMatchService;
import com.songheng.dsp.model.client.MatchClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 广告匹配管理类,遍历所有广告匹配业务
 * @author: zhangshuai@021.com
 **/
public class AdvMatchClient {

    private static Map<String, MatchService> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("default",new DefaultMatchService());
        realize.put("test",new NoneMatchService());
    }
    /**
     * 根据不同流量获取不同垄断广告策略key
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }
    /**
     * 执行匹配服务
     * */
    public static void execute(MatchClientRequest request){
        String key = getDispatchKey(request.getBaseFlow());
        MatchService server = realize.containsKey(key) ? realize.get(key) : new DefaultMatchService();
        server.matchAdvInfo(request);
    }

}
