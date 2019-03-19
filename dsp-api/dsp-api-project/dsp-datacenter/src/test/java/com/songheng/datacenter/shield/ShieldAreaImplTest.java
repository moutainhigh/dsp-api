package com.songheng.datacenter.shield;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.shield.ShieldAreaImpl;
import com.songheng.dsp.model.shield.ShieldArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/3/19 21:01
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class ShieldAreaImplTest {

    @Autowired
    private ShieldAreaImpl shieldAreaImpl;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getShieldAreaInfo(){
        shieldAreaImpl.updateShieldArea();
        Map<String, List<ShieldArea>> map = shieldAreaImpl.getTmlSiteQidMap();
        for (String key : map.keySet()){
            System.out.println("key: " + key);
            for (ShieldArea shieldArea : map.get(key)){
                System.out.println(shieldArea);
            }
            System.out.println("----------------------------");
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
