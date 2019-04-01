package com.songheng.dsp.dsprtb.dsprtb;

import com.songheng.dsp.model.client.DspRtbClientRequest;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.ssp.AdvSspFloorPrice;

import java.util.List;

/**
 * @description: DSP实时竞价服务类
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 10:32
 **/
public abstract class DspRtbService {

    /**
     * DSP 实时竞价
     * @param request
     * @return
     */
    public MaterielDirect dspRtb(DspRtbClientRequest request){
        List<MaterielDirect> advList = request.getAdvList();
        if(null!=advList && advList.size()>0) {
            //获取当前竞价的底价信息
            AdvSspFloorPrice price = request.getAdvSspFloorPrice();
            if(null == price){
                return null;
            }
            Long floorPrice = request.getBaseFlow().isNight() ? price.getMinCpmNight() : price.getMinCpmDay();
            //获取竞价上的广告
            MaterielDirect adv = getTopAdv(advList,floorPrice,request.getBidModel());
            //设置广告的售卖情况
            setAdvSellInfo(adv,advList,floorPrice,request.getBidModel());

            return adv;

        }
        return null;

    }

    /**
     * 设置广告的售卖情况
     * @param advList
     * @param floorPrice 底价
     * @param bidModel 竞价模式
     * @return
     */
    protected abstract void setAdvSellInfo(MaterielDirect adv,List<MaterielDirect> advList,Long floorPrice,int bidModel);

    /**
     * 获取竞价上的广告
     * @param advList
     * @param floorPrice 底价
     * @param bidModel 竞价模式
     * @return
     */
    protected abstract MaterielDirect getTopAdv(List<MaterielDirect> advList,Long floorPrice,int bidModel);

}
