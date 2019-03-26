package com.songheng.dsp.common.utils;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ProjectEnum;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: luoshaobing
 * @date: 2019/3/26 11:33
 * @description:
 */
public class ZkClientUtilsTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void zkClientTest(){
        ZkClient zkClient = ZkClientUtils.getClient("10.9.197.173:2181", 5000, 5000);
        String dataPath = ZkClientUtils.createNode(zkClient, "/dsp-dc/advice/data-", true, "001", CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(dataPath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("node data : " + ZkClientUtils.readData(zkClient, dataPath));
        ZkClientUtils.updateNode(zkClient, dataPath, "10000");
        System.out.println("after update node data : " + ZkClientUtils.readData(zkClient, dataPath));

    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
