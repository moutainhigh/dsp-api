package com.songheng.dsp.partner.dc;

import com.songheng.dsp.model.ssp.AdvSspSlot;
import java.util.Map;

/**
 * @description: 获取
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 14:59
 **/
public interface DictDc {
    /**
     * 获取所有广告位
     * */
    Map<String, AdvSspSlot> getAdvSspSlotMap();
}
