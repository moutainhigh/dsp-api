package com.songheng.datacenter.common.config;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.common.config.PropertiesLoader;
import com.songheng.dsp.datacenter.job.UpdateConfigCache;
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
 * @date: 2019/2/28 11:42
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class PropertiesLoaderTest {

    @Autowired
    private UpdateConfigCache updateConfigCache;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getAdxConfigTest(){
        updateConfigCache.updateAllConfig();
        Map<String, String> map = PropertiesLoader.getAllProperty();
        System.out.println(map);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
