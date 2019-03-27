package com.songheng.dsp.dspbid.dspbid.impl;

import com.songheng.dsp.dspbid.dspbid.DspBidServer;
import com.songheng.dsp.model.client.DspBidClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;
import java.util.List;

/**
 * @description: 默认的DSP竞标服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 14:50
 **/
public class DefaultDspBidServer extends DspBidServer {
    @Override
    protected void advSpeedLimit(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel) {

    }

    @Override
    protected MaterielDirect dspRtb(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId, int bidModel) {
        return null;
    }

    @Override
    protected void matchAdvList(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId,int bidModel) {

    }

    @Override
    protected void shieldAdvList(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId, int bidModel) {

    }
}
