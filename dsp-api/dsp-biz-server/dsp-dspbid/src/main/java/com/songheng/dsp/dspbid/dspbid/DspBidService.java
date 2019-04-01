package com.songheng.dsp.dspbid.dspbid;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.adx.response.BidBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.client.DspBidClientRequest;
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
public abstract class DspBidService {

    /**
     * 竞价模式 1:优先竞价 0:自由竞价 2：打底竞价
     * **/
    private static final int[] BID_MODEL = {1,0,2};

    /**
     * 获取DSP的竞价服务
     * */
    public ResponseBean getDspResponseBean(DspBidClientRequest request){
        ResponseBean responseBean = this.initResponseBean(request);
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

                //使用ctr模型,对cpc的广告进行点击率预测 通过ctr转化成cpm进行竞价
                this.putCpmByCtr(advList,request,tagId,bidModel);

                //广告排序
                this.advSort(advList);

                //遍历广告
                Iterator<MaterielDirect> advIterator = advList.iterator();
                while(advIterator.hasNext()) {
                    MaterielDirect next = advIterator.next();
                    //过滤重复广告
                    if(deliveryIdFilter.contains(next.getDeliveryId())){
                        iterator.remove();
                    }
                    //广告限速
                    if(advSpeedLimit(next,request,tagId,bidModel)){
                        advIterator.remove();
                        continue;
                    }
                    //广告屏蔽
                    if(shieldAdv(next,request,tagId,bidModel)){
                        advIterator.remove();
                        continue;
                    }
                    //广告定向
                    if(!matchAdv(next,request,tagId,bidModel)){
                        advIterator.remove();
                        continue;
                    }
                }
                //广告RTB实时竞价
                MaterielDirect advInfo = this.dspRtb(advList,request,tagId,bidModel);
                if(null!=advInfo) {
                    deliveryIdFilter.add(advInfo.getDeliveryId());
                    //转化对象信息
                    bidBeans.add(conversion(advInfo));
                    responseBean.setSeatbid(bidBeans);
                }
            }
        }
        return responseBean;

    }

    /**
     * ctr
     * @param advList
     * @param request
     * @param tagId
     * @param bidModel
     */
    protected abstract void putCpmByCtr(List<MaterielDirect> advList, DspBidClientRequest request, String tagId, int bidModel);

    /**
     * 对广告进行排序
     * @param advList
     */
    private void advSort(List<MaterielDirect> advList) {
    }

    /**
     * 广告限速
     * @param next 广告
     * @param request
     * @param tagId
     * @param bidModel
     * @return
     */
    protected abstract boolean advSpeedLimit(MaterielDirect next,DspBidClientRequest request, String tagId, int bidModel);

    /**
     * dsp实时竞价
     * @param advList 可用广告列表
     * @param request 请求信息
     * @param tagId 广告位信息
     * @param bidModel 竞价模式
     * @return 获取竞价成功的广告
     */
    protected abstract MaterielDirect dspRtb(List<MaterielDirect> advList,DspBidClientRequest request, String tagId, int bidModel);

    /**
     * 定向匹配广告
     * @param adv 需要匹配的广告
     * @param request 请求信息
     * @param tagId 广告位
     * @param bidModel 竞价模式
     * @return
     */
    protected abstract boolean matchAdv(MaterielDirect adv, DspBidClientRequest request, String tagId,int bidModel);

    /**
     * 屏蔽广告
     * @param next 广告
     * @param request 请求数据
     * @param tagId 广告位
     * @param bidModel 竞价模式
     * @return
     */
    protected abstract boolean shieldAdv(MaterielDirect next, DspBidClientRequest request, String tagId,int bidModel);

    /**
     * 获取当前位置的广告
     * @param tagId 广告位id
     * @param bidModel 竞价模式
     * @return
     */
    private final List<MaterielDirect> getCurrTagIdAdvList(Map<String, Set<MaterielDirect>> advInfoMap,String tagId,int bidModel){
        List<MaterielDirect> result = new ArrayList<>();
        Set<MaterielDirect> advInfo = (null!=advInfoMap && advInfoMap.containsKey(tagId)) ? advInfoMap.get(tagId) : new TreeSet<MaterielDirect>();
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
     * @param request
     * @return
     */
    private final ResponseBean initResponseBean(DspBidClientRequest request) {
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(request.getBaseFlow().getReqId());
        responseBean.setBidid(RandomUtils.generateRandString("m",20));
        responseBean.setDsp_id(request.getUser().getDspid());
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
