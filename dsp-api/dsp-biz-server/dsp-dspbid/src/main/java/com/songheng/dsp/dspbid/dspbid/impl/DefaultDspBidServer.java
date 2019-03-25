package com.songheng.dsp.dspbid.dspbid.impl;

import com.songheng.dsp.dspbid.dspbid.DspBidServer;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import com.songheng.dsp.model.materiel.ExtendNews;

import java.util.List;
import java.util.Set;

/**
 * @description: 默认的DSP竞标服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 14:50
 **/
public class DefaultDspBidServer extends DspBidServer {
    @Override
    protected DspAdvInfo dspRtb(List<ExtendNews> advList, Set<String> deliveryIdFilter, BaseFlow baseFlow, String tagId, int bidModel) {
        return null;
    }

    @Override
    protected void matchAdvList(List<ExtendNews> advList, BaseFlow baseFlow, String tagId,int bidModel) {

    }

    @Override
    protected void shieldAdvList(List<ExtendNews> advList, BaseFlow baseFlow, String tagId, int bidModel) {

    }

    @Override
    protected List<ExtendNews> getCurrTagIdAdvList(BaseFlow baseFlow, String tagId, int bidModel) {
        return null;
    }

    @Override
    protected boolean filterCurrTagId(BaseFlow baseFlow, String tagId, int bidModel) {
        return false;
    }
}
