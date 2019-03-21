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

    /**
     * @param userInfos 用户列表
     * @param baseFlow 流量信息
     * */
    public List<ResponseBean> execute(List<DspUserInfo> userInfos ,BaseFlow baseFlow){
        //构建请求对象
        RequestBean requestBean = BuildAdxBidRequestBean.buildAdxBidRequestBean(baseFlow);
        //发送竞标请求
        List<Future> futures = new ArrayList<>();
        for(DspUserInfo dspUserInfo  : userInfos){
            //将竞标任务提交线程池
            SendAdxBidReq sendAdxBidReq = new SendAdxBidReq(dspUserInfo,requestBean,baseFlow);
            Future<ResponseBean> responseBeanFuture = ThreadPoolUtils.submit(dspUserInfo.getDspid(),sendAdxBidReq);
            futures.add(responseBeanFuture);
        }
        //竞标请求返回响应的广告对象
        List<ResponseBean> responseBeans = new ArrayList<>();
        //遍历获取所有的广告响应
        for (Future<ResponseBean> requestBeanFuture : futures) {
            try {
                /**
                 * 等待<code>timeouot</code> ms ,超时后,抛出异常,不再进行等待
                 * */
                ResponseBean responseBean = requestBeanFuture.get(100, TimeUnit.MILLISECONDS);
                responseBeans.add(responseBean);
            }catch (Exception e){
                e.printStackTrace();
                log.error("[get-response-error],reqId={}\t{}",baseFlow.getReqId(),e);
            }
        }
        return responseBeans;
    }
}
