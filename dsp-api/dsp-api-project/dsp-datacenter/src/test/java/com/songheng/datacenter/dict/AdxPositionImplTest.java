package com.songheng.datacenter.dict;

import com.songheng.dsp.datacenter.DataCenterApplication;
import com.songheng.dsp.datacenter.dict.AdxPositionImpl;
import com.songheng.dsp.model.dict.AdPosition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/3/1 20:47
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApplication.class)
public class AdxPositionImplTest {

    @Autowired
    private AdxPositionImpl adxPositionImpl;


    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void getAdxPositionByTml(){
        adxPositionImpl.updateAdPosition();
        List<AdPosition> adPositionList = adxPositionImpl.getAdPositionList("app");
        for (AdPosition adPosition : adPositionList){
            System.out.println(adPosition.getLocation_id() + " : " + adPosition.getLocation_name());
        }
    }

    @Test
    public void getAdxPositionById(){
        adxPositionImpl.updateAdPosition();
        AdPosition adPosition = adxPositionImpl.getAdPositionById("app", "BEC5EC962E4411E896A46C92BF16AE06");
        System.out.println(adPosition.getLocation_id() + " : " + adPosition.getLocation_name());
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
