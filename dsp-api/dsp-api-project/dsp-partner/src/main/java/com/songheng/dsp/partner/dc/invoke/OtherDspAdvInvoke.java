package com.songheng.dsp.partner.dc.invoke;

import com.songheng.dsp.dubbo.baseinterface.materiel.adx.OtherDspAdvService;
import com.songheng.dsp.model.materiel.DspAdvExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/27 20:59
 * @description: OtherDspAdv DC远程RPC调用
 */
@Service
public class OtherDspAdvInvoke {

    /**
     * cacheManager
     */
    @Autowired
    private CacheManager cacheManager;

    /**
     * otherDspAdvService
     */
    @Autowired
    private OtherDspAdvService otherDspAdvService;

    /**
     * getDspAdvInfos
     * @param terminal
     * @return
     */
    @Cacheable(value = "other_dsp_adv", key = "#terminal", condition = "#result != null and #result.size() > 0")
    public List<DspAdvExtend> getDspAdvInfos(String terminal){
        return otherDspAdvService.getDspAdvInfos(terminal);
    }

    /**
     * getDspAdvByHisIdDspId
     * @param terminal
     * @param hisId
     * @param dspId
     * @return
     */
    @Cacheable(value = "other_dsp_adv", key = "#terminal.concat('_').concat(#hisId).concat('_').concat(#dspId)", condition = "#result != null")
    public DspAdvExtend getDspAdvByHisIdDspId(String terminal, String hisId, String dspId){
        return otherDspAdvService.getDspAdvByHisIdDspId(terminal, hisId, dspId);
    }

    /**
     * getDspAdvByAdvIdDspId
     * @param terminal
     * @param advId
     * @param dspId
     * @return
     */
    @Cacheable(value = "other_dsp_adv", key = "#terminal.concat('_').concat(#advId).concat('_').concat(#dspId)", condition = "#result != null")
    public DspAdvExtend getDspAdvByAdvIdDspId(String terminal, String advId, String dspId){
        return otherDspAdvService.getDspAdvByAdvIdDspId(terminal, advId, dspId);
    }

    /**
     * 更新缓存
     */
    public void updateOtherDspAdv(){
        Map<String, List<DspAdvExtend>> dspAdvListMap = otherDspAdvService.getDspAdvListMap();
        Cache cache = cacheManager.getCache("other_dsp_adv");
        if (null != dspAdvListMap && dspAdvListMap.size() > 0){
            for (String key : dspAdvListMap.keySet()){
                cache.put(key, dspAdvListMap.get(key));
            }
        }
        Map<String, DspAdvExtend> dspAdvByHisIdMap = otherDspAdvService.getDspAdvByHisIdMap();
        if (null != dspAdvByHisIdMap && dspAdvByHisIdMap.size() > 0){
            for (String key : dspAdvByHisIdMap.keySet()){
                cache.put(key, dspAdvByHisIdMap.get(key));
            }
        }
        Map<String, DspAdvExtend> dspAdvByAdvIdMap = otherDspAdvService.getDspAdvByAdvIdMap();
        if (null != dspAdvByAdvIdMap && dspAdvByAdvIdMap.size() > 0){
            for (String key : dspAdvByAdvIdMap.keySet()){
                cache.put(key, dspAdvByAdvIdMap.get(key));
            }
        }
    }

}
