package com.songheng.dsp.ssp.riskcontrol;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.utils.MathUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *风控测试类
 **/
public class RiskControlClientTest {


    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    @Before
    public void setUp()  {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void riskControl() {

    }

}