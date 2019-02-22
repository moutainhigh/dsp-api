package com.songheng.dsp.common.hbase;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.sentinel.HbaseSentinelResource;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import org.apache.hadoop.hbase.client.Increment;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: luoshaobing
 * @date: 2019/2/22 23:52
 * @description:
 */
public class HbaseWriteBackTest {

    @BeforeClass
    public static void initLoad(){
        InitLoadConf.init(ProjectEnum.H5);
    }

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @Test
    public void serialWriteBackInfos(){
        Increment hisIdIncre = new Increment(new StringBuilder("190108103903642").reverse().toString().getBytes());
        hisIdIncre.addColumn("d".getBytes(), "cost_total".getBytes(),1000L);
        HbaseExecArgs hbaseExecArgs = new HbaseExecArgs(ClusterEnum.CLUSTER_B,PropertyPlaceholder.getProperty("adplatform_adstatus_B"),
                HbaseSentinelResource.ADPLATFORM_ADSTATUS_B.getName(),HbaseExecType.INCREMENT,hisIdIncre);
        HbaseWriteBack.putWriteBackInfo(HbaseSentinelResource.ADPLATFORM_ADSTATUS_B.getName(),hbaseExecArgs);
        HbaseWriteBack hbaseWriteBack = new HbaseWriteBack() {
            @Override
            protected void writeBackSentinelByTimer() {

            }

            @Override
            protected void initialized() {

            }

            @Override
            protected void destroyed() {

            }
        };
        hbaseWriteBack.serialWriteBackInfos();
        hbaseWriteBack.deSerialWriteBackInfos();
        HbaseWriteBack.batchExeHbaseWBInfo(
                HbaseWriteBack.getWriteBackInfo(HbaseSentinelResource.ADPLATFORM_ADSTATUS_B.getName()),1);
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
