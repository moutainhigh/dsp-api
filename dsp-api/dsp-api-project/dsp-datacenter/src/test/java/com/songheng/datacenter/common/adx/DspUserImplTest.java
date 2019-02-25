package com.songheng.datacenter.common.adx;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.common.adx.impl.DspUserImpl;
import com.songheng.dsp.model.adx.user.DspUserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/25 20:46
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class DspUserImplTest {

    @Autowired
    private DspUserImpl dspUserImpl;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getUsersTest(){
        dspUserImpl.updateDspUsers();
        List<DspUserInfo> dspUserInfoList = dspUserImpl.getDspUsers("h5");
        System.out.println(dspUserInfoList);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
