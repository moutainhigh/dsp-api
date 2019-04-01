package com.songheng.dsp.datacenter.dict;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.dict.AdvDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 17:20
 * @description: Adv字典缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdvDictService.class,
        timeout = 1000)
@Component
public class AdvDictImpl implements AdvDictService {

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
     * 更新行业映射信息
     *
     */
    public void updateSectorInfo(){
        String sql = SqlMapperLoader.getSql("AdvDict", "querySectorInfo");
        if (StringUtils.isBlank(sql)){
            log.error("updateSectorInfo error sql is null, namespace: AdvDict, id: querySectorInfo");
            return;
        }
        Map<String, String> sectorTmp = new ConcurrentHashMap<>(128);
        DbUtils.query2Map(ProjectEnum.DATACENTER.getDs()[0], sql, sectorTmp);
        if (sectorTmp.size() > 0){
            sectorMap = sectorTmp;
        }
        log.debug("Sector Map Size: {}", sectorTmp.size());
    }

    /**
     * 更新手机型号信息
     */
    public void updateMobileVendor(){
        String sql = SqlMapperLoader.getSql("AdvDict", "queryMobileVendor");
        if (StringUtils.isBlank(sql)){
            log.error("updateMobileVendor error sql is null, namespace: AdvDict, id: queryMobileVendor");
            return;
        }
        Map<String, String> mobileVendorTmp = new ConcurrentHashMap<>(128);
        DbUtils.query2Map(ProjectEnum.DATACENTER.getDs()[0], sql, mobileVendorTmp);
        if (mobileVendorTmp.size() > 0){
            mobileVendorMap = mobileVendorTmp;
        }
        log.debug("Mobile Vendor Map Size: {}", mobileVendorTmp.size());
    }

    /**
     * 更新其他手机型号信息
     */
    public void updateOtherVendor(){
        String sql = SqlMapperLoader.getSql("AdvDict", "queryOtherVendor");
        if (StringUtils.isBlank(sql)){
            log.error("updateOtherVendor error sql is null, namespace: AdvDict, id: queryOtherVendor");
            return;
        }
        Map<String, String> otherVendorTmp = new ConcurrentHashMap<>(16);
        DbUtils.query2Map(ProjectEnum.DATACENTER.getDs()[0], sql, otherVendorTmp);
        if (otherVendorTmp.size() > 0){
            otherVendorMap = otherVendorTmp;
        }
        log.debug("Other Vendor Map Size: {}", otherVendorTmp.size());
    }

}
