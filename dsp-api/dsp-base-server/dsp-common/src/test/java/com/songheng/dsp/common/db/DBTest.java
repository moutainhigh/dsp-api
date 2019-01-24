package com.songheng.dsp.common.db;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.model.AdplatformAdShowHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/1/23 00:13
 * @description:DB工具类测试
 */
public class DBTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init();
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void queryListTest(){
        String sql = "SELECT money FROM adplatform_adShowHistory WHERE adstate <> ? AND FIND_IN_SET(?,positionType) ";
        List<Long> list = DbUtils.queryList(sql, Long.class, -1, "pc");
        System.out.println(list);
    }

    @Test
    public void queryListTest2(){
        String sql = "SELECT hisId, adId, startTime, endTime, statusflag, money, unitprice FROM adplatform_adShowHistory WHERE adstate <> ? AND FIND_IN_SET(?,positionType) ";
        List<AdplatformAdShowHistory> list = DbUtils.queryList(sql, AdplatformAdShowHistory.class, -1, "pc");
        System.out.println(list);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
