package com.songheng.dsp.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
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
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }


    @Test
    public void httpGet(){
        for(int i=0;i<1;i++) {
            long base = System.currentTimeMillis();
            HttpClientUtils.httpGet("http://106.75.98.65/dfpcitv/pcitv-test?type=toutiao&qid=test" +
                    "&uid=1480584973985692&readhistory=&pageposition=ny" +
                    "&os=windows&jsonpcallback=jsonp1&newsitetype=ttz", 80);
            System.out.println("耗时:"+(System.currentTimeMillis()-base));
        }
    }

    @Test
    public void httpGet1(){
        for(int i=0;i<1;i++) {
            long base = System.currentTimeMillis();
            String jsonStr = "{\"at\":2,\"device\":{\"imsi\":\"\",\"isp\":\"0\",\"model\":\"SM-G900P\",\"net\":\"100\",\"os\":\"android\",\"osver\":\"android 5.x\",\"serverip\":\"\",\"ua\":\"Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Mobile Safari/537.36\"},\"id\":\"rFDNhQcwxeX\",\"imp\":[{\"id\":\"busY5tUxgcc\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"bb96Hl2YykL\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"bnrth6axSlE\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"b0b7sMaayRX\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"bE4AZHZCvDg\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"bW5FWfN5MFt\",\"bidfloor\":1.0,\"styles\":[]},{\"id\":\"bzczbu9Swon\",\"bidfloor\":1.0,\"styles\":[]}],\"site\":{\"bundle\":\"com.songheng.eastnews\",\"flowtype\":\"h5\",\"id\":\"h5gvCdhbtNPMQpFvLmua\",\"newstype\":\"toutiao\",\"reqtype\":\"list\"},\"user\":{\"age\":\"\",\"buyerid\":\"\",\"deviceid\":\"87125454657653121\",\"gender\":\"\",\"id\":\"1512121381123513211\",\"imei\":\"1512121381123513211\"}}";
            JSONObject json = JSONObject.parseObject(jsonStr);
            HttpClientUtils.httpPost("http://localhost:8888/dsptask/monitor",json,10);
            System.out.println("耗时:"+(System.currentTimeMillis()-base));
        }
    }


    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
