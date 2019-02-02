package com.songheng.dsp.ssp.riskcontrol;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.ssp.model.BaseFlow;
import com.songheng.dsp.ssp.model.PcFlow;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.FlowRiskControl;
import com.songheng.dsp.ssp.riskcontrol.riskchain.impl.OtherRiskControl;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 *风控测试类
 **/
public class RiskControlClientTest {


    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    BaseFlow baseFlow = new PcFlow();

    @Before
    public void setUp()  {
        baseFlow.setTestFlow(false);
        baseFlow.setBrushFlow(false);
        baseFlow.setTerminal("pc");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void riskControlPass() {
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));
    }
    @Test
    public void riskControlTest() {
        baseFlow.setTestFlow(true);
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));
    }
    @Test
    public void riskControlBrush() {
        baseFlow.setBrushFlow(true);
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));
    }
    @Test
    public void riskControlNoPc() {
        baseFlow.setTerminal("h5");
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));
    }

    @Test
    public void riskControlNoPassAll() {
        baseFlow.setTerminal("h5");
        baseFlow.setTestFlow(true);
        baseFlow.setBrushFlow(true);
        RiskControlClient client = new RiskControlClient();
        System.out.println(client.verification(baseFlow,client));
    }

    @Test
    public void riskControlOne() {
        baseFlow.setTerminal("h5");
        baseFlow.setTestFlow(false);
        baseFlow.setBrushFlow(true);
        RiskControlClient client = new RiskControlClient();
        System.out.println(new FlowRiskControl().verification(baseFlow,client));
    }
}