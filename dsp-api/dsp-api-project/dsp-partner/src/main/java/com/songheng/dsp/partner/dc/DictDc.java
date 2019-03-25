package com.songheng.dsp.partner.dc;

import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.ssp.AdvSspSlot;

import java.util.List;

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

    /**
     * 获取adx用户信息集合
     * */
    List<DspUserInfo> getAdxUserInfoList(String terminal);

}
