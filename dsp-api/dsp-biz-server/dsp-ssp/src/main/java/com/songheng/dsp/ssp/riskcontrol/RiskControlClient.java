package com.songheng.dsp.ssp.riskcontrol;

import com.songheng.dsp.common.utils.CollectionUtils;
import com.songheng.dsp.ssp.model.BaseFlow;
import com.songheng.dsp.ssp.model.PcFlow;
import com.songheng.dsp.ssp.riskcontrol.riskchain.RiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.AntiBrushRiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.FlowRiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.OtherRiskControl;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
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
    private static List<RiskControl> riskVerifications = CollectionUtils.objsToList(
        new FlowRiskControl(),
        new AntiBrushRiskControl(),
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
    public RiskControlResult doVerification(BaseFlow baseFlow) {
        //该管理类只负责转发,不负责验证
       throw new IllegalAccessError("RiskControlClient.doVerification(BaseFlow baseFlow)函数不允许调用," +
               "请调用RiskControlClient.verification()函数");
    }


    public static void main(String[] args) {

        BaseFlow baseFlow = new PcFlow();
        baseFlow.setTestFlow(false);
        baseFlow.setBrushFlow(false);
        baseFlow.setTerminal("pc");
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));


        baseFlow.setTestFlow(true);
        baseFlow.setBrushFlow(false);
        baseFlow.setTerminal("pc");
        RiskControlClient client2 = new RiskControlClient();
        System.out.println(client2.verification(baseFlow,client2));


        baseFlow.setTestFlow(false);
        baseFlow.setBrushFlow(true);
        baseFlow.setTerminal("pc");
        RiskControlClient client3 = new RiskControlClient();
        System.out.println(client3.verification(baseFlow,client3));

        baseFlow.setTestFlow(false);
        baseFlow.setBrushFlow(false);
        baseFlow.setTerminal("h5");
        RiskControlClient client4 = new RiskControlClient();
        System.out.println(client4.verification(baseFlow,client4));


        baseFlow.setTestFlow(true);
        baseFlow.setBrushFlow(true);
        baseFlow.setTerminal("h5");
        RiskControlClient client5 = new RiskControlClient();
        System.out.println(client5.verification(baseFlow,client5));

    }
}
