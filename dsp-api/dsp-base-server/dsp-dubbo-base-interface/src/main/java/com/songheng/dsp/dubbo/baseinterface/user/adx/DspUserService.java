package com.songheng.dsp.dubbo.baseinterface.user.adx;

import com.songheng.dsp.model.adx.user.DspUserInfo;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取所有 List<DspUserInfo>
     * @return
     */
    Map<String, List<DspUserInfo>> getDspUsersListMap();

    /**
     * 获取所有 List<DspUserInfo>
     * @return
     */
    Map<String, List<DspUserInfo>> getDspUsersByDspIdListMap();

    /**
     * 获取所有 List<DspUserInfo>
     * @return
     */
    Map<String, List<DspUserInfo>> getDspUsersByPriorityListMap();

}
