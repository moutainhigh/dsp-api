package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * 随机工具类
 */
public class RandomUtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown(){
    }

    @Test(timeout = 5)
    public void generateUUID() {
        for(int i=0;i<5;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    assertNotNull(RandomUtils.generateUUID(false));
                }
            }).start();
        }
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
        for(int i=0;i<10;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    assertNotNull(RandomUtils.generateDateRand("df", 5));
                }
            }).start();
        }
    }
}