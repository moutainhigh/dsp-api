package com.songheng.dsp.dspbid.dspbid.impl;

import com.songheng.dsp.dspbid.dspbid.DspBidServer;
import com.songheng.dsp.match.AdvMatchClient;
import com.songheng.dsp.model.client.DspBidClientRequest;
import com.songheng.dsp.model.client.MatchClientRequest;
import com.songheng.dsp.model.client.ShieldClientRequest;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.ShieldClient;

import java.util.List;

/**
 * @description: 默认的DSP竞标服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 14:50
 **/
public class DefaultDspBidServer extends DspBidServer {
    @Override
    protected void putCpmByCtr(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel) {

    }

    @Override
    protected boolean advSpeedLimit(MaterielDirect adv, DspBidClientRequest request, String tagId, int bidModel) {
        return true;
    }

    @Override
    protected MaterielDirect dspRtb(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel) {
        return null;
    }

    @Override
    protected boolean matchAdv(MaterielDirect adv, DspBidClientRequest request, String tagId,int bidModel) {
        //组装匹配模块请求参数
        MatchClientRequest matchRequest = new MatchClientRequest();
        matchRequest.setAdv(adv);
        matchRequest.setBaseFlow(request.getBaseFlow());
        matchRequest.setTagId(tagId);
        matchRequest.setBidModel(bidModel);
        matchRequest.setConsumeInfo(request.getConsumeInfo());
        return AdvMatchClient.execute(matchRequest);
    }

    @Override
    protected boolean shieldAdv(MaterielDirect adv,DspBidClientRequest request, String tagId,int bidModel) {
        //组装屏蔽模块请求参数
        ShieldClientRequest shieldRequest = new ShieldClientRequest();
        shieldRequest.setAdv(adv);
        shieldRequest.setBaseFlow(request.getBaseFlow());
        shieldRequest.setTagId(tagId);
        shieldRequest.setBidModel(bidModel);
        shieldRequest.setShiledJson(request.getShiledJson());
        //执行屏蔽模块
        return ShieldClient.execute(shieldRequest);
    }
}
