package com.songheng.dsp.dsprtb;

import com.songheng.dsp.dsprtb.dsprtb.DspRtbService;
import com.songheng.dsp.dsprtb.dsprtb.impl.DefaultDspRtbService;
import com.songheng.dsp.model.client.DspRtbClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: DSP实时竞价客户端
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 10:31
 **/
public class DspRtbClient {

    private static Map<String, DspRtbService> realize = new HashMap<>(5);

    /**
     *注册具体实现
     **/
    static{
        realize.put("default",new DefaultDspRtbService());
    }
    /**
     * 根据不同流量获取不同实现的key
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }

    /**
     * 执行竞价请求
     * @param request 请求信息
     * @return 响应信息
     */
    public static MaterielDirect execute(DspRtbClientRequest request) {
        try {
            String key = getDispatchKey(request.getBaseFlow());
            DspRtbService service = realize.containsKey(key) ? realize.get(key) : new DefaultDspRtbService();
            return service.dspRtb(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
