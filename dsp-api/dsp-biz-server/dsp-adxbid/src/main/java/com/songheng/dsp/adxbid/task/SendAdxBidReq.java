package com.songheng.dsp.adxbid.task;


import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.HttpClientUtils;
import com.songheng.dsp.common.utils.serialize.FastJsonUtils;
import com.songheng.dsp.model.adx.request.RequestBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
/**
 * 发送adx竞标请求
 * @author zhangshuai
 * */
@Slf4j
public class SendAdxBidReq implements Callable<ResponseBean> {

    private DspUserInfo user;

    private String requestBeanJson;

    private BaseFlow baseFlow;

    public SendAdxBidReq(DspUserInfo user, RequestBean requestBean, BaseFlow baseFlow){
        this.user = user;
        this.baseFlow = baseFlow;
        this.requestBeanJson = FastJsonUtils.toJSONString(requestBean);
    }


    @Override
    public ResponseBean call() {
        if(limitOverQPS()){
            return null;
        }
        //发送竞标请求
        if(user.isOneselfDsp()){
            //TODO oneselfDsp
        }else{
            try {
                String result = HttpClientUtils.httpPost(user.getBidurl(),requestBeanJson,100);
                return FastJsonUtils.json2Bean(result, RequestBean.class);
            }catch (Exception e){
                log.error("[send-bid-error],dspId={}&terminal={}&reqId={}=currQps={}\t{}", user.getDspid(),
                        baseFlow.getTerminal(),baseFlow.getReqId(),baseFlow.getCurrQps(),e);
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *限制高流量QPS
     * @return
     * true :限流 ;
     * false: 不限流
     * */
    private boolean limitOverQPS(){
        if(ProjectEnum.isApp(baseFlow.getTerminal()) && baseFlow.getCurrQps() <= user.getAppQps()){
            return false;
        }
        if(ProjectEnum.isH5(baseFlow.getTerminal()) && baseFlow.getCurrQps() <= user.getH5Qps()){
            return false;
        }
        if(ProjectEnum.isPc(baseFlow.getTerminal()) && baseFlow.getCurrQps() <= user.getPcQps()){
            return false;
        }
        log.info("[limit-qps],dspId={}&terminal={}&reqId={}=currQps={}",
              user.getDspid(), baseFlow.getTerminal(),baseFlow.getReqId(),baseFlow.getCurrQps());
        return true;
    }
}
