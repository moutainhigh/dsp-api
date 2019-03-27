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
public class ReqSlotInfo implements Comparable<ReqSlotInfo>{
    /**
     * 单次收费价格转化倍率
     * */
    private static final int PRICE_CONVERSION_RATE = 100000;
    /**
     * 针对cpm价格转化倍率
     * */
    private static final int CPM_PRICE_CONVERSION_RATE = PRICE_CONVERSION_RATE / 1000;

    /**
     * 大维度广告位ID-流量方从ssp申请下来的广告位Id
     * 如 list,detailflow 类型的广告位
     * */
    private String slotId;
    /**
     * 小维度广告位ID
     * 针对信息流类型的广告位,可能一次请求多个位置 拼接形式如下：
     * String.format("%s%s%s%s%s", this.slotId, "_", baseFlow.getPgnum(),"_",idx)
     * */
    private String tagId;

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

    /**
     * 当前idx
     * */
    private int idx;

    public ReqSlotInfo (){}

    /**
     * 从ssp配置中获取相关信息
     * */
    public static ReqSlotInfo buildReqSlotInfo(AdvSspSlot advSspSlot, BaseFlow baseFlow,int idx){
        ReqSlotInfo reqSlotInfo = null;
        if(null!=advSspSlot && null!=baseFlow) {
            baseFlow.setAppId(StringUtils.replaceInvalidString(advSspSlot.getAppId(),""));
            baseFlow.setTerminal(advSspSlot.getTerminal());
            baseFlow.setPgType(advSspSlot.getPgtype());
            baseFlow.setAppName(advSspSlot.getAppName());
            reqSlotInfo = new ReqSlotInfo();
            reqSlotInfo.setIdx(idx);
            reqSlotInfo.slotId = advSspSlot.getSlotId();
            reqSlotInfo.slotName = advSspSlot.getSlotName();
            reqSlotInfo.styleIds = CollectionUtils.listToSetNum(
                 StringUtils.strToList(advSspSlot.getStyleIds())
            );
            reqSlotInfo.styles = CollectionUtils.listToSet(
                 StringUtils.strToList(advSspSlot.getStyleIds())
            );
            //拼接小维度的广告id
            reqSlotInfo.tagId = String.format("%s%s%s%s%s", reqSlotInfo.slotId, "_",
                    baseFlow.getPgnum(),"_",idx);
            //设置有效的广告位id
            baseFlow.getValidTagIds().add(reqSlotInfo.getTagId());
            //查找底价
            reqSlotInfo.minCpm = advSspSlot.getFloorPrice();
            reqSlotInfo.minPrice = MathUtils.doubleToLong(reqSlotInfo.minCpm,
                    CPM_PRICE_CONVERSION_RATE);

        }
        return reqSlotInfo;
    }

    @Override
    public int hashCode() {
        return tagId.hashCode();
    }

    /**
     * tagId 相同则为同一对象
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
        return Objects.equals(tagId, that.tagId);
    }
    @Override
    public int compareTo(ReqSlotInfo reqSlotInfo) {
        if(this.idx>reqSlotInfo.idx){
            return -1;
        }else if(this.idx<reqSlotInfo.idx){
            return 1;
        }
        return 0;
    }

}
