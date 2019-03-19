package com.songheng.dsp.ssp.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.materiel.ExtendNews;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @description: 广告位数据本地服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-18 16:27
 **/
@Service
public class AdvSspSlotLocalService{

    @Reference
    private AdvSspSlotService advSspSlotService;


    /**
     * 获取所有 slotId 对应ssp广告位信息
     * @return
     */
    public Map<String,AdvSspSlot> getAdvSspSlotMap(){
        return advSspSlotService.getAdvSspSlotMap();
    }


}
