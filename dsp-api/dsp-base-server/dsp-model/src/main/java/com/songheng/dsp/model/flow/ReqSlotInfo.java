package com.songheng.dsp.model.flow;

import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.common.utils.MathUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

/**
 * @description: 广告位信息
 * @author: zhangshuai@021.com
 * @date: 2019-03-18 19:50
 **/
@Getter
@Setter
@ToString
public class ReqSlotInfo {
    /**
     * 单次收费价格转化倍率
     * */
    private static final int PRICE_CONVERSION_RATE = 100000;
    /**
     * 针对cpm价格转化倍率
     * */
    private static final int CPM_PRICE_CONVERSION_RATE = PRICE_CONVERSION_RATE / 1000;

    /**
     * 广告位ID：密文
     * */
    private String slotId;
    /**
     * 广告位名称
     * */
    private String slotName;

    /**
     * 广告位支持的样式Id
     * */
    private Set<Integer> styleIds;

    /**
     * 广告位支持的样式 big,one,group
     * */
    private Set<String> styles;

    /**
     * 广告位底价
     *    单位：千次曝光收费:元
     * */
    private double minCpm;

    /**
     * 广告位底价
     *    单位: 单次曝光收费*10W：元
     * */
    private long minPrice;


    public ReqSlotInfo(AdvSspSlot advSspSlot, BaseFlow baseFlow){
        if(null!=advSspSlot && null!=baseFlow) {
            this.slotId = advSspSlot.getSlotId();
            this.slotName = advSspSlot.getSlotName();
            this.minCpm = advSspSlot.getFloorPrice();

            this.minPrice = MathUtils.doubleToLong(minCpm,
                    CPM_PRICE_CONVERSION_RATE);
            this.styleIds = CollectionUtils.listToSetNum(
                 StringUtils.strToList(advSspSlot.getStyleIds())
            );
            this.styles = CollectionUtils.listToSet(
                 StringUtils.strToList(advSspSlot.getStyleIds())
            );
            baseFlow.setAppId(advSspSlot.getAppId());
            baseFlow.setAdnum(advSspSlot.getAdnum());
            baseFlow.setTerminal(advSspSlot.getTerminal());
            baseFlow.setPgType(advSspSlot.getPgtype());
            baseFlow.setAppName(advSspSlot.getAppName());
        }
    }

    @Override
    public int hashCode() {
        return slotId.hashCode();
    }

    /**
     * slotId 相同则为同一对象
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReqSlotInfo that = (ReqSlotInfo) o;
        return Objects.equals(slotId, that.slotId);
    }
}
