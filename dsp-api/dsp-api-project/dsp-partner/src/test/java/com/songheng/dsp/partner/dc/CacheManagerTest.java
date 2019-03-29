package com.songheng.dsp.partner.dc;

import com.songheng.dsp.partner.PartnerApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: luoshaobing
 * @date: 2019/3/27 20:12
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerApplication.class)
public class CacheManagerTest {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheableTest cacheableTest;


    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }


    @Test
    public void cacheManagerTest(){
        Cache cache = cacheManager.getCache("adx_user");
        cache.put("getTerminal", "pc");
        String termianl = cacheableTest.getTerminal();
        System.out.println("get method cache: "+termianl);
        String tml = (String) cache.get("getTerminal").get();
        System.out.println("get cache manager: "+tml);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
