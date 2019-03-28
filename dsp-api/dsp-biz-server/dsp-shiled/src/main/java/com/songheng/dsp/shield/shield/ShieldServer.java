package com.songheng.dsp.shield.shield;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.DateUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.common.utils.serialize.FastJsonUtils;
import com.songheng.dsp.model.client.ShiledClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.MaterielDirect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @description: 屏蔽服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-28 13:19
 **/
public abstract class ShieldServer {
    /**
     * 时间段分隔符
     * */
    private static final String TIME_SECTION_SPLIT = "-";

    /**
     * 小时 分钟 时间分隔符
     */
    private static final String TIME_SPLIT = ":";

    private static final String[] MATCH_ALL_STR = {"all","全部","*"};

    public void shield(ShiledClientRequest request){
        //公共屏蔽
        JSONObject publicJson = parseJsonStr(request.getPublicShiledJson());
        if(null != publicJson) {
            this.shieldPublic(request,publicJson);
        }
        // 特殊屏蔽
        JSONObject specialJson = parseJsonStr(request.getSpecialShiledJson());
        if(null != specialJson) {
            this.shieldSpecial(request,specialJson);
        }
    }

    /**
     * 特殊屏蔽
     * @param request 请求信息
     * @param json 解析的json
     */
    protected abstract void shieldSpecial(ShiledClientRequest request,JSONObject json);

    /**
     * 公共屏蔽
     * @param request 请求信息
     *  @param json 解析的json
     */
    protected abstract void shieldPublic(ShiledClientRequest request,JSONObject json);

    /**
     * 验证json是否有效
     * @param jsonStr
     * @return
     *      true:有效json
     *      false:无效json
     */
    private JSONObject parseJsonStr(String jsonStr){
        if(StringUtils.isNullOrEmpty(jsonStr)) { return null; }
        try {
            return FastJsonUtils.toJsonObject(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public boolean validateArea(String areas, BaseFlow baseFlow) {
        //不包含地域信息则不屏蔽
        if(StringUtils.isNullOrEmpty(areas)){
            return false;
        }
        List<String> area = StringUtils.strToList(areas);
        //地域包含 "all" 、"全部" 、"*" ,则全屏蔽
        for (String str : MATCH_ALL_STR) {
            if(area.contains(str)){
                return true;
            }
        }
        if(area.contains(baseFlow.getProvince()) || area.contains(baseFlow.getCity())){
            return true;
        }
        return false;
    }



    /**
     * @param time 00:00:00-23:59:59
     * @return
     */
    public boolean validateTime(String time) {
        //时间格式错误
        if(StringUtils.isNullOrEmpty(time) || !time.contains(TIME_SECTION_SPLIT) || !time.contains(TIME_SPLIT)){
            return false;
        }
        try {
            Date nowTime = DateUtils.dateParse(DateUtils.dateFormat(new Date(),DateUtils.DATE_FORMAT_HHMMSS),DateUtils.DATE_FORMAT_HHMMSS);
            Date startTime = DateUtils.dateParse(time.split(TIME_SECTION_SPLIT)[0], DateUtils.DATE_FORMAT_HHMMSS);
            Date endTime = DateUtils.dateParse(time.split(TIME_SECTION_SPLIT)[1], DateUtils.DATE_FORMAT_HHMMSS);
            return DateUtils.isEffectiveDate(nowTime,startTime,endTime);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
