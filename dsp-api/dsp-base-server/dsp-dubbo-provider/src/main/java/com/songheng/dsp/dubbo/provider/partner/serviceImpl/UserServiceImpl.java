package com.songheng.dsp.dubbo.provider.partner.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.songheng.dsp.dubbo.baseinterface.partner.service.UserService;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 13:56
 * @description: UserServiceImpl
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String getUser() {
        return "Hello Dubbo";
    }
}
