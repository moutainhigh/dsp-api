package com.songheng.dsp.adxbid.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.common.utils.serialize.FastJsonUtils;
import com.songheng.dsp.model.adx.request.Device;
import com.songheng.dsp.model.adx.request.RequestBean;
import com.songheng.dsp.model.adx.request.Site;
import com.songheng.dsp.model.adx.request.User;
import com.songheng.dsp.model.flow.AdvPositions;
import com.songheng.dsp.model.flow.BaseFlow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构建请求bean
 * @author zhangshuai@021.com
 * */
public final class BuildAdxBidRequestBean {

    private BuildAdxBidRequestBean(){}

    /**
     * 构建请求对象
     * */
    public static RequestBean buildAdxBidRequestBean(BaseFlow baseFlow){

        RequestBean requestBean = new RequestBean(baseFlow.getReqId());

        //构建竞拍模式
        buildBidModel(baseFlow,requestBean);

        //构建设备对象信息
        buildDeviceInfo(baseFlow,requestBean);

        //构建用户信息
        buildUserInfo(baseFlow,requestBean);

        //构建站点信息
        buildSiteInfo(baseFlow,requestBean);

        //构建曝光对象信息
        buildImplInfo(baseFlow,requestBean);

        return requestBean;
    }

    public static String bidRequestBeanToJsonString(RequestBean requestBean){
        return FastJsonUtils.toJSONString(requestBean);
    }

    /**
     * 构建竞拍模式
     * */
    private static void buildBidModel(BaseFlow baseFlow,RequestBean requestBean){
        //拍卖模式  1:明拍 2:暗拍
        int bidModel = Integer.parseInt(StringUtils.replaceInvalidString(PropertyPlaceholder.getProperty("bidModel"),"2"));
        requestBean.setAt(bidModel);
    }

    /**
     * 构建设备对象信息
     * */
    private static void buildDeviceInfo(BaseFlow baseFlow,RequestBean requestBean){
        Device device = new Device(
                baseFlow.getUa(),
                baseFlow.getRemoteIp(),
                baseFlow.getOs(),
                baseFlow.getNet(),
                baseFlow.getModel(),
                baseFlow.getOsAndVersion()
        ) ;
        requestBean.setDevice(device);
    }

    /**
     * 构建用户信息
     * */
    private static void buildUserInfo(BaseFlow baseFlow,RequestBean requestBean){
        User user = new User(
                baseFlow.getUserId(),
                baseFlow.getGender(),
                baseFlow.getAge(),
                baseFlow.getDeviceId()
        );
        requestBean.setUser(user);
    }
    /**
     * 构建站点信息
     * */
    private static void buildSiteInfo(BaseFlow baseFlow,RequestBean requestBean){
        Site site = new Site(
                baseFlow.getSiteName(),
                baseFlow.getPage(),
                baseFlow.getQid(),
                baseFlow.getReadHistory(),
                baseFlow.getPgType(),
                baseFlow.getNewsType()
        );
        requestBean.setSite(site);
    }


    /**
     * 构建曝光对象
     * */
    private static void buildImplInfo(BaseFlow baseFlow,RequestBean requestBean){
        JSONArray imps = new JSONArray();
        List<AdvPositions> positions = baseFlow.getBidPositions();
        for (AdvPositions advPosition:positions) {
            JSONObject imp = new JSONObject();
            imp.put("id", RandomUtils.generateRandString("b",10));
            imp.put("tagid", advPosition.getTagId());
            imp.put("styles", StringUtils.strToList(advPosition.getOuterStyle()));
            imp.put("bidfloor",advPosition.getFloorPrice());
            imps.add(imp);
        }
        requestBean.setImp(imps);
    }

    public static void main(String[] args) {
        BaseFlow baseFlow = BaseFlow.getTestBaseFlow();
        RequestBean requestBean =  buildAdxBidRequestBean(baseFlow);
        String json = bidRequestBeanToJsonString(requestBean);
        System.out.println(json);
    }


}
