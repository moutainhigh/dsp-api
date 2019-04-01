package com.songheng.dsp.dspbid.dspbid.impl;

import com.songheng.dsp.ctr.CtrClient;
import com.songheng.dsp.dspbid.dspbid.DspBidService;
import com.songheng.dsp.dsprtb.DspRtbClient;
import com.songheng.dsp.match.AdvMatchClient;
import com.songheng.dsp.model.client.*;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.shield.ShieldClient;
import com.songheng.dsp.speed.SpeedClient;

import java.util.List;

/**
 * @description: 默认的DSP竞标服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 14:50
 **/
public class DefaultDspBidService extends DspBidService {
    @Override
    protected void putCpmByCtr(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel) {
        //组装点击率预测模块请求参数
        CtrClientRequest ctrClientRequest = new CtrClientRequest();
        ctrClientRequest.setAdvList(advList);
        ctrClientRequest.setConsumeInfo(request.getConsumeInfo());
        ctrClientRequest.setBaseFlow(request.getBaseFlow());
        ctrClientRequest.setTagId(tagId);
        ctrClientRequest.setBidModel(bidModel);
        //执行点击率预估模块任务
        CtrClient.execute(ctrClientRequest);
    }

    @Override
    protected boolean advSpeedLimit(MaterielDirect adv, DspBidClientRequest request, String tagId, int bidModel) {
        //组装限速模块请求参数
        SpeedClientRequest speedClientRequest = new SpeedClientRequest();
        speedClientRequest.setAdv(adv);
        speedClientRequest.setBaseFlow(request.getBaseFlow());
        speedClientRequest.setTagId(tagId);
        speedClientRequest.setBidModel(bidModel);
        //执行限速模块任务
        return SpeedClient.execute(speedClientRequest);
    }

    @Override
    protected MaterielDirect dspRtb(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel) {
        DspRtbClientRequest dspRtbClientRequest = new DspRtbClientRequest();
        dspRtbClientRequest.setAdvList(advList);
        dspRtbClientRequest.setBaseFlow(request.getBaseFlow());
        dspRtbClientRequest.setAdvSspFloorPrice(request.getAdvSspFloorPrice());
        dspRtbClientRequest.setBidModel(bidModel);
        //执行rtb模块
        return DspRtbClient.execute(dspRtbClientRequest);
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
        //执行匹配模块
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
