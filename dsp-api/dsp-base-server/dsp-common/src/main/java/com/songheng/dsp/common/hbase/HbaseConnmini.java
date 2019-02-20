package com.songheng.dsp.common.hbase;

import com.songheng.dsp.common.utils.PropertyPlaceholder;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/19 20:51
 * @description: Hbase连接工具类
 */
@Slf4j
public class HbaseConnmini {

    /**
     * Configuration
     */
    private static Configuration conf = HBaseConfiguration.create();

    /**
     * List<HConnection> connections
     */
    private static List<HConnection> connections = new ArrayList<>();

    /**
     * 获取hbase连接
     * @return
     */
    public static HConnection getConn() {
        if (connections.size() == 0){
            return null;
        }
        int id = (int) (System.currentTimeMillis() % connections.size());
        return connections.get(id);
    }

    /**
     * 获取 Hbase Table
     * @param tblName
     * @return
     * @throws Exception
     */
    public static HTableInterface getHbaseTable(String tblName) throws Exception {
        return getConn().getTable(tblName);
    }

    /**
     * 初始化创建hbase连接
     * @param channel
     */
    public static void createHbaseLink(String channel){
        String zkQuorum = null;
        if ("pc".equalsIgnoreCase(channel)){
            zkQuorum = PropertyPlaceholder.getProperty("hbase.zk.pcquorum");
        } else {//默认wap
            zkQuorum = PropertyPlaceholder.getProperty("hbase.zk.quorum");
        }
        //设置zk集群
        conf.set("hbase.zookeeper.quorum", zkQuorum);
        String zkPort = PropertyPlaceholder.getProperty("hbase.zk.clientPort");
        //设置zk端口号
        conf.set("hbase.zookeeper.property.clientPort", zkPort);
        int linknum = Integer.parseInt(PropertyPlaceholder.getProperty("hbase.linknum"));
        for(int i=0; i<linknum; i++){
            conf.setInt("hbase.client.instance.id",i);
            try {
                connections.add(HConnectionManager.createConnection(conf));
                log.info("正常创建第"+(i+1)+"个HBASE连接:"+zkQuorum+"\t"+zkPort+"\t"+linknum);
                System.out.println("正常创建第"+(i+1)+"个HBASE连接:"+zkQuorum+"\t"+zkPort+"\t"+linknum);
            } catch (IOException e) {
                log.error("创建第"+(i+1)+"个HBASE连接异常："+e.getMessage());
                System.out.println("创建第"+(i+1)+"个HBASE连接异常："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
