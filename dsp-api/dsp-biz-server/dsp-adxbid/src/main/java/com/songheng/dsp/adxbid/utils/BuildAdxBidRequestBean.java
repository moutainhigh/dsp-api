package com.songheng.dsp.adxbid.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.adx.request.Device;
import com.songheng.dsp.model.adx.request.RequestBean;
import com.songheng.dsp.model.adx.request.Site;
import com.songheng.dsp.model.adx.request.User;
import com.songheng.dsp.model.flow.AdvPositions;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 构建请求bean
 * @author zhangshuai@021.com
 * */
public final class BuildAdxBidRequestBean {

    private BuildAdxBidRequestBean(){}

    /**
     * 竞拍模式：系统固定暗拍模式,不做调整
     * 1：明拍 2：暗拍
     * */
    private final static int BID_MODEL = 2;

    /**
     * 构建请求对象
     * */
    public static RequestBean buildAdxBidRequestBean(BaseFlow baseFlow){

        RequestBean requestBean = new RequestBean(baseFlow.getReqId());

        //构建竞拍模式
        requestBean.setAt(BID_MODEL);

        //构建设备对象信息
        requestBean.setDevice(new Device(baseFlow));

        //构建用户信息
        requestBean.setUser(new User(baseFlow));

        //构建站点信息
        requestBean.setSite(new Site(baseFlow));

        //构建曝光对象信息
        buildImplInfo(baseFlow,requestBean);

        return requestBean;
    }

    /**
     * 构建曝光对象
     * */
    private static void buildImplInfo(BaseFlow baseFlow,RequestBean requestBean){
        JSONArray imps = new JSONArray();
        Set<ReqSlotInfo> reqSlotInfos = baseFlow.getReqSlotInfos();
        Iterator<ReqSlotInfo> iterator = reqSlotInfos.iterator();
        while(iterator.hasNext()){
            ReqSlotInfo reqSlotInfo = iterator.next();
            JSONObject imp = new JSONObject();
            imp.put("id", RandomUtils.generateRandString("b",20));
            imp.put("tagid", reqSlotInfo.getTagId());
            imp.put("styles", reqSlotInfo.getStyles());
            imp.put("bidfloor",reqSlotInfo.getMinCpm());
            imps.add(imp);
        }
        requestBean.setImp(imps);
    }

    public static void main(String[] args) {

    }


}
