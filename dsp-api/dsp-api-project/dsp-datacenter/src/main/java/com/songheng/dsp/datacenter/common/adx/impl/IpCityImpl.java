package com.songheng.dsp.datacenter.common.adx.impl;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.dubbo.baseinterface.common.adx.service.IpCityService;
import com.songheng.dsp.model.adx.IpCityInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 20:23
 * @description: IpCity缓存接口
 */
@Slf4j
@Component
public class IpCityImpl implements IpCityService {

    /**
     * ipCityMap
     */
    private volatile static Map<String,IpCityInfo> ipCityMap = new ConcurrentHashMap<>(16);

    /**
     * 更新IpCity
     */
    public void updateIpCityInfo(){
        List<IpCityInfo> ipCityInfoList = DbUtils.queryList(
                "SELECT ip, province, city FROM adplatform_ip_manage WHERE enable = 'Y'",
                IpCityInfo.class);
        Map<String,IpCityInfo> ipcityTmp = new ConcurrentHashMap<>(16);
        for (IpCityInfo ipCityInfo : ipCityInfoList){
            ipcityTmp.put(ipCityInfo.getIp(), ipCityInfo);
        }
        if (ipcityTmp.size() > 0){
            ipCityMap = ipcityTmp;
        }
        log.debug("ipcityMapSize: {}", ipcityTmp.size());
    }

    /**
     * getAllIpCity
     * @return
     */
    @Override
    public Map<String, IpCityInfo> getAllIpCity() {
        return ipCityMap;
    }

    /**
     * getIpCityInfo By ip
     * @param ip
     * @return
     */
    @Override
    public IpCityInfo getIpCityInfo(String ip) {
        if (StringUtils.isBlank(ip)){
            return new IpCityInfo();
        }
        return ipCityMap.get(ip);
    }
}
