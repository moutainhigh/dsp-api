package com.songheng.dsp.monopoly.monoply;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import com.songheng.dsp.common.utils.RandomUtils;
import com.songheng.dsp.model.adx.response.BidBean;
import com.songheng.dsp.model.adx.response.ResponseBean;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.materiel.DspAdvInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * @description: 垄断业务
 * @author: zhangshuai@021.com
 * @date: 2019-02-02 13:05
 **/
@Slf4j
public abstract class Monopoly {

    /**
     *获取垄断广告的响应信息
     **/
    public final List<ResponseBean> getMonopolyAdvResponseBean(BaseFlow baseFlow){
        List<ResponseBean> result = new ArrayList<>();
        if(null == baseFlow || baseFlow.getFlowPositions().size() == 0){
            log.debug("没有获取到流量广告位");
            return result;
        }
        List<String> positions = baseFlow.getFlowPositions();
        int positionSize = positions.size();
        /**
         *已经展现的投放id
         **/
        Set<String> deliveryIdFilter = new HashSet<>(20);
        /**
         * 已经使用过的广告位
         * */
        Set<String> positionFilter = new HashSet<>(20);


        //遍历所有广告位
        for(int i=0;i<positionSize;i++){
            String position = positions.get(i);
            //使用过的广告位,不再使用
            if(positionFilter.contains(position)){
                continue;
            }
            //获取该位置的广告
            List<DspAdvInfo> advInfos = this.getMonopolyAdvByCurPosition(baseFlow,position);
            if(null != advInfos && advInfos.size()>0) {
                this.sort(advInfos);
                //过滤广告 获取可用广告
                DspAdvInfo advInfo = this.getMonopolyAdvInfoByFilter(baseFlow,advInfos,deliveryIdFilter);
                if(null != advInfo) {
                    //将广告对象转化为响应对象
                    ResponseBean responseBean = this.conversion(advInfo, baseFlow, i);
                    result.add(responseBean);
                    //设置返回出去的广告,用于过滤
                    deliveryIdFilter.add(advInfo.getDeliveryid());
                    //设置已经占用的广告位
                    positionFilter.add(position);
                    //设置垄断广告占用的位置
                    baseFlow.getMonopolyPositions().add(position);
                }
            }
        }
        return result;
    }
    /**
     * 获取这个位置的垄断广告
     * @param baseFlow 流量信息
     * @param position 位置
     * @return 所有该位置的垄断广告
     */
    public abstract List<DspAdvInfo> getMonopolyAdvByCurPosition(BaseFlow baseFlow,String position);

    /**
     * 对广告排序
     **/
    public void sort(List<DspAdvInfo> advInfos){
    }

    /**
     * 获取可用的垄断广告
     * @param baseFlow 流量信息
     * @param advInfos 需要过滤的广告列表
     * @param deliveryIdFilter 已经展现过的投放id
     * @return 广告信息
     */
    public final DspAdvInfo getMonopolyAdvInfoByFilter(BaseFlow baseFlow,List<DspAdvInfo> advInfos,Set<String> deliveryIdFilter){
        Iterator<DspAdvInfo> iterator = advInfos.iterator();
        while (iterator.hasNext()){
            DspAdvInfo advInfo = iterator.next();
            //过滤已经展现的广告
            if(deliveryIdFilter.contains(advInfo.getDeliveryid())){
                iterator.remove();
                continue;
            }
            //TODO 公共的过滤(引入屏蔽模块)
            //1、地域屏蔽
            //2、操作系统

            //定制化过滤
            if(isUsefulAdv(baseFlow,advInfo)){
                return advInfo;
            }
        }
        return null;
    }

    /**
     * 判断该广告是否可用,针对不同的终端垄断策略可定制化
     * @param baseFlow 流量信息
     * @param advInfos 需要过滤的广告列表
     * @return 广告信息
     */
    public abstract boolean isUsefulAdv(BaseFlow baseFlow,DspAdvInfo advInfos);



    /**
     * 转化广告对象为响应对象
     */
    public final ResponseBean conversion(DspAdvInfo advInfo,BaseFlow baseFlow,int idx){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setId(baseFlow.getReqId());
        responseBean.setBidid(RandomUtils.generateRandString("m",10));
        responseBean.setDsp_id(PropertyPlaceholder.getProperty("dfdspid"));
        responseBean.setIsmonopolyad(true);
        responseBean.setMonopolyidx(idx);
        responseBean.setPid(advInfo.getPid());
        List<BidBean> bids = new ArrayList<>();
        BidBean bid = new BidBean();
        bid.setAdm(advInfo.getDeliveryid());
        bid.setImpid(RandomUtils.generateRandString("m",10));
        JSONArray jsonArray = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("calshow", "1000");
        jsonArray.add(obj);
        bid.setExt(jsonArray);
        bids.add(bid);
        responseBean.setSeatbid(bids);
        return responseBean;
    }
}
