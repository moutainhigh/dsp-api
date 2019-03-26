package com.songheng.dsp.dspbid.dspbid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.adx.response.BidBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import com.songheng.dsp.model.materiel.ExtendNews;
import lombok.extern.slf4j.Slf4j;
import java.util.*;


/**
 * @description: dsp竞价服务
 * @author: zhangshuai@021.com
 * @date: 2019-03-25 13:53
 **/
@Slf4j
public abstract class DspBidServer {

    /**
     * 竞价模式 1:优先竞价 0:自由竞价 2：打底竞价
     * **/
    private static final int[] BID_MODEL = {1,0,2};

    /**
     * 获取DSP的竞价服务
     * */
    public ResponseBean getDspResponseBean(BaseFlow baseFlow, DspUserInfo user){
        ResponseBean responseBean = this.initResponseBean(baseFlow,user);
        List<BidBean> bidBeans = new ArrayList<>();
        //获取广告位列表
        Set<ReqSlotInfo> reqSlotInfos = baseFlow.getReqSlotInfos();
        //已经展现的投放id
        Set<String> deliveryIdFilter = new HashSet<>(20);
        //遍历所有广告位
        Iterator<ReqSlotInfo> iterator = reqSlotInfos.iterator();
        while(iterator.hasNext()) {
            ReqSlotInfo reqSlotInfo = iterator.next();
            //广告位标识 slotId+pgNum+idx
            String tagId = reqSlotInfo.getTagId();
            for(int i=0,len=BID_MODEL.length;i<len;i++) {
                int bidModel = BID_MODEL[i];
                //过滤当前流量广告位
                if (this.filterCurrTagId(baseFlow,tagId,bidModel)) {
                    continue;
                }
                //获取当前模式当前位置的广告列表
                List<ExtendNews> advList = this.getCurrTagIdAdvList(baseFlow,tagId,bidModel);
                //广告屏蔽
                shieldAdvList(advList,baseFlow,tagId,bidModel);
                //广告定向
                matchAdvList(advList,baseFlow,tagId,bidModel);
                //广告RTB实时竞价
                DspAdvInfo advInfo = dspRtb(advList,deliveryIdFilter,baseFlow,tagId,bidModel);
                if(null!=advInfo) {
                    deliveryIdFilter.add(advInfo.getDeliveryid());
                    //转化对象信息
                    bidBeans.add(conversion(advInfo));
                }

            }
        }
        if(bidBeans.size()>0) {
            responseBean.setSeatbid(bidBeans);
        }
        return responseBean;

    }



    /**
     * dsp实时竞价
     * @param advList 可用广告列表
     * @param deliveryIdFilter 只展现的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位信息
     * @param bidModel 竞价模式
     * @return 获取竞价成功的广告
     */
    protected abstract DspAdvInfo dspRtb(List<ExtendNews> advList,Set<String> deliveryIdFilter, BaseFlow baseFlow, String tagId, int bidModel);

    /**
     * 定向匹配广告
     * @param advList 已过滤的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位
     * @param bidModel 竞价模式
     */
    protected abstract void matchAdvList(List<ExtendNews> advList, BaseFlow baseFlow, String tagId,int bidModel);

    /**
     * 屏蔽广告
     * @param advList 可用的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位
     * @param bidModel 竞价模式
     */
    protected abstract void shieldAdvList(List<ExtendNews> advList, BaseFlow baseFlow, String tagId,int bidModel);

    /**
     * 获取当前位置的广告
     * @param baseFlow 流量信息
     * @param tagId 广告位id
     * @param bidModel 竞价模式
     * @return
     */
    protected abstract List<ExtendNews> getCurrTagIdAdvList(BaseFlow baseFlow, String tagId, int bidModel);

    /**
     * 过滤当前位置信息
     * @param baseFlow 流量信息
     * @param tagId 广告位信息
     *  @param bidModel 竞价模式
     * @return true:过滤 false:不过滤
     * */
    protected abstract boolean filterCurrTagId(BaseFlow baseFlow, String tagId,int bidModel);

    private ResponseBean initResponseBean(BaseFlow baseFlow,DspUserInfo user) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(baseFlow.getReqId());
        responseBean.setBidid(RandomUtils.generateRandString("m",20));
        responseBean.setDsp_id(user.getDspid());
        return responseBean;
    }
    /**
     * 转化广告对象为响应对象
     */
    private BidBean conversion(DspAdvInfo advInfo){
        BidBean bid = new BidBean();
        bid.setAdm(advInfo.getDeliveryid());
        bid.setImpid(RandomUtils.generateRandString("b",10));
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("calshow", "1000");
        jsonArray.add(obj);
        bid.setExt(jsonArray);
        return bid;
    }
}