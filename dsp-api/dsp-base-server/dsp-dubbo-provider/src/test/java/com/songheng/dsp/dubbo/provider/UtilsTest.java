package com.songheng.dsp.dubbo.provider;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 17:46
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderApplication.class)
@WebAppConfiguration
public class UtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }


    @Test
    public void getProperties(){
        String hTabName = PropertyPlaceholder.getProperty("adplatform_adstatus");
        System.out.println(hTabName);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
