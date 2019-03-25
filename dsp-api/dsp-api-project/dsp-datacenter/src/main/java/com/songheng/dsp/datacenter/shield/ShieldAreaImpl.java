package com.songheng.dsp.datacenter.shield;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.shield.ShieldAreaService;
import com.songheng.dsp.model.shield.ShieldArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/19 15:55
 * @description: 地域屏蔽信息缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = ShieldAreaService.class,
        timeout = 100)
@Component
public class ShieldAreaImpl implements ShieldAreaService {

    /**
     * 按 terminal+site+qid 维度拆分ShieldArea
     * key: terminal+site+qid
     * value: List<ShieldArea>
     */
    private volatile Map<String, List<ShieldArea>> tmlSiteQidMap = new ConcurrentHashMap<>(16);
    /**
     * 按 terminal+shieldType 维度拆分ShieldArea
     * key: terminal+shieldType
     * value: List<ShieldArea>
     */
    private volatile Map<String, List<ShieldArea>> tmlShieldTypeMap = new ConcurrentHashMap<>(16);

    /**
     * 行业信息映射
     * sectorMap
     * key: code
     * value: name
     */
    private volatile Map<String, String> sectorMap = new ConcurrentHashMap<>(16);

    /**
     * 手机型号映射
     * mobileVendorMap
     * key: name
     * value: code
     */
    private volatile Map<String, String> mobileVendorMap = new ConcurrentHashMap<>(16);

    /**
     * 其他手机型号映射
     * otherVendorMap
     * key: name
     * value: code
     */
    private volatile Map<String, String> otherVendorMap = new ConcurrentHashMap<>(16);


    /**
     * 获取所有 以terminal+site+qid 维度的 ShieldAreaMap
     * @return
     */
    @Override
    public Map<String, List<ShieldArea>> getTmlSiteQidMap(){
        return tmlSiteQidMap;
    }

    /**
     * 获取 以terminal+site+qid 维度的 List<ShieldArea>
     * @param terminal
     * @param site
     * @param qid
     * @return
     */
    @Override
    public List<ShieldArea> getShieldAreaList(String terminal, String site, String qid){
        String tmlSiteQid = String.format("%s%s%s%s%s", terminal, ".", site, ".", qid);
        List<ShieldArea> result = tmlSiteQidMap.get(tmlSiteQid);
        return null != result ? result : new ArrayList<ShieldArea>();
    }

    /**
     * 获取所有 以terminal+shieldType 维度的 ShieldAreaMap
     * @return
     */
    @Override
    public Map<String, List<ShieldArea>> getTmlShieldTypeMap(){
        return tmlShieldTypeMap;
    }

    /**
     * 获取 以terminal+shieldType 维度的 List<ShieldArea>
     * @param terminal
     * @param shieldType
     * @return
     */
    @Override
    public List<ShieldArea> getShieldAreaList(String terminal, String shieldType) {
        String tmlShieldType = String.format("%s%s%s", terminal, ".", shieldType);
        List<ShieldArea> result = tmlShieldTypeMap.get(tmlShieldType);
        return null != result ? result : new ArrayList<ShieldArea>();
    }

    /**
     * getSectorMap
     * @return
     */
    @Override
    public Map<String, String> getSectorMap(){
        return sectorMap;
    }

    /**
     * getSectorByCode
     * @param code
     * @return
     */
    @Override
    public String getSectorByCode(String code){
        if (StringUtils.isBlank(code)){
            return null;
        }
        return sectorMap.get(code);
    }

    /**
     * getMobileVendorMap
     * @return
     */
    @Override
    public Map<String, String> getMobileVendorMap(){
        return mobileVendorMap;
    }

    /**
     * getMobileVendorByName
     * @param name
     * @return
     */
    @Override
    public String getMobileVendorByName(String name){
        if (StringUtils.isBlank(name)){
            return null;
        }
        return mobileVendorMap.get(name);
    }

    /**
     * getOtherVendorMap
     * @return
     */
    @Override
    public Map<String, String> getOtherVendorMap(){
        return otherVendorMap;
    }

    /**
     * getOtherVendorByName
     * @param name
     * @return
     */
    @Override
    public String getOtherVendorByName(String name){
        if (StringUtils.isBlank(name)){
            return null;
        }
        return otherVendorMap.get(name);
    }

