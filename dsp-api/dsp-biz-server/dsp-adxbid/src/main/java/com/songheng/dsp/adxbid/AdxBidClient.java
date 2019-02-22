package com.songheng.dsp.adxbid;

import com.songheng.dsp.adxbid.task.SendAdxBidReq;
import com.songheng.dsp.adxbid.utils.BuildAdxBidRequestBean;
import com.songheng.dsp.common.utils.ThreadPoolUtils;
import com.songheng.dsp.model.adx.request.RequestBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Adx竞标
 * @author zhangshuai@021.com
 * */
@Slf4j
public class AdxBidClient {

    public List<ResponseBean> execute(BaseFlow baseFlow){
        //竞价的响应结构
        List<ResponseBean> responseBeans = new ArrayList<>();
        //构建请求对象
        RequestBean requestBean = BuildAdxBidRequestBean.buildAdxBidRequestBean(baseFlow);

        //TODO DC 获取用户信息
        List<DspUserInfo> userInfos = new ArrayList<>();

        //发送竞标请求
        List<Future> futures = new ArrayList<>();
        for(DspUserInfo dspUserInfo  : userInfos){
            SendAdxBidReq sendAdxBidReq = new SendAdxBidReq(dspUserInfo,requestBean,baseFlow);
            Future<ResponseBean> responseBeanFuture = ThreadPoolUtils.submit(dspUserInfo.getDspid(),sendAdxBidReq);
            futures.add(responseBeanFuture);
        }

        //获取响应对象
        for (Future<ResponseBean> requestBeanFuture : futures) {
            try {
                ResponseBean responseBean = requestBeanFuture.get(100, TimeUnit.MILLISECONDS);
                responseBeans.add(responseBean);
            }catch (Exception e){
                e.printStackTrace();
                log.error("[get-response-error]\t{}",e);
            }
        }

        return responseBeans;
    }
}
