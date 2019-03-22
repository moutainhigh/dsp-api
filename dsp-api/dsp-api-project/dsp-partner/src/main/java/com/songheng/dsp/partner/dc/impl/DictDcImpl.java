package com.songheng.dsp.partner.dc.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.partner.dc.DictDc;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @description: 获取基础字典数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 14:59
 **/
@Service
public class DictDcImpl implements DictDc {

    @Reference
    AdvSspSlotService slotService;

    /**
     * 获取slotId具体广告位信息
     * */
    @Override
    @Cacheable(value = {"ssp_slot"})
    public AdvSspSlot getAdvSspSlotMap(String slotId){
        AdvSspSlot advSspSlot = slotService.getAdvSspSlotMap().get(slotId);
        return advSspSlot;
    }
}
