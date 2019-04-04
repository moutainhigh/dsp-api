package com.songheng.dsp.datacenter.user.dsp;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.user.dsp.AdvertiserService;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.dsp.AdvertiserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: DSP广告主信息
 * @author: zhangshuai@021.com
 * @date: 2019-04-03 18:06
 **/
@Slf4j
@Service(interfaceClass = AdvertiserService.class,
        timeout = 1000)
@Component
public class AdvertiserImpl implements AdvertiserService {

    /**
     * 广告主映射信息
     */
    private volatile Map<Integer, AdvertiserInfo> advertiserInfoMap = new ConcurrentHashMap<>(32);

    @Override
    public AdvertiserInfo getAdvertiserInfoByUserId(Integer userId) {
        return advertiserInfoMap.get(userId);
    }

    @Override
    public Map<Integer, AdvertiserInfo> getAdvertiserInfo() {
        return advertiserInfoMap;
    }


    /**
     * 更新广告主信息
     */
    public void updateAdvertiser() {
        String sql = SqlMapperLoader.getSql("Advertiser", "queryAdvertiser");
        if (StringUtils.isBlank(sql)) {
            log.error("updateAdvertiser error sql is null, namespace: Advertiser, id: queryAdvertiser");
            return;
        }
        List<AdvertiserInfo> advertiserInfos = DbUtils.queryList(ProjectEnum.DATACENTER.getDs()[0], sql,
                AdvertiserInfo.class);
        Map<Integer, AdvertiserInfo> advertiserInfoMapTemp = new ConcurrentHashMap<>(120);
        for (AdvertiserInfo info : advertiserInfos) {
            advertiserInfoMapTemp.put(info.getUserId(),info);
        }
        if(advertiserInfoMapTemp.size()>0){
            advertiserInfoMap = advertiserInfoMapTemp;
        }
        log.debug("advertiserSize:{}",advertiserInfos.size());
    }
}
