package com.songheng.dsp.partner.controller;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.partner.bizflow.IBizFlow;
import com.songheng.dsp.partner.utils.GzipResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 广告素材请求入口
 * @author: zhangshuai@021.com
 * @date: 2019-03-21 13:59
 **/
@RestController
public class AdvReqController {

   @Resource(name="default")
   IBizFlow bizFlow;

    @RequestMapping(value="/api/list")
    public void partner(HttpServletRequest request, HttpServletResponse response, @RequestBody BaseFlow baseFlow){
        System.out.println(baseFlow);
        JSONObject data = new JSONObject();
        data.put("dta",bizFlow.assemble(request,baseFlow));
        data.put("province",baseFlow.getProvince());
        data.put("city",baseFlow.getCity());
        data.put("dt",System.currentTimeMillis());
        String callBack = baseFlow.getJsonpcallback();
        String respData = (null == callBack) ? (data.toJSONString()) : (callBack+ "(" + data.toJSONString() + ")");
        GzipResponse.gzipPrint(request,response,respData);
    }

}
