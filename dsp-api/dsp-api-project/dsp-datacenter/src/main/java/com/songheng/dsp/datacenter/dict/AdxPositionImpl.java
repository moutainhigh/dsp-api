package com.songheng.dsp.datacenter.dict;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.dict.AdxPositionService;
import com.songheng.dsp.model.dict.AdPosition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 12:07
 * @description: ADX广告位缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdxPositionService.class)
@Component
public class AdxPositionImpl implements AdxPositionService {

    /**
     * key: app,h5,pc
     * value: List<AdPosition>
     */
    private volatile Map<String, List<AdPosition>> tmlAdPositions = new ConcurrentHashMap<>(6);
    /**
     * key: app,h5,pc+locationId/locationName
     * value AdPosition
     */
    private volatile Map<String, AdPosition> locationIdsMap = new ConcurrentHashMap<>(16);


    /**
     * 更新DSP广告位信息
     */
    public void updateAdPosition(){
        String sql = SqlMapperLoader.getSql("AdPosition", "queryAdPositions");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdPosition error sql is null, namespace: AdPosition, id: queryAdPositions");
            return;
        }
        List<AdPosition> adPositions = DbUtils.queryList(sql, AdPosition.class);
        List<AdPosition> appAdPosit = new ArrayList<>();
        List<AdPosition> h5AdPosit = new ArrayList<>();
        List<AdPosition> pcAdPosit = new ArrayList<>();
        Map<String, AdPosition> locationIdsTmp = new ConcurrentHashMap<>(1024);
        for (AdPosition adPosition : adPositions){
            if ("app".equalsIgnoreCase(adPosition.getType())){
                appAdPosit.add(adPosition);
            } else if ("h5".equalsIgnoreCase(adPosition.getType())){
                h5AdPosit.add(adPosition);
            } else if ("pc".equalsIgnoreCase(adPosition.getType())){
                pcAdPosit.add(adPosition);
            }
            locationIdsTmp.put(String.format("%s%s%s", adPosition.getType(), "_", adPosition.getLocation_id()), adPosition);
            locationIdsTmp.put(String.format("%s%s%s", adPosition.getType(), "_", adPosition.getLocation_name()), adPosition);
        }
        if (appAdPosit.size() > 0){
            tmlAdPositions.put("app", appAdPosit);
        }
        if (h5AdPosit.size() > 0){
            tmlAdPositions.put("h5", h5AdPosit);
        }
        if (pcAdPosit.size() > 0){
            tmlAdPositions.put("pc", pcAdPosit);
        }
        if (locationIdsTmp.size() > 0){
            locationIdsMap = locationIdsTmp;
        }
        log.debug("adxPositionSize: {}\tappPositionSize: {}\th5PositionSize: {}\tpcPositionSize: {}",
                adPositions.size(), appAdPosit.size(), h5AdPosit.size(), pcAdPosit.size());
    }

    /**
     * 根据terminal 获取 List<AdPosition>
     * @param terminal
     * @return
     */
    @Override
    public List<AdPosition> getAdPositionList(String terminal) {
        if (StringUtils.isBlank(terminal)){
            return new ArrayList<>();
        }
        List<AdPosition> result = tmlAdPositions.get(terminal);
        return null != result ? result : new ArrayList<AdPosition>();
    }

    /**
     * 根据terminal,locationId 获取 AdPosition
     * @param terminal
     * @param locationId
     * @return
     */
    @Override
    public AdPosition getAdPositionById(String terminal, String locationId) {
        String tml_locationId = String.format("%s%s%s", terminal, "_", locationId);
        AdPosition adPosition = locationIdsMap.get(tml_locationId);
        return null != adPosition ? adPosition : new AdPosition();
    }

    /**
     * 根据terminal,locationName 获取 AdPosition
     * @param terminal
     * @param locationName
     * @return
     */
    @Override
    public AdPosition getAdPositionByName(String terminal, String locationName) {
        if(null != locationName && (locationName.split("_").length == 5
                || locationName.split("_").length == 6)){
            String tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
            //具体站点_具体渠道
            AdPosition position = locationIdsMap.get(tml_locationName);
            if(null == position){
                tml_locationName = String.format("%s%s%s", terminal, "_",
                        locationName.replace(locationName.split("_")[1], "other"));
                //具体站点_other
                position = locationIdsMap.get(tml_locationName);
            }
            if(null == position){
                locationName = locationName.replace(locationName.split("_")[0], "default");
                tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
                //default_具体渠道
                position = locationIdsMap.get(tml_locationName);
            }
            if(null == position){
                locationName = locationName.replace(locationName.split("_")[1], "other");
                tml_locationName = String.format("%s%s%s", terminal, "_", locationName);
                //default_other
                position = locationIdsMap.get(tml_locationName);
            }
            return null == position ? new AdPosition() : position;
        }
        return new AdPosition();
    }
}
