package com.songheng.dsp.partner.bizflow;

import com.songheng.dsp.adxbid.AdxBidClient;
import com.songheng.dsp.common.utils.ThreadPoolUtils;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.partner.job.dspbidreq.SendDspBidReq;
import com.songheng.dsp.partner.service.BizService;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @description: 业务流模板
 * @author: zhangshuai@021.com
 * @date: 2019-03-22 17:03
 **/
@Slf4j
public abstract class BaseBizFlow{

    @Autowired
    BizService bizService;

    /**
     * 组装业务流
     * */
    public List<ResponseBean> groupBizFlow(HttpServletRequest request, BaseFlow baseFlow){
        //初始化风控请求对象
        SspClientRequest sspClientRequest = bizService.initSspClientRequestObj(request,baseFlow);
        //风控验证
        RiskControlResult riskControlResult = bizService.execute(sspClientRequest);
        if(riskControlResult.isSuccess()){
            //发送竞标请求获取Future信息
            List<Future> futures = sendBidReqAndGetFuture(baseFlow);
            //通过futures获取响应bean
            List<ResponseBean> responseBeans = getResponseByFutures(futures,baseFlow);
            //ADX竞价业务


            return responseBeans;
        }
        return null;
    }

    /**
     * 发送竞标请求获取Future信息
     * */
    private List<Future> sendBidReqAndGetFuture(BaseFlow baseFlow){
        List<Future> futures = new ArrayList<>();
        //发送DSP竞标请求
        DspUserInfo dspUserInfo = bizService.getOneSelfAdxUser(baseFlow.getTerminal());
        if(null!=dspUserInfo) {
            Future future = ThreadPoolUtils.submit(dspUserInfo.getDspid(), new SendDspBidReq(dspUserInfo,baseFlow));
            futures.add(future);
        }
        //发送第三方ADX竞标请求
        List<DspUserInfo> dspUserInfos = bizService.getThirdAdxUserList(baseFlow.getTerminal());
        List<Future> thirdFutures = AdxBidClient.execute(dspUserInfos,baseFlow);
        futures.addAll(thirdFutures);
        return futures;
    }
    /**
     * 通过futures获取响应bean
     * */
    private List<ResponseBean> getResponseByFutures(List<Future> futures,BaseFlow baseFlow){
        List<ResponseBean> responseBeans = new ArrayList<>(futures.size());
        //遍历获取所有的广告响应
        for (Future<ResponseBean> future : futures) {
            try {
                /**
                 * 等待<code>timeouot</code> ms ,超时后,抛出异常,不再进行等待
                 * */
                ResponseBean responseBean = future.get(1000, TimeUnit.MILLISECONDS);
                if(null!=responseBean) {
                    responseBeans.add(responseBean);
                }
            }catch (Exception e){
                e.printStackTrace();
                log.error("[get-response-error],reqId={}\t{}",baseFlow.getReqId(),e);
            }
        }
        return responseBeans;
    }
}
