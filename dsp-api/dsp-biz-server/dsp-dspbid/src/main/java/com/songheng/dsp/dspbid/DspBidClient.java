package com.songheng.dsp.dspbid;

import com.songheng.dsp.dspbid.dspbid.DspBidServer;
import com.songheng.dsp.dspbid.dspbid.impl.DefaultDspBidServer;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.flow.BaseFlow;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: DSP竞价客户端
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 11:48
 **/
public class DspBidClient {
    private static Map<String, DspBidServer> realize = new HashMap<>();

    /**
     *注册具体实现
     **/
    static{
        realize.put("pc",new DefaultDspBidServer());
        realize.put("app",new DefaultDspBidServer());
        realize.put("h5",new DefaultDspBidServer());
    }
    /**
     * 根据不同流量获取不同垄断广告策略key
     **/
    private static String getDispatchKey(BaseFlow baseFlow){
        return baseFlow.getTerminal() + "_" + baseFlow.getPgType();
    }

    /**
     * 执行竞价请求
     * @param baseFlow 流量信息
     * @return 响应信息
     */
    public static ResponseBean execute(BaseFlow baseFlow,DspUserInfo user) {
        try {
            String key = getDispatchKey(baseFlow);
            DspBidServer dspBidServer = realize.containsKey(key) ? realize.get(key) : new DefaultDspBidServer();
            return dspBidServer.getDspResponseBean(baseFlow, user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
