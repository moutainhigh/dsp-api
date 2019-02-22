package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: luoshaobing
 * @date: 2019/1/24 17:15
 * @description: 工具类测试
 */
public class UtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
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
