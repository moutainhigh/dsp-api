package com.songheng.dsp.common.db;

import com.songheng.dsp.common.CommonApplication;
import com.songheng.dsp.common.model.AdplatformAdShowHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/1/23 00:13
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApplication.class)
@WebAppConfiguration
public class DBTest {

    @Autowired
    private DbUtils dbUtils;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void queryListTest(){
        String sql = "SELECT money FROM adplatform_adShowHistory WHERE adstate <> ? AND FIND_IN_SET(?,positionType) ";
        List<Long> list = dbUtils.queryList(sql, Long.class, -1, "pc");
        System.out.println(list);
    }

    @Test
    public void queryListTest2(){
        String sql = "SELECT hisId, adId, startTime, endTime, statusflag, money, unitprice FROM adplatform_adShowHistory WHERE adstate <> ? AND FIND_IN_SET(?,positionType) ";
        List<AdplatformAdShowHistory> list = dbUtils.queryList(sql, AdplatformAdShowHistory.class, -1, "pc");
        System.out.println(list);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
