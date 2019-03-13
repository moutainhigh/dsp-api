package com.songheng.dsp.web.base.controller;


import com.songheng.dsp.web.model.UserInfo;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 登录页面
 * @author: zhangshuai@021.com
 * @date: 2019-03-13 12:48
 **/
@RestController
public class LoginController {
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
}
