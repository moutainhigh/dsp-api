package com.songheng.datacenter.shield;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.shield.AdvDictShieldImpl;
import com.songheng.dsp.model.shield.AdvDictShield;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/29 18:37
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class AdvDictShieldImplTest {

    @Autowired
    private AdvDictShieldImpl advDictShieldImpl;


    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getAdvDictShield(){
        advDictShieldImpl.updateAdvDictShield();
        Map<String, AdvDictShield> map = advDictShieldImpl.getAdvDictShieldMap();
        for (String key : map.keySet()){
            System.out.println("key: " + key);
            System.out.println("value: " + map.get(key));
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
