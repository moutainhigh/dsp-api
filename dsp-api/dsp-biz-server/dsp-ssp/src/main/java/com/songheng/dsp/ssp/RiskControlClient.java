package com.songheng.dsp.ssp;

import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.model.client.SspClientRequest;
import com.songheng.dsp.model.flow.BaseFlow;
import com.songheng.dsp.model.ssp.AdvSspSlot;
import com.songheng.dsp.ssp.context.RpcServiceContext;
import com.songheng.dsp.ssp.riskcontrol.RiskControlResult;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.AntiTheftControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.FlowCheckControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.BlackListControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.OtherRiskControl;
import com.songheng.dsp.ssp.service.AdvSspSlotLocalService;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;

/**
 * @description: 风控管理类,遍历所有风控业务
 * @author: zhangshuai@021.com
 * @date: 2019-02-01 14:25
 **/
@Slf4j
public class RiskControlClient extends RiskControl {
    /***
     *风控列表,后续如需实现新的风控业务,添加即可
     **/
    protected static List<RiskControl> riskVerifications = CollectionUtils.objsToList(
        new BlackListControl(),
        new FlowCheckControl(),
        new BlackListControl(),
        new AntiTheftControl(),
        new OtherRiskControl()
    );

    /**
     *风控链索引，用于遍历风控列表
     **/
    private int index = 0;

    @Override
    public RiskControlResult verification(BaseFlow baseFlow, RiskControl riskControl){
        //所有风控业务遍历完,表明风控验证通过
        if (index == riskVerifications.size()){
            return getSuccessResult(baseFlow).setRcNum(index);
        }
        // 通过索引 获取风控列表的风控业务
        RiskControl currentRiskControl = riskVerifications.get(index);
        // 修改索引值，以便下次回调获取下个节点，达到遍历效果
        index++;
        // 将获取到的风控进行验证
        return currentRiskControl.verification(baseFlow, this).setRcNum(index);
    }

    @Override
    protected RiskControlResult doVerification(BaseFlow baseFlow) {
        //该管理类只负责转发,不负责验证
       throw new IllegalAccessError("RiskControlClient.doVerification(BaseFlow baseFlow)函数不允许调用," +
               "请调用RiskControlClient.verification()函数");
    }

    /**
     * 客户端执行验证函数
     * */
    public static RiskControlResult verification(SspClientRequest clientRequest){
        //生成流量对象信息
        BaseFlow baseFlow = BaseFlow.generateBaseFlow(clientRequest);
        //流量验证
        RiskControlClient client = new RiskControlClient();
        return client.verification(baseFlow,client);
    }

    public static void main(String[] args) {
        //构建请求信息
        String ua = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36";
        String reqSlotIds = "list";
        String remoteIp = "10.9.119.12";
        BaseFlow reqArgBaseFlow = new BaseFlow();
        reqArgBaseFlow.setNewsClassify("yule");
        reqArgBaseFlow.setGender("1");
        reqArgBaseFlow.setQid("test");
        reqArgBaseFlow.setUserId("121212");
        reqArgBaseFlow.setUserIdType(4);
        reqArgBaseFlow.setPgnum(1);
        reqArgBaseFlow.setReferer("http://www.biadu.com");
        reqArgBaseFlow.setMac("02:00:00:00");
        AdvSspSlotLocalService advSspSlotLocalService = RpcServiceContext.getLocalService(AdvSspSlotLocalService.class);
        Map<String,AdvSspSlot> advSspSlot = advSspSlotLocalService.getAdvSspSlotMap();
        SspClientRequest clientRequest = new SspClientRequest(ua,remoteIp,
                "list",advSspSlot,reqArgBaseFlow);

        //执行风控业务
        System.out.println(verification(clientRequest));
    }
}
