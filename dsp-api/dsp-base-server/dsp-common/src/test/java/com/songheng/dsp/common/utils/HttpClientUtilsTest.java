package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: luoshaobing
 * @date: 2019/2/20 17:24
 * @description: HttpClientUtilsTest
 */
public class HttpClientUtilsTest {
    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }


    @Test
    public void httpGet(){
        System.out.println(HttpClientUtils.httpGet("http://106.75.98.65/dfpcitv/pcitv-test?type=toutiao&qid=test&uid=1480584973985692&readhistory=&pageposition=ny&os=windows&jsonpcallback=jsonp1&newsitetype=ttz"));
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
