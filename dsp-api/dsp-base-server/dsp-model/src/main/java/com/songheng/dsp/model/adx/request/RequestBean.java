package com.songheng.dsp.model.adx.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBean {
    private String id;
    private JSONArray imp;
    private Site site;
    private Device device;
    private User user;
    private int at;

    public RequestBean(String reqId){
        this.id = reqId;
    }
}