package com.songheng.dsp.web.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: TODO
 * @author: zhangshuai@021.com
 * @date: 2019-03-13 18:25
 **/
@Getter
@Setter
public class UserInfo {
    private String username;
    private String password;
    public UserInfo(){

    }
    public UserInfo(String username){
        this.username = username;
    }


}
