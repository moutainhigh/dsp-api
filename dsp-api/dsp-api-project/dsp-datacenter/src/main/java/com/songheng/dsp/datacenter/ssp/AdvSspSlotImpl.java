package com.songheng.dsp.datacenter.ssp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspSlotService;
import com.songheng.dsp.model.materiel.ExtendNews;
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
    private volatile Map<String, Set<ExtendNews>> extendNewsMap = new ConcurrentHashMap<>(16);
    /**
     * 映射slotId 对应ssp广告位信息
     */
    private volatile Map<String, AdvSspSlot> advSspSlotMap = new ConcurrentHashMap<>(16);

    /**
     * 获取所有 slotId对应广告池 信息
     * @return
     */
    @Override
    public Map<String, Set<ExtendNews>> getExtendNewsMap(){
        return extendNewsMap;
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
     * 获取一个或多个slotId 映射的 Set<ExtendNews>
     * @param slotIds
     * @return
     */
    @Override
    public Map<String, Set<ExtendNews>> getExtendNewsBySlotIds(String slotIds){
        Map<String, Set<ExtendNews>> result = new HashMap<>(16);
        if (StringUtils.isBlank(slotIds)){
            return result;
        }
        String[] slots = slotIds.split(",|，");
        for (String slotId : slots){
            result.put(slotId, getExtendNewsSet(slotId));
        }
        return result;
    }

    /**
     * 获取指定 slotId 对应广告池信息
     * @param slotId
     * @return
     */
    @Override
    public Set<ExtendNews> getExtendNewsSet(String slotId){
        if (StringUtils.isBlank(slotId)){
            return new HashSet<>();
        }
        Set<ExtendNews> result = extendNewsMap.get(slotId);
        return null != result ? result : new HashSet<ExtendNews>();
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
        List<AdvSspSlot> advSspSlotList = DbUtils.queryList(
                "SELECT " +
                "\ta.id AS sspAppId, \n" +
                "\ta.appName, \n" +
                "\ta.terminal, \n" +
                "\ta.appType, \n" +
                "\ta.appId, \n" +
                "\ta.bosshead,\n" +
                "\ta.validateQid,\n" +
                "\ta.remark,\n" +
                "\ts.slotId,\n" +
                "\ts.slotName,\n" +
                "\ts.pgtype,\n" +
                "\ts.slotDesc,\n" +
                "\ts.slotImgs,\n" +
                "\ts.adnum,\n" +
                "\ts.slotSort,\n" +
                "\ts.styleIds,\n" +
                "\ts.floorPrice,\n" +
                "\tss.sellSeatId\n" +
                "\tFROM adv_ssp_slot s\n" +
                "\tINNER JOIN adv_ssp_application a\n" +
                "\tON s.appId = a.Id\n" +
                "\tAND a.status = 1 \n" +//启用
                "\tAND a.validateQid = 0 \n" +//不验证
                "\tINNER JOIN adv_dict_adstyle d\n" +
                "\tON FIND_IN_SET(d.id,s.styleIds) \n" +//样式过滤
                "\tAND d.status = 1 \n"+
                "\tLEFT JOIN adv_ssp_slot_seal ss\n" +
                "\tON s.slotId = ss.slotId\n" +
                "\tWHERE s.status = 1 \n" +//启用
                "\tUNION ALL\n" +
                "\tSELECT a.id AS sspAppId, \n" +
                "\ta.appName, \n" +
                "\ta.terminal, \n" +
                "\ta.appType, \n" +
                "\ta.appId, \n" +
                "\ta.bosshead,\n" +
                "\ta.validateQid,\n" +
                "\ta.remark,\n" +
                "\ts.slotId,\n" +
                "\ts.slotName,\n" +
                "\ts.pgtype,\n" +
                "\ts.slotDesc,\n" +
                "\ts.slotImgs,\n" +
                "\ts.adnum,\n" +
                "\ts.slotSort,\n" +
                "\ts.styleIds,\n" +
                "\ts.floorPrice,\n" +
                "\tss.sellSeatId\n" +
                "\tFROM adv_ssp_slot s\n" +
                "\tINNER JOIN adv_ssp_application a\n" +
                "\tON s.appId = a.Id\n" +
                "\tAND a.status = 1 \n" +//启用
                "\tAND a.validateQid = 1 \n" +//验证
                "\tINNER JOIN adv_dict_adstyle d\n" +
                "\tON FIND_IN_SET(d.id,s.styleIds) \n" +//样式过滤
                "\tAND d.status = 1 \n"+
                "\tLEFT JOIN adv_ssp_slot_seal ss\n" +
                "\tON s.slotId = ss.slotId\n" +
                "\tWHERE s.status = 1 \n" +//启用
                "\tAND a.id IN " +//渠道白名单过滤
                        "\t(SELECT q.appId FROM adv_ssp_qid q WHERE q.status = 1)", AdvSspSlot.class);
        //映射slotId对应广告池
        Map<String, Set<ExtendNews>> slot_extendNews = new ConcurrentHashMap<>(64);
        Map<String, AdvSspSlot> advSspSlotTmp = new ConcurrentHashMap<>(64);
        for (AdvSspSlot advSspSlot : advSspSlotList){
            slot_extendNews.put(advSspSlot.getSlotId(),
                    dfDspAdvCache.getExtendNewsSet(advSspSlot.getTerminal(), advSspSlot.getPgtype()));
            advSspSlotTmp.put(advSspSlot.getSlotId(), advSspSlot);
        }
        if (slot_extendNews.size() > 0){
            extendNewsMap = slot_extendNews;
        }
        if (advSspSlotTmp.size() > 0){
            advSspSlotMap = advSspSlotTmp;
        }
        log.debug("AdvSspSlot Size: {}", advSspSlotList.size());
    }

}
