package com.songheng.dsp.datacenter.ssp;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/11 15:49
 * @description: SSP 广告位缓存接口实现类
 */
@Slf4j
@Service(interfaceClass = AdvSspSlotService.class,
        timeout = 1000)
@Component
public class AdvSspSlotImpl implements AdvSspSlotService {

    /**
     * dfDspAdvCache
     */
    @Autowired
    private DfDspAdvCache dfDspAdvCache;

    /**
     * 映射slotId对应广告池
     */
    private volatile Map<String, Set<MaterielDirect>> materielDirectMap = new ConcurrentHashMap<>(16);
    /**
     * 映射slotId 对应ssp广告位信息
     */
    private volatile Map<String, AdvSspSlot> advSspSlotMap = new ConcurrentHashMap<>(16);

    /**
     * 获取所有 slotId对应广告池 信息
     * @return
     */
    @Override
    public Map<String, Set<MaterielDirect>> getMaterielDirectMap(){
        return materielDirectMap;
    }

    /**
     * 获取所有 slotId 对应ssp广告位信息
     * @return
     */
    @Override
    public Map<String, AdvSspSlot> getAdvSspSlotMap(){
        return advSspSlotMap;
    }

    /**
     * 获取一个或多个slotId 映射的 Set<MaterielDirect>
     * @param slotIds
     * @return
     */
    @Override
    public Map<String, Set<MaterielDirect>> getMaterielDirectBySlotIds(String slotIds){
        Map<String, Set<MaterielDirect>> result = new HashMap<>(16);
        if (StringUtils.isBlank(slotIds)){
            return result;
        }
        String[] slots = slotIds.split(",|，");
        for (String slotId : slots){
            result.put(slotId, getMaterielDirectSet(slotId));
        }
        return result;
    }

    /**
     * 获取指定 slotId 对应广告池信息
     * @param slotId
     * @return
     */
    @Override
    public Set<MaterielDirect> getMaterielDirectSet(String slotId){
        if (StringUtils.isBlank(slotId)){
            return new HashSet<>();
        }
        Set<MaterielDirect> result = materielDirectMap.get(slotId);
        return null != result ? result : new HashSet<MaterielDirect>();
    }

    /**
     * 获取指定 slotId 对应ssp广告位信息
     * @param slotId
     * @return
     */
    @Override
    public AdvSspSlot getAdvSspSlotById(String slotId){
        if (StringUtils.isBlank(slotId)){
            return new AdvSspSlot();
        }
        AdvSspSlot advSspSlot = advSspSlotMap.get(slotId);
        return null != advSspSlot ? advSspSlot : new AdvSspSlot();
    }

    /**
     * 更新Ssp 广告位缓存
     * 并映射slotId对应广告池
     */
    public void updateAdvSspSlot(){
        String sql = SqlMapperLoader.getSql("AdvSsp", "queryAdvSspSlot");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvSspSlot error sql is null, namespace: AdvSsp, id: queryAdvSspSlot");
            return;
        }
        List<AdvSspSlot> advSspSlotList = DbUtils.queryList(ProjectEnum.DATACENTER.getDs()[0], sql, AdvSspSlot.class);
        //映射slotId对应广告池
        Map<String, Set<MaterielDirect>> slot_materielDirect = new ConcurrentHashMap<>(64);
        Map<String, AdvSspSlot> advSspSlotTmp = new ConcurrentHashMap<>(64);
        for (AdvSspSlot advSspSlot : advSspSlotList){
            slot_materielDirect.put(advSspSlot.getSlotId(),
                    dfDspAdvCache.getMaterielDirectSet(advSspSlot.getTerminal(), advSspSlot.getPgtype()));
            advSspSlotTmp.put(advSspSlot.getSlotId(), advSspSlot);
        }
        if (slot_materielDirect.size() > 0){
            materielDirectMap = slot_materielDirect;
        }
        if (advSspSlotTmp.size() > 0){
            advSspSlotMap = advSspSlotTmp;
        }
        log.debug("AdvSspSlot Size: {}", advSspSlotList.size());
    }

}
