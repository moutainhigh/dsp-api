package com.songheng.dsp.dubbo.baseinterface.user.dsp;

import com.songheng.dsp.model.dsp.AdvertiserInfo;

/**
 * @description: DSP广告主信息
 * @author: zhangshuai@021.com
 * @date: 2019-04-03 18:03
 **/
public interface AdvertiserService {

    /**
     * 根据userId获取广告主信息
     * @param userId 用户id
     * @return 广告主信息
     */
    AdvertiserInfo getAdvertiserInfoByUserId(Integer userId);

}
