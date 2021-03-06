package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 随机工具类
 */
public class RandomUtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown(){
    }

    @Test(timeout = 5)
    public void generateUUID() {
        assertNotNull(RandomUtils.generateUUID(false));
    }

    @Test(timeout = 5)
    public void generateRandNumber() {
        assertNotNull(RandomUtils.generateRandNumber("df",6));
    }

    @Test(timeout = 5)
    public void generateRandString() {
        assertNotNull(RandomUtils.generateRandString("df",6));
    }

    @Test(timeout = 5)
    public void generateTimeMillis() {
        assertNotNull(RandomUtils.generateTimeMillis("df"));
    }

    @Test(timeout = 5)
    public void generateDateRand() {
        assertNotNull(RandomUtils.generateDateRand("df", 5));
    }
}