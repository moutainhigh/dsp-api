package com.songheng.dsp.shield.shield.impl;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.shield.shield.ShieldService;

/**
 * @description: 不执行任务屏蔽
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:30
 **/
public class NoneShieldService extends ShieldService {

    @Override
    protected boolean oppositionShield(JSONObject levelJson, String sectorName, BaseFlow baseFlow) {
        return false;
    }

    @Override
    protected boolean positiveShield(JSONObject levelJson, String sectorName, BaseFlow baseFlow){
        return false;
    }
}
