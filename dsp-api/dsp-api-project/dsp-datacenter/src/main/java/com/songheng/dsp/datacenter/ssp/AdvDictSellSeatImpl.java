package com.songheng.dsp.datacenter.ssp;

import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvDictSellSeatService;
import com.songheng.dsp.model.ssp.AdvDictSellSeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/3/11 22:12
 * @description: dsp售卖位置缓存接口实现类
 */
@Slf4j
@Component
public class AdvDictSellSeatImpl implements AdvDictSellSeatService {

    /**
     * sellSeatId映射 dsp售卖位置
     */
    private volatile Map<String, AdvDictSellSeat> sellSeatIdMap = new ConcurrentHashMap<>(32);
    /**
     * priority映射 dsp售卖位置
     */
    private volatile Map<Integer, AdvDictSellSeat> priorityMap = new ConcurrentHashMap<>(32);

    /**
     *  获取所有 sellSeatId映射 dsp售卖位置
     * @return
     */
    @Override
    public Map<String, AdvDictSellSeat> getSellSeatIdMap(){
        return sellSeatIdMap;
    }

    /**
     * 获取所有 priority映射 dsp售卖位置
     * @return
     */
    @Override
    public Map<Integer, AdvDictSellSeat> getPriorityMap(){
        return priorityMap;
    }

    /**
     * 获取指定 sellSeatId 映射 dsp售卖位置
     * @param sellSeatId
     * @return
     */
    @Override
    public AdvDictSellSeat getAdvDictSellSeat(String sellSeatId){
        if (StringUtils.isBlank(sellSeatId)){
            return new AdvDictSellSeat();
        }
        AdvDictSellSeat advDictSellSeat = sellSeatIdMap.get(sellSeatId);
        return null != advDictSellSeat ? advDictSellSeat : new AdvDictSellSeat();
    }

    /**
     * 获取指定 priority映射 dsp售卖位置
     * @param priority
     * @return
     */
    @Override
    public AdvDictSellSeat getAdvDictSellSeat(int priority){
        AdvDictSellSeat advDictSellSeat = priorityMap.get(priority);
        return null != advDictSellSeat ? advDictSellSeat : new AdvDictSellSeat();
    }


    /**
     * 更新 dsp售卖位置
     */
    public void updateAdvDictSellSeat(){
        String sql = SqlMapperLoader.getSql("AdvSsp", "queryAdvDictSellSeat");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvDictSellSeat error sql is null, namespace: AdvSsp, id: queryAdvDictSellSeat");
            return;
        }
        List<AdvDictSellSeat> advDictSellSeatList = DbUtils.queryList(sql, AdvDictSellSeat.class);
        Map<String, AdvDictSellSeat> sellSeatIdTmp = new ConcurrentHashMap<>(32);
        Map<Integer, AdvDictSellSeat> priorityTmp = new ConcurrentHashMap<>(32);
        for (AdvDictSellSeat advDictSellSeat : advDictSellSeatList){
            if (StringUtils.isNotBlank(advDictSellSeat.getSellSeatId())){
                sellSeatIdTmp.put(advDictSellSeat.getSellSeatId(), advDictSellSeat);
            }
            priorityTmp.put(advDictSellSeat.getPriority(), advDictSellSeat);
        }
        if (sellSeatIdTmp.size() > 0){
            sellSeatIdMap = sellSeatIdTmp;
        }
        if (priorityTmp.size() > 0){
            priorityMap = priorityTmp;
        }
        log.debug("AdvDictSellSeat Size: {}\tSellSeatId Of Map Size: {}\tPriority Of Map Size: {}",
                advDictSellSeatList.size(), sellSeatIdTmp.size(), priorityTmp.size());
    }
}
