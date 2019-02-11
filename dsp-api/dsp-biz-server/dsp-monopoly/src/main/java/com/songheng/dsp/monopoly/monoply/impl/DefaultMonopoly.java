package com.songheng.dsp.monopoly.monoply.impl;

import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import com.songheng.dsp.monopoly.monoply.Monopoly;
import java.util.ArrayList;
import java.util.List;


/**
 * @description: 默认的垄断业务
 * @author: zhangshuai@021.com
 * @date: 2019-02-02 13:05
 **/
public class DefaultMonopoly extends Monopoly {
    @Override
    public List<DspAdvInfo> getMonopolyAdvByCurPosition(BaseFlow baseFlow, String position) {

        List<DspAdvInfo> list = new ArrayList<>(4);


        DspAdvInfo advInfo1 = new DspAdvInfo();
        advInfo1.setDeliveryid("1");
        list.add(advInfo1);

        DspAdvInfo advInfo2 = new DspAdvInfo();
        advInfo2.setDeliveryid("2");
        list.add(advInfo2);

        DspAdvInfo advInfo3 = new DspAdvInfo();
        advInfo3.setDeliveryid("3");
        list.add(advInfo3);

        DspAdvInfo advInfo4 = new DspAdvInfo();
        advInfo4.setDeliveryid("4");
        list.add(advInfo4);
        System.out.println("获取当前位置的垄断广告："+position+"\t"+list);
        return list;
    }

    @Override
    public boolean isUsefulAdv(BaseFlow baseFlow,DspAdvInfo advInfo) {
        System.out.println("--------------------filter------------------");
        if("2".equalsIgnoreCase(advInfo.getDeliveryid())){
            return false;
        }
        return true;
    }
}
