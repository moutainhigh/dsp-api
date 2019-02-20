package com.songheng.dsp.adxbid.task;


import com.songheng.dsp.adxbid.utils.BuildAdxBidRequestBean;
import com.songheng.dsp.common.utils.HttpClientUtils;
import com.songheng.dsp.common.utils.serialize.FastJsonUtils;
import com.songheng.dsp.model.adx.request.RequestBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;

import java.util.concurrent.Callable;

public class SendAdxBidReq implements Callable<ResponseBean> {

    private DspUserInfo user;

    private String requestBeanJson;

    public SendAdxBidReq(DspUserInfo user,RequestBean requestBean){
        this.user = user;
        this.requestBeanJson = BuildAdxBidRequestBean.bidRequestBeanToJsonString(requestBean);
    }


    @Override
    public ResponseBean call() {
        if("dfadx".equalsIgnoreCase(user.getDspid())){
            //TODO go to dfadx
        }else{
            String result = HttpClientUtils.httpPost(user.getBidurl(),requestBeanJson);
            try {
                return FastJsonUtils.json2Bean(result, RequestBean.class);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
