package com.songheng.dsp.partner.dc;

import com.songheng.dsp.model.ssp.AdvSspSlot;

/**
 * @description:  获取基础字典数据
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 14:59
 **/
public interface DictDc {
    /**
     * 获取slotId具体广告位信息
     * */
    AdvSspSlot getAdvSspSlotMap(String slotId);
}
