package com.songheng.datacenter.config.db;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.config.db.DbConfigLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


/**
 * @author: luoshaobing
 * @date: 2019/2/28 17:49
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class DbConfigLoaderTest {

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getAdxConfigTest(){
        DbConfigLoader.loadAllDBConfig();
        String key = String.format("%s%s%s", "app", "_", "app_installtimeshieldgrade");
        System.out.println(DbConfigLoader.getDbConfigValue(key));
        Map<String, String> map = DbConfigLoader.getDbConfigMap("h5");
        for (String dspkey : map.keySet()){
            System.out.println(dspkey + " : " + map.get(dspkey));
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
