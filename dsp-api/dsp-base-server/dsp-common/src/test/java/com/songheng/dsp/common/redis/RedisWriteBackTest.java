package com.songheng.dsp.common.redis;

import com.songheng.dsp.common.InitLoadConf;
import com.songheng.dsp.common.enums.ClusterEnum;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.FileUtils;
import com.songheng.dsp.common.utils.serialize.KryoSerialize;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/20 21:23
 * @description:
 */
public class RedisWriteBackTest {
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
        WriteBackObj writeBackObj = new WriteBackObj(RedisCommand.DEL, ClusterEnum.CLUSTER_B, true, "1901020946160550001");
        RedisWriteBack.putWriteBackInfo(writeBackObj);
        RedisWriteBack redisWriteBack = new RedisWriteBack() {
            @Override
            protected void writeBackExcptByTimer() {

            }

            @Override
            protected void initialized() {

            }

            @Override
            protected void destroyed() {

            }
        };
        redisWriteBack.serialWriteBackInfos();
        redisWriteBack.deSerialWriteBackInfos();
        redisWriteBack.writeBack(RedisWriteBack.getWriteBackInfo().poll());
    }

    @Test
    public void test(){
        Put put = new Put((new StringBuilder("14747244720015182").reverse().toString() + new StringBuilder("181121101342262").reverse().toString()).getBytes());
        put.add("d".getBytes(), "calshow".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "currentshow".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "show".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "show".getBytes(), "1001".getBytes());
        put.add("d".getBytes(), "currentclick".getBytes(),"1000".getBytes());
        put.add("d".getBytes(), "click".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "click".getBytes(), "1001".getBytes());
        put.add("d".getBytes(), "currentinview".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "inview".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "inview".getBytes(), "1001".getBytes());
        put.add("d".getBytes(), "calshow".getBytes(), "1002".getBytes());
        put.add("d".getBytes(), "calshow".getBytes(), "1003".getBytes());
        put.add("d".getBytes(), "cost_total".getBytes(), "1000".getBytes());
        put.add("d".getBytes(), "cost_total".getBytes(), "1002".getBytes());

        boolean result = FileUtils.writeFile("/data/dspdatalog/dspsyslog/serial/writebacknew.txt",
                KryoSerialize.writeToString(put), false);
        if (result){
            Put put2 = KryoSerialize.readFromString(FileUtils.readString("/data/dspdatalog/dspsyslog/serial/writebacknew.txt"));
            try {
                System.out.println(put2.toJSON(20));
                System.out.println("-------------------分割线------------------");
                Collection<List<Cell>> celllists = put2.getFamilyCellMap().values();
                for (List<Cell> cells : celllists){
                    for (Cell cell : cells){
                        System.out.println(Bytes.toString(cell.getRow()));
                        System.out.println(Bytes.toString(cell.getFamily()));
                        System.out.println(Bytes.toString(cell.getQualifier()));
                        System.out.println(Bytes.toString(cell.getValue()));
                        System.out.println(cell.getTimestamp());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}
