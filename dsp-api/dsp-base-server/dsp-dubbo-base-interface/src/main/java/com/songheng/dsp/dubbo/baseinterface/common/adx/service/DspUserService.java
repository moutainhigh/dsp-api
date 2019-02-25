package com.songheng.dsp.dubbo.baseinterface.common.adx.service;

import com.songheng.dsp.model.adx.user.DspUserInfo;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 21:54
 * @description: DspUser缓存接口
 */
public interface DspUserService {

    /**
     * 根据terminal 获取 List<DspUserInfo>
     * @param terminal
     * @return
     */
    List<DspUserInfo> getDspUsers(String terminal);

    /**
     * 根据terminal,dspId 获取 List<DspUserInfo>
     * @param terminal
     * @param dspId
     * @return
     */
    List<DspUserInfo> getDspUsersByDspId(String terminal, String dspId);

    /**
     * 根据terminal,priority 获取 List<DspUserInfo>
     * @param terminal
     * @param priority
     * @return
     */
    List<DspUserInfo> getDspUsersByPriority(String terminal, String priority);


}
