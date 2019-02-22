package com.songheng.dsp.common.sentinel;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 10:19
 * @description: Hbase Sentinel Resource Key
 */
public enum HbaseSentinelResource {

    ADX_USER_CONSUME_B("adx_user_consume"),
    ADPLATFORM_ADSTATUS_B("adplatform_adstatus"),
    ADPLATFORM_ADAPPEARED_B("adplatform_adappeared"),
    ADPLATFORM_ADTIMELIMIT_B("adplatform_adtimelimit"),
    ADPLATFORM_ADORDER_B("adplatform_adorder"),
    ADX_DSPRESPONSE_B("adx_dspresponse"),
    ADPLATFORM_ADSTATUS_MONOPOLY_B("adplatform_adstatus_monopoly"),
    ADX_USER_CONSUME_E("adx_user_consume"),
    ADPLATFORM_ADSTATUS_E("adplatform_adstatus_pc"),
    ADPLATFORM_ADAPPEARED_E("adplatform_adappeared"),
    ADPLATFORM_ADTIMELIMIT_E("adplatform_adtimelimit"),
    ADPLATFORM_ADORDER_E("adplatform_adorder"),
    ADX_DSPRESPONSE_E("adx_dspresponse"),
    ADPLATFORM_ADSTATUS_MONOPOLY_E("adplatform_adstatus_monopoly");


    private String name;

    private HbaseSentinelResource(String name){
        this.name = name;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
