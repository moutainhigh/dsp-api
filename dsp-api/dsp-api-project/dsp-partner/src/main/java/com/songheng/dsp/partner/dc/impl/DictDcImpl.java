package com.songheng.dsp.partner.dc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.partner.dc.DictDc;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: 获取基础字典数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 14:59
 **/
@Component
public class DictDcImpl implements DictDc {

    @Reference
    AdvSspSlotService slotService;

    /**
     * 获取所有广告位
     * */
    @Override
    public Map<String, AdvSspSlot> getAdvSspSlotMap(){
        return slotService.getAdvSspSlotMap();
    }
}
