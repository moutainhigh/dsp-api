package com.songheng.dsp.dubbo.consumer.partner.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.songheng.dsp.dubbo.baseinterface.partner.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 13:56
 * @description: UserController
 */
@RestController
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/getUser")
    public String getUser(){
        return userService.getUser();
    }
}
