package com.songheng.datacenter.ssp;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.materiel.dsp.DfDspAdvCache;
import com.songheng.dsp.datacenter.ssp.AdvSspSlotImpl;
import com.songheng.dsp.model.materiel.MaterielDirect;
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
 * @date: 2019/3/12 21:14
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class AdvSspSlotImplTest {

    @Autowired
    private AdvSspSlotImpl advSspSlot;
    @Autowired
    private DfDspAdvCache dfDspAdvCache;



    @Before
    public void init() {
        System.out.println("开始测试-----------------");
        dfDspAdvCache.updateDfDspAdv();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        advSspSlot.updateAdvSspSlot();
    }

    @Test
    public void getExtendNewsMap(){
        Map<String, Set<MaterielDirect>> result = advSspSlot.getMaterielDirectMap();
        for (String key : result.keySet()){
            Set<MaterielDirect> set = result.get(key);
            System.out.println("key: "+key);
            for (MaterielDirect adv : set){
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
