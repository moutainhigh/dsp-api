package com.songheng.dsp.model.adx.request;

import com.alibaba.fastjson.JSONArray;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
//@ToString
public class RequestBean implements Serializable {
    private String id;
    private JSONArray imp;
    private Site site;
    private Device device;
    private User user;
    private int at;

    public RequestBean(){}

    public RequestBean(String reqId){
        this.id = reqId;
    }

    @Override
    public String toString() {
        return id;
    }
}
