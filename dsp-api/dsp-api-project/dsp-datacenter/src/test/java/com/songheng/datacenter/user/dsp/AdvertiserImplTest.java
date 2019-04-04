package com.songheng.datacenter.user.dsp;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.user.adx.DspUserImpl;
import com.songheng.dsp.datacenter.user.dsp.AdvertiserImpl;
import com.songheng.dsp.dubbo.baseinterface.user.dsp.AdvertiserService;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import com.songheng.dsp.model.dsp.AdvertiserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @description: 广告主测试
 * @author: zhangshuai@021.com
 * @date: 2019-04-04 10:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class AdvertiserImplTest {
    @Autowired
    private AdvertiserImpl advertiserImpl;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getUsersTest(){
        advertiserImpl.updateAdvertiser();
        AdvertiserInfo advertiserInfoByUserId = advertiserImpl.getAdvertiserInfoByUserId(1);
        System.out.println(advertiserInfoByUserId);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
