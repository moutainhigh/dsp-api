package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.model.AdplatformAdShowHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtilsTest {
    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init("partner", "wap");
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }


    @Test
    public void threadTest(){
        while(true) {
            List<Future> futureList = new ArrayList();
            // 发送100次消息
            for (int i = 0; i < 1000; i++) {
                Future<AdplatformAdShowHistory> messageFuture = ThreadPoolUtils.submit("default",
                        new TestCallable(String.format("这是第{%s}条任务", i)));
                futureList.add(messageFuture);
                try{
                    Thread.sleep(new Random().nextInt(2));
                }catch (Exception e){

                }
            }
            long start = System.currentTimeMillis();
            List<AdplatformAdShowHistory> list = new ArrayList<>();
            try {
                for (Future<AdplatformAdShowHistory> message : futureList) {
                    AdplatformAdShowHistory adv = message.get(100, TimeUnit.MILLISECONDS);
                    list.add(adv);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long diff = System.currentTimeMillis() - start;
            System.out.println(String.format("共计耗时{%s}毫秒,list:{%s}", diff, list.size()));
            try{
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }
    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }


    static class TestCallable implements Callable<AdplatformAdShowHistory> {

        private String message;

        public TestCallable(String message) {
            this.message = message;
        }
        @Override
        public AdplatformAdShowHistory call() throws Exception {
            Thread.sleep(100);
            //System.out.println(Thread.currentThread().getName()+"\t"+String.format("执行任务:%s", message));
            AdplatformAdShowHistory adplatformAdShowHistory = new AdplatformAdShowHistory();
            adplatformAdShowHistory.setAdId(message);
            return adplatformAdShowHistory;
        }

    }
}
