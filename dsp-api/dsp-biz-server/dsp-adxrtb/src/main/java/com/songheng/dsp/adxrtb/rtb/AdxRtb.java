package com.songheng.dsp.adxrtb.rtb;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.adx.response.BidBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.client.AdxRtbClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import com.songheng.dsp.model.output.OutPutAdv;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * @description: Adx Rtb竞价服务
 * @author: zhangshuai@021.com
 * @date: 2019-02-02 13:05
 **/
@Slf4j
public abstract class AdxRtb {

    public List<OutPutAdv> adxRtb(AdxRtbClientRequest request){
        List<ResponseBean> responseBeans = request.getResponseBeans();
        Iterator<ResponseBean> iterator = responseBeans.iterator();
        while(iterator.hasNext()){
            ResponseBean next = iterator.next();
            List<BidBean> seatbid = next.getSeatbid();
            Iterator<BidBean> seatIterator = seatbid.iterator();
            while(seatIterator.hasNext()){
                BidBean bidBean = seatIterator.next();
                bidBean.getAdm();
            }
        }

        return null;
    }

}
