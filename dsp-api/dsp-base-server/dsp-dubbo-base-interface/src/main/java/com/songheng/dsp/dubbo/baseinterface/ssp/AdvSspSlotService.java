package com.songheng.dsp.dubbo.baseinterface.ssp;

import com.songheng.dsp.model.materiel.MaterielDirect;
import com.songheng.dsp.model.ssp.AdvSspSlot;

import java.util.Map;
import java.util.Set;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 15:52
 * @description: SSP 广告位缓存接口
 */
public interface AdvSspSlotService {

    /**
     * 获取所有 slotId对应广告池 信息
     * @return
     */
    Map<String, Set<MaterielDirect>> getMaterielDirectMap();

    /**
     * 获取所有 slotId 对应ssp广告位信息
     * @return
     */
    Map<String, AdvSspSlot> getAdvSspSlotMap();

    /**
     * 获取指定 slotId 对应广告池信息
     * @param slotId
     * @return
     */
    Set<MaterielDirect> getMaterielDirectSet(String slotId);

    /**
     * 获取一个或多个slotId 映射的 Set<MaterielDirect>
     * @param slotIds
     * @return
     */
    Map<String, Set<MaterielDirect>> getMaterielDirectBySlotIds(String slotIds);

    /**
     * 获取指定 slotId 对应ssp广告位信息
     * @param slotId
     * @return
     */
    AdvSspSlot getAdvSspSlotById(String slotId);

}
