package com.songheng.dsp.partner.job.dspbidreq;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.dspbid.DspBidClient;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.client.DspBidClientRequest;
import com.songheng.dsp.partner.service.DspBidService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.Callable;

/**
 * @description: 发送dsp竞价服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 09:57
 **/
@Service
public class SendDspBidReq implements Callable<ResponseBean> {

    private DspBidClientRequest dspBidClientRequest;

    public SendDspBidReq(DspBidClientRequest request){
       this.dspBidClientRequest =request;
    }


    @Autowired
    DspBidService dspBidService;

    @Override
    public ResponseBean call() {
        return DspBidClient.execute(dspBidClientRequest);
    }
}
