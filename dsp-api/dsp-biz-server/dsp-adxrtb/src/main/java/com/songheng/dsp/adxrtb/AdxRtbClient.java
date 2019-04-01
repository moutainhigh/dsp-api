package com.songheng.dsp.adxrtb;


import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.output.OutPutAdv;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: Rtb竞价服务
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 14:25
 **/
public class AdxRtbClient {

    public static List<OutPutAdv> execute(BaseFlow baseFlow,List<ResponseBean> responseBeans) {
        List<OutPutAdv> advList = new ArrayList<>();
        OutPutAdv outPutAdv = new OutPutAdv();
        advList.add(outPutAdv);
        return advList;
    }

}
