package com.songheng.dsp.shield.shield.impl;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.model.client.ShiledClientRequest;
import com.songheng.dsp.shield.shield.ShieldServer;

/**
 * @description: 不执行任务屏蔽
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:30
 **/
public class NoneShieldServer extends ShieldServer {
    @Override
    protected void shieldSpecial(ShiledClientRequest request, JSONObject json) {

    }

    @Override
    protected void shieldPublic(ShiledClientRequest request,JSONObject json) {
    }
}
