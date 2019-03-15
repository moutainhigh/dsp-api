package com.songheng.dsp.web.base.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.songheng.dsp.dubbo.baseinterface.materiel.dsp.DfDspAdvService;
import com.songheng.dsp.model.materiel.ExtendNews;
import com.songheng.dsp.web.model.UserInfo;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @description: 登录页面
 * @author: zhangshuai@021.com
 * @date: 2019-03-13 12:48
 **/
@RestController
public class LoginController {
    @Reference
    private DfDspAdvService dfDspAdvService;


    @RequestMapping(value="/api/login")
    public UserInfo login(@RequestBody UserInfo userInfo){
        System.out.println("登录... \t用户名:"+userInfo.getUsername()+"\t密码："+userInfo.getPassword());
        if("admin".equals(userInfo.getUsername())
                && "dsptest".equals(userInfo.getPassword())){
            return new UserInfo(userInfo.getUsername()+"✨");
        }else{
            return new UserInfo();
        }
    }
    @RequestMapping(value="/api/list")
    public String getAdvList(@RequestBody Map<String,String> param){
        System.out.println(param);
        return JSON.toJSON(dfDspAdvService.getExtendNewsSet(param.get("termail"),param.get("sell"))).toString();
    }
}
