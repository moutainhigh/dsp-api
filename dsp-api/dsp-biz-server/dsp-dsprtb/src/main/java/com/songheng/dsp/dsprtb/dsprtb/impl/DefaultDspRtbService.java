package com.songheng.dsp.dsprtb.dsprtb.impl;

import com.songheng.dsp.dsprtb.dsprtb.DspRtbService;
import com.songheng.dsp.model.materiel.MaterielDirect;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @description: 默认的dsp实时竞价服务
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 10:33
 **/
public class DefaultDspRtbService extends DspRtbService {
    @Override
    protected void setAdvSellInfo(MaterielDirect adv, List<MaterielDirect> advList, Long floorPrice, int bidModel) {
        if(null!=adv) {
            if (0 != bidModel) {
                //非自由竞价模式
                adv.setCalshow(1000L);
                adv.setSecondPrice(adv.getBidPrice());
                adv.setSellPrice(adv.getBidPrice());
            } else {
                int advSize = advList.size();
                long secondPrice = advSize == 1 ? adv.getSortPrice() : advList.get(1).getSortPrice();
                long sellPrice = advSize == 1 ? floorPrice + 1 : advList.get(1).getSortPrice() + 1;
                //TODO 计算CalShow
                long calShow = secondPrice/sellPrice;
                adv.setSecondPrice(secondPrice);
                adv.setSellPrice(sellPrice);
                adv.setCalshow(calShow);
            }
        }
    }

    @Override
    protected MaterielDirect getTopAdv(List<MaterielDirect> advList, Long floorPrice, int bidModel) {
        if(0!=bidModel){
            //非自由竞价模式,打乱顺序
            //TODO  广告主筛选
            Collections.shuffle(advList);
            return advList.get(0);

        }else{
            //自由竞价的广告,筛选超过底价的广告
            //TODO K值 + 倍率逻辑
            Iterator<MaterielDirect> iterator = advList.iterator();
            while(iterator.hasNext()){
                MaterielDirect next = iterator.next();
                if(next.getSortPrice()<floorPrice){
                    iterator.remove();
                }
            }
            if(advList.size()>0){
                return advList.get(0);
            }else{
                return null;
            }
        }

    }
}
