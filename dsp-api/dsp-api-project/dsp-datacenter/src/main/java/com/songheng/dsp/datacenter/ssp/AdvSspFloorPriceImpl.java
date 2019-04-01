package com.songheng.dsp.datacenter.ssp;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.common.db.DbUtils;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.datacenter.config.db.SqlMapperLoader;
import com.songheng.dsp.dubbo.baseinterface.ssp.AdvSspFloorPriceService;
import com.songheng.dsp.model.ssp.AdvSspFloorPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: luoshaobing
 * @date: 2019/4/1 11:09
 * @description:
 */
@Slf4j
@Service(interfaceClass = AdvSspFloorPriceService.class,
            timeout = 1000)
@Component
public class AdvSspFloorPriceImpl implements AdvSspFloorPriceService {

    /**
     * key: slotId+qid
     * value: List<AdvSspFloorPrice>
     */
    private volatile Map<String, List<AdvSspFloorPrice>> advSspFloorPriceListMap = new ConcurrentHashMap<>(16);

    /**
     * key: slotId+pgnum_idx
     * value: AdvSspFloorPrice
     */
    private volatile Map<String, AdvSspFloorPrice> advSspFloorPriceMap = new ConcurrentHashMap<>(16);


    /**
     * 更新底价列表缓存
     */
    public void updateAdvSspFloorPrice(){
        String sql = SqlMapperLoader.getSql("AdvSspFloorPrice", "queryAdvSspFloorPrice");
        if (StringUtils.isBlank(sql)){
            log.error("updateAdvSspFloorPrice error sql is null, namespace: AdvSspFloorPrice, id: queryAdvSspFloorPrice");
            return;
        }
        List<AdvSspFloorPrice> advSspFloorPriceList = DbUtils.queryList(ProjectEnum.DATACENTER.getDs()[0], sql, AdvSspFloorPrice.class);
        Map<String, List<AdvSspFloorPrice>> advSspFloorPriceListMapTmp = new ConcurrentHashMap<>(32);
        Map<String, AdvSspFloorPrice> advSspFloorPriceTmp = new ConcurrentHashMap<>(32);
        String slotId_qid, slotId_pgnum_idx;
        for (AdvSspFloorPrice advSspFloorPrice : advSspFloorPriceList){
            slotId_qid = String.format("%s%s%s", advSspFloorPrice.getSlotId(), "_", advSspFloorPrice.getQid());
            slotId_pgnum_idx = String.format("%s%s%s%s%s", advSspFloorPrice.getSlotId(), "_", advSspFloorPrice.getPgnum(),
                    "_", advSspFloorPrice.getIdx());
            if (advSspFloorPriceListMapTmp.containsKey(slotId_qid)){
                advSspFloorPriceListMapTmp.get(slotId_qid).add(advSspFloorPrice);
            } else {
                List<AdvSspFloorPrice> list = new ArrayList<>();
                list.add(advSspFloorPrice);
                advSspFloorPriceListMapTmp.put(slotId_qid, list);
            }
            advSspFloorPriceTmp.put(slotId_pgnum_idx, advSspFloorPrice);
        }
        if (advSspFloorPriceListMapTmp.size() > 0){
            advSspFloorPriceListMap = advSspFloorPriceListMapTmp;
        }
        if (advSspFloorPriceTmp.size() > 0){
            advSspFloorPriceMap = advSspFloorPriceTmp;
        }
        log.debug("AdvSspFloorPrice List Size: {}", advSspFloorPriceList.size());
    }

    /**
     * 根据 slotId，qid获取底价列表
     * @param slotId
     * @param qid
     * @return
     */
    @Override
    public List<AdvSspFloorPrice> getFloorPriceListBySlotId(String slotId, String qid) {
        String slotId_qid = String.format("%s%s%s", slotId, "_", qid);
        List<AdvSspFloorPrice> result = advSspFloorPriceListMap.get(slotId);
        return null != result ? result : new ArrayList<AdvSspFloorPrice>();
    }

    /**
     * 获取底价列表
     * @return
     */
    @Override
    public Map<String, List<AdvSspFloorPrice>> getFloorPriceListMap() {
        return advSspFloorPriceListMap;
    }

    /**
     * 根据 slotId，pgnum, idx 获取底价列表
     * @param slotId
     * @param pgnum
     * @param idx
     * @return
     */
    @Override
    public AdvSspFloorPrice getFloorPriceById(String slotId, String pgnum, String idx) {
        String slotId_pgnum_idx = String.format("%s%s%s%s%s", slotId, "_", pgnum, "_", idx);
        AdvSspFloorPrice result = advSspFloorPriceMap.get(slotId_pgnum_idx);
        return null != result ? result : new AdvSspFloorPrice();
    }

    /**
     * 获取底价列表
     * @return
     */
    @Override
    public Map<String, AdvSspFloorPrice> getFloorPriceMap() {
        return advSspFloorPriceMap;
    }
}
