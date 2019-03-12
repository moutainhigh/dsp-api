package com.songheng.dsp.common.enums;

/**
 * @author: luoshaobing
 * @date: 2019/3/7 16:40
 * @description: Hbase 列簇/列 枚举
 */
public enum HbaseFamilyQualifierEnum {

    FAMILY_D("d".getBytes()),
    QUALIFIER_COST_TOTAL("cost_total".getBytes()),
    QUALIFIER_INTERVAL_COST_TOTAL("interval_cost_total".getBytes()),
    QUALIFIER_CURRENTSHOW("currentshow".getBytes()),
    QUALIFIER_SHOW("show".getBytes()),
    QUALIFIER_CALSHOW("calshow".getBytes()),
    QUALIFIER_CURRENTCLICK("currentclick".getBytes()),
    QUALIFIER_CLICK("click".getBytes()),
    QUALIFIER_CLICKSORT("clicksort".getBytes()),
    QUALIFIER_PRESORT("presort".getBytes()),
    QUALIFIER_ADVERTAG("advertag".getBytes()),
    QUALIFIER_SEX("sex".getBytes()),
    QUALIFIER_AGE("age".getBytes()),
    QUALIFIER_CONSUME_LEVEL("consume_level".getBytes()),
    QUALIFIER_SECONDBID_PRICE("secondbid_price".getBytes());

    /**
     * 列簇/列 byte[]
     */
    private byte[] fmlyQlfirName;

    /**
     *  构造
     * @param fmlyQlfirName
     */
    HbaseFamilyQualifierEnum(byte[] fmlyQlfirName){
        this.fmlyQlfirName = fmlyQlfirName;
    }

    /**
     * Getter
     * @return
     */
    public byte[] getFmlyQlfirName() {
        return fmlyQlfirName;
    }

    /**
     * Setter
     * @param fmlyQlfirName
     */
    public void setFmlyQlfirName(byte[] fmlyQlfirName) {
        this.fmlyQlfirName = fmlyQlfirName;
    }
}
