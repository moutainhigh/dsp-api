package com.songheng.dsp.partner.service.impl;

import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.partner.service.DspBidService;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * @description: DSP竞标请求服务实现
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 18:44
 **/
@Service
public class DspBidServiceImp implements DspBidService, Callable<ResponseBean> {



    @Override
    public boolean limitOverQPS(){
        return false;
    }

    @Override
    public ResponseBean call() {
        return new ResponseBean();
    }
}
