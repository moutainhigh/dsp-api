package com.songheng.dsp.ssp;

import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.model.client.ClientResponse;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.AntiTheftControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.FlowCheckControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.BlackListControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.SlotIdCheckControl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 风控管理类,遍历所有风控业务
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 14:25
 **/
@Slf4j
public class SspClient extends RiskControl {

    private SspClient(){}
    /***
     *风控列表,后续如需实现新的风控业务,添加即可
     **/
    protected static List<RiskControl> riskVerifications = CollectionUtils.objsToList(
        new FlowCheckControl(),
        new AntiTheftControl(),
        new BlackListControl(),
        new SlotIdCheckControl()
    );

    /**
     *风控链索引，用于遍历风控列表
     **/
    private int index = 0;

    @Override
    public ClientResponse verification(BaseFlow baseFlow,SspClientRequest request, RiskControl riskControl){
        //所有风控业务遍历完,表明风控验证通过
        if (index == riskVerifications.size()){
            return getSuccessResult(baseFlow).setRcNum(index);
        }
        // 通过索引 获取风控列表的风控业务
        RiskControl currentRiskControl = riskVerifications.get(index);
        // 修改索引值，以便下次回调获取下个节点，达到遍历效果
        index++;
        // 将获取到的风控进行验证
        return currentRiskControl.verification(baseFlow, request,this).setRcNum(index);
    }

    @Override
    protected ClientResponse doVerification(BaseFlow baseFlow,SspClientRequest request) {
        //该管理类只负责转发,不负责验证
       throw new IllegalAccessError("SspClient.doVerification(BaseFlow baseFlow)函数不允许调用," +
               "请调用RiskControlClient.verification()函数");
    }

    /**
     * 客户端执行验证函数
     * */
    public static ClientResponse verification(SspClientRequest request){
        //生成流量对象信息
        BaseFlow baseFlow = BaseFlow.generateBaseFlow(request);
        //流量验证
        SspClient client = new SspClient();
        return client.verification(baseFlow,request,client);
    }

    public static void main(String[] args) {
        BaseFlow apiArg = new BaseFlow();
        //获取客户端IP
        String remoteIp = "180.10.12";
        //获取客户端ua
        String ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";
        //获取客户端referer
        String referer = "http://mini.eastday.com/";
        String slotIdStr = "list";
        String qid = "baiducom";

        apiArg.setRemoteIp(remoteIp);
        apiArg.setUa(ua);
        apiArg.setReferer(referer);
        apiArg.setSlotIds(slotIdStr);
        apiArg.setQid(qid);
        apiArg.setSlotIds(StringUtils.replaceInvalidString(apiArg.getSlotIds(),""));
        //获取广告位集合数据
        List<String> slotIds = StringUtils.strToList(apiArg.getSlotIds());
        Map<String, AdvSspSlot> slotMap = new HashMap<>(slotIds.size());
        for(String slotId : slotIds){
            AdvSspSlot advSspSlot = new AdvSspSlot();
            advSspSlot.setSlotId(slotId);
            advSspSlot.setPgtype(slotId);
            advSspSlot.setSspAppId(1);
            advSspSlot.setTerminal("h5");
            slotMap.put(slotId,advSspSlot);
        }
        System.out.println(verification(new SspClientRequest(slotMap,apiArg)));

    }
}
