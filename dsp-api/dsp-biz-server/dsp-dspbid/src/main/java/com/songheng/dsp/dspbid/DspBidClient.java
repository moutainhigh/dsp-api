package com.songheng.dsp.dspbid;

import com.songheng.dsp.dspbid.dspbid.DspBidService;
import com.songheng.dsp.dspbid.dspbid.impl.DefaultDspBidService;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.client.DspBidClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: DSP竞价客户端
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 11:48
 **/
public class DspBidClient {
    private static Map<String, DspBidService> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("pc",new DefaultDspBidService());
        realize.put("app",new DefaultDspBidService());
        realize.put("h5",new DefaultDspBidService());
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
    public static ResponseBean execute(DspBidClientRequest request) {
        try {
            String key = getDispatchKey(request.getBaseFlow());
            DspBidService service = realize.containsKey(key) ? realize.get(key) : new DefaultDspBidService();
            return service.getDspResponseBean(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
