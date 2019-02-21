package com.songheng.dsp.common.sentinel;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 10:19
 * @description: Hbase Sentinel Resource Key
 */
public enum HbaseSentinelResource {

    ADX_USER_CONSUME("adx_user_consume"),
    ADPLATFORM_ADSTATUS("adplatform_adstatus"),
    ADPLATFORM_ADSTATUS_PC("adplatform_adstatus_pc"),
    ADPLATFORM_ADAPPEARED("adplatform_adappeared"),
    ADPLATFORM_ADTIMELIMIT("adplatform_adtimelimit"),
    ADPLATFORM_ADORDER("adplatform_adorder"),
    ADX_DSPRESPONSE("adx_dspresponse"),
    ADPLATFORM_ADSTATUS_MONOPOLY("adplatform_adstatus_monopoly");


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
