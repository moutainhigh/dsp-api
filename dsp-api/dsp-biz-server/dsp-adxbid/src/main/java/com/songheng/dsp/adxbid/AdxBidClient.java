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
    public static List<Future> execute(List<DspUserInfo> userInfos ,BaseFlow baseFlow){
        List<Future> futures = new ArrayList<>();
        if(userInfos.size()>0) {
            //构建请求对象
            RequestBean requestBean = BuildAdxBidRequestBean.buildAdxBidRequestBean(baseFlow);
            //发送竞标请求
            for (DspUserInfo dspUserInfo : userInfos) {
                //将竞标任务提交线程池
                SendAdxBidReq sendAdxBidReq = new SendAdxBidReq(dspUserInfo, requestBean, baseFlow);
                Future<ResponseBean> future = ThreadPoolUtils.submit(dspUserInfo.getDspid(),sendAdxBidReq);
                futures.add(future);
            }
        }
        return futures;
    }
}
