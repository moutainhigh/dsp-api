package com.songheng.dsp.dspbid.dspbid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.adx.response.BidBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.client.DspBidClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.flow.ReqSlotInfo;
import com.songheng.dsp.model.materiel.MaterielDirect;
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
    public ResponseBean getDspResponseBean(DspBidClientRequest request){
        ResponseBean responseBean = this.initResponseBean(request.getBaseFlow(),request.getUser());
        List<BidBean> bidBeans = new ArrayList<>();
        //获取广告位列表
        Set<ReqSlotInfo> reqSlotInfos = request.getBaseFlow().getReqSlotInfos();
        //已经展现的投放id
        Set<String> deliveryIdFilter = new HashSet<>(20);
        //遍历所有广告位
        Iterator<ReqSlotInfo> iterator = reqSlotInfos.iterator();
        while(iterator.hasNext()) {
            ReqSlotInfo reqSlotInfo = iterator.next();
            //如果这个广告位无效则直接进入下一个广告位
            if(!request.getBaseFlow().getValidTagIds().contains(reqSlotInfo.getTagId())){
                continue;
            }
            //广告位标识 slotId+pgNum+idx
            String tagId = reqSlotInfo.getTagId();
            for(int i=0,len=BID_MODEL.length;i<len;i++) {
                //竞价模式
                int bidModel = BID_MODEL[i];
                //获取当前模式当前位置的广告列表
                List<MaterielDirect> advList = this.getCurrTagIdAdvList(request.getMateriedInfoByTagIds(),tagId,bidModel);
                //广告限速
                this.advSpeedLimit(advList,request,tagId,bidModel);
                //广告屏蔽
                shieldAdvList(advList,request.getBaseFlow(),tagId,bidModel);
                //广告定向
                matchAdvList(advList,request.getBaseFlow(),tagId,bidModel);
                //广告RTB实时竞价
                MaterielDirect advInfo = this.dspRtb(advList,deliveryIdFilter,request.getBaseFlow(),tagId,bidModel);
                if(null!=advInfo) {
                    deliveryIdFilter.add(advInfo.getDeliveryId());
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
     * 广告限速
     * @param advList
     * @param request
     * @param tagId
     * @param bidModel
     */
    protected abstract void advSpeedLimit(List<MaterielDirect> advList,DspBidClientRequest request, String tagId, int bidModel);


    /**
     * dsp实时竞价-过滤本次已请求到的广告
     * @param advList 可用广告列表
     * @param deliveryIdFilter 只展现的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位信息
     * @param bidModel 竞价模式
     * @return 获取竞价成功的广告
     */
    private final MaterielDirect dspRtb(List<MaterielDirect> advList,Set<String> deliveryIdFilter, BaseFlow baseFlow, String tagId, int bidModel){
        Iterator<MaterielDirect> iterator = advList.iterator();
        while(iterator.hasNext()){
            MaterielDirect materielDirect = iterator.next();
            if(deliveryIdFilter.contains(materielDirect.getDeliveryId())){
                iterator.remove();
            }
        }
        return this.dspRtb(advList,baseFlow,tagId,bidModel);
    }
    /**
     * dsp实时竞价
     * @param advList 可用广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位信息
     * @param bidModel 竞价模式
     * @return 获取竞价成功的广告
     */
    protected abstract MaterielDirect dspRtb(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId, int bidModel);

    /**
     * 定向匹配广告
     * @param advList 已过滤的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位
     * @param bidModel 竞价模式
     */
    protected abstract void matchAdvList(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId,int bidModel);

    /**
     * 屏蔽广告
     * @param advList 可用的广告列表
     * @param baseFlow 流量信息
     * @param tagId 广告位
     * @param bidModel 竞价模式
     */
    protected abstract void shieldAdvList(List<MaterielDirect> advList, BaseFlow baseFlow, String tagId,int bidModel);

    /**
     * 获取当前位置的广告
     * @param tagId 广告位id
     * @param bidModel 竞价模式
     * @return
     */
    private final List<MaterielDirect> getCurrTagIdAdvList(Map<String, Set<MaterielDirect>> advInfoMap,String tagId, int bidModel){
        List<MaterielDirect> result = new ArrayList<>();
        Set<MaterielDirect> advInfo = advInfoMap.containsKey(tagId) ? advInfoMap.get(tagId) : new TreeSet<MaterielDirect>();
        Iterator<MaterielDirect> iterator = advInfo.iterator();
        while(iterator.hasNext()){
            MaterielDirect next = iterator.next();
            if(next.getBidModel() == bidModel){
                result.add(next);
            }
        }
        return result;
    }

    /**
     * 初始化响应对象bean
     * @param baseFlow
     * @param user
     * @return
     */
    private final ResponseBean initResponseBean(BaseFlow baseFlow,DspUserInfo user) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(baseFlow.getReqId());
        responseBean.setBidid(RandomUtils.generateRandString("m",20));
        responseBean.setDsp_id(user.getDspid());
        return responseBean;
    }
    /**
     * 转化广告对象为响应对象
     */
    private BidBean conversion(MaterielDirect advInfo){
        BidBean bid = new BidBean();
        bid.setAdm(advInfo.getDeliveryId());
        bid.setImpid(RandomUtils.generateRandString("b",10));
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("calshow", "1000");
        jsonArray.add(obj);
        bid.setExt(jsonArray);
        return bid;
    }

}
