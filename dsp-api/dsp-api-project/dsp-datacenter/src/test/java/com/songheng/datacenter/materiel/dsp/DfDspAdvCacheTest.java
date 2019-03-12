package com.songheng.datacenter.materiel.dsp;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.model.materiel.ExtendNews;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.Set;

/**
 * @author: luoshaobing
 * @date: 2019/3/12 16:42
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class DfDspAdvCacheTest {

    @Autowired
    private DfDspAdvCache dfDspAdvCache;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getExtendNewsSet(){
        dfDspAdvCache.updateDfDspAdv();
        Set<ExtendNews> set = dfDspAdvCache.getExtendNewsSet("h5", "detail");
        if (null == set){
            return;
        }
        for (ExtendNews adv : set){
            System.out.println(adv.getDeliveryId()+" : " + adv.getTotalmoney());
        }
    }

    @Test
    public void getTmlPgTypeExtendNewsMap(){
        dfDspAdvCache.updateDfDspAdv();
        Map<String,Set<ExtendNews>> result = dfDspAdvCache.getTmlPgTypeExtendNewsMap();
        for (String key : result.keySet()){
            System.out.println("key: " + key);
            Set<ExtendNews> set = result.get(key);
            for (ExtendNews adv : set){
//                System.out.println(adv.getDeliveryId()+" : " + adv.getTotalmoney());
                System.out.println(adv);
            }
            System.out.println("-------------------------------------");
        }
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
