package com.songheng.dsp.datacenter.dict;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
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
@Component
public class AdxPositionImpl implements AdxPositionService {

    /**
     * key: app,h5,pc
     * value: List<AdPosition>
     */
    private volatile static Map<String, List<AdPosition>> tmlAdPositions = new ConcurrentHashMap<>(6);
    /**
     * key: app,h5,pc+locationId/locationName
     * value AdPosition
     */
    private volatile static Map<String, AdPosition> locationIdsMap = new ConcurrentHashMap<>(16);


    /**
     * 更新DSP广告位信息
     */
    public void updateAdPosition(){
        List<AdPosition> adPositions = DbUtils.queryList("SELECT\n" +
                "\ta.location_id,\n" +
                "\ta.location_name AS location_name_original,\n" +
                "\ta.isbottomad,\n" +
                "\tCONCAT( a.site, '_', a.qid, '_', a.location_name ) AS location_name,\n" +
                "\ta.cpm_start_price,\n" +
                "\ta.cpm_start_price_1,\n" +
                "\ta.cpm_start_price_2,\n" +
                "\ta.cpm_start_price_f,\n" +
                "\ta.block_vocation,\n" +
                "\ta.allow_file,\n" +
                "\ta.screen,\n" +
                "\ta.type,\n" +
                "\tMAX(\n" +
                "\tCASE a.style_id \n" +
                "\tWHEN 'big' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS bigstyle,\n" +
                "\tMAX(\n" +
                "\tCASE\n" +
                "\ta.style_id \n" +
                "\tWHEN 'small' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS smallstyle,\n" +
                "\tMAX(\n" +
                "\tCASE\n" +
                "\ta.style_id \n" +
                "\tWHEN 'group' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS groupstyle,\n" +
                "\tMAX(\n" +
                "\tCASE\n" +
                "\ta.style_id \n" +
                "\tWHEN 'banner' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS bannerstyle,\n" +
                "\tMAX(\n" +
                "\tCASE\n" +
                "\ta.style_id \n" +
                "\tWHEN 'wzl' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS wzlstyle,\n" +
                "\tMAX(\n" +
                "\tCASE\n" +
                "\ta.style_id \n" +
                "\tWHEN 'icon' THEN\n" +
                "\tCONCAT( a.style_name, ':', a.imgwidth, '*', a.imgheight, '*', a.imgnum ) \n" +
                "\tEND \n" +
                "\t) AS iconstyle,\n" +
                "\ta.c_ad_price,\n" +
                "\ta.k_rate\n" +
                "\tFROM\n" +
                "\t(\n" +
                "\tSELECT\n" +
                "\tp.location_id,\n" +
                "\tp.location_name,\n" +
                "\tp.isbottomad,\n" +
                "\tp.site,\n" +
                "\tp.qid,\n" +
                "\tp.cpm_start_price,\n" +
                "\tp.cpm_start_price_1,\n" +
                "\tp.cpm_start_price_2,\n" +
                "\tp.cpm_start_price_f,\n" +
                "\tp.block_vocation,\n" +
                "\tp.allow_file,\n" +
                "\tp.screen,\n" +
                "\tp.review_pic,\n" +
                "\tp.type,\n" +
                "\tp.c_ad_price,\n" +
                "\tp.k_rate,\n" +
                "\tb.style_id,\n" +
                "\tb.imgwidth,\n" +
                "\tb.imgheight,\n" +
                "\tb.imgnum,\n" +
                "\tb.style_name \n" +
                "\tFROM\n" +
                "\tadx_floor_price p\n" +
                "\tLEFT JOIN adx_adStyle b ON FIND_IN_SET( b.style_id, p.styles ) \n" +
                "\tWHERE\n" +
                "\tp.ENABLE = 'Y' \n" +
                "\tORDER BY\n" +
                "\tp.location_name DESC \n" +
                "\t) a \n" +
                "\tGROUP BY a.site,a.qid,a.location_name", AdPosition.class);
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
        return tmlAdPositions.get(terminal.toLowerCase());
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
        if (StringUtils.isBlank(tml_locationId)){
            return new AdPosition();
        }
        return locationIdsMap.get(tml_locationId);
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
