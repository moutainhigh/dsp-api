package com.songheng.dsp.dubbo.baseinterface.dict;

import com.songheng.dsp.model.dict.IpCityInfo;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 12:04
 * @description: IpCity
 */
public interface IpCityService {

    /**
     * 获取所有 Map<String,IpCityInfo>
     * @return
     */
    Map<String,IpCityInfo> getAllIpCity();

    /**
     * 获取指定 ip 的 IpCityInfo
     * @param ip
     * @return
     */
    IpCityInfo getIpCityInfo(String ip);

}