    /**
     * 更新地域屏蔽信息
     *
     */
    public void updateShieldArea(){
        String sql = SqlMapperLoader.getSql("ShieldArea", "queryShieldAreaInfo");
        if (StringUtils.isBlank(sql)){
            log.error("updateShieldArea error sql is null, namespace: ShieldArea, id: queryShieldAreaInfo");
            return;
        }
        List<ShieldArea> shieldAreaList = DbUtils.queryList(sql, ShieldArea.class);
        Map<String, List<ShieldArea>> tmlSiteQidTmp = new ConcurrentHashMap<>(256);
        Map<String, List<ShieldArea>> tmlShieldTypeTmp = new ConcurrentHashMap<>(256);
        String tmlSiteQid, tmlShieldType;
        for (ShieldArea shieldArea : shieldAreaList){
            tmlSiteQid = String.format("%s%s%s%s%s", shieldArea.getTerminal(),
                    ".", shieldArea.getSite(), ".", shieldArea.getQid());
            tmlShieldType = String.format("%s%s%s", shieldArea.getTerminal(), ".", shieldArea.getShielType());
            if (tmlSiteQidTmp.containsKey(tmlSiteQid)){
                tmlSiteQidTmp.get(tmlSiteQid).add(shieldArea);
            } else {
                List<ShieldArea> list = new ArrayList<>();
                list.add(shieldArea);
                tmlSiteQidTmp.put(tmlSiteQid, list);
            }
            if (!tmlShieldTypeTmp.containsKey(tmlShieldType)){
                List<ShieldArea> list = new ArrayList<>();
                list.add(shieldArea);
                tmlShieldTypeTmp.put(tmlShieldType, list);
            } else {
                tmlShieldTypeTmp.get(tmlShieldType).add(shieldArea);
            }
        }
        if (tmlSiteQidTmp.size() > 0){
            tmlSiteQidMap = tmlSiteQidTmp;
        }
        if (tmlShieldTypeTmp.size() > 0){
            tmlShieldTypeMap = tmlShieldTypeTmp;
        }
        log.debug("ShieldAreaList Size: {}", shieldAreaList.size());
    }

    /**
     * 更新行业映射信息
     *
     */
    public void updateSectorInfo(){
        String sql = SqlMapperLoader.getSql("ShieldArea", "querySectorInfo");
        if (StringUtils.isBlank(sql)){
            log.error("updateSectorInfo error sql is null, namespace: ShieldArea, id: querySectorInfo");
            return;
        }
        Map<String, String> sectorTmp = new ConcurrentHashMap<>(128);
        DbUtils.query2Map(sql, sectorTmp);
        if (sectorTmp.size() > 0){
            sectorMap = sectorTmp;
        }
        log.debug("Sector Map Size: {}", sectorTmp.size());
    }

    /**
     * 更新手机型号信息
     */
    public void updateMobileVendor(){
        String sql = SqlMapperLoader.getSql("ShieldArea", "queryMobileVendor");
        if (StringUtils.isBlank(sql)){
            log.error("updateMobileVendor error sql is null, namespace: ShieldArea, id: queryMobileVendor");
            return;
        }
        Map<String, String> mobileVendorTmp = new ConcurrentHashMap<>(128);
        DbUtils.query2Map(sql, mobileVendorTmp);
        if (mobileVendorTmp.size() > 0){
            mobileVendorMap = mobileVendorTmp;
        }
        log.debug("Mobile Vendor Map Size: {}", mobileVendorTmp.size());
    }

    /**
     * 更新其他手机型号信息
     */
    public void updateOtherVendor(){
        String sql = SqlMapperLoader.getSql("ShieldArea", "queryOtherVendor");
        if (StringUtils.isBlank(sql)){
            log.error("updateOtherVendor error sql is null, namespace: ShieldArea, id: queryOtherVendor");
            return;
        }
        Map<String, String> otherVendorTmp = new ConcurrentHashMap<>(16);
        DbUtils.query2Map(sql, otherVendorTmp);
        if (otherVendorTmp.size() > 0){
            otherVendorMap = otherVendorTmp;
        }
        log.debug("Other Vendor Map Size: {}", otherVendorTmp.size());
    }
}
