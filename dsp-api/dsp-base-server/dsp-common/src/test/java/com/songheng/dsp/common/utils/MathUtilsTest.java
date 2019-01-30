package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 计算工具类
 */
public class MathUtilsTest {
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
    public void add() {
        assertEquals(2.9241,0.1,MathUtils.add(1.2112,1.7133));
    }

    @Test
    public void add1() {
    }

    @Test
    public void sub() {
    }

    @Test
    public void sub1() {
    }

    @Test
    public void mul() {
    }

    @Test
    public void mul1() {
    }

    @Test
    public void div() {
    }

    @Test
    public void div1() {
    }

    @Test
    public void round() {
    }

    @Test
    public void round1() {
    }

    @Test
    public void mode() {
    }

    @Test
    public void doubleToLong() {
    }

    @Test
    public void doubleMulRate() {
    }

    @Test
    public void doubleToLong1() {
    }

    @Test
    public void doubleToInt() {
    }

    @Test
    public void doubleToInt1() {
    }
}