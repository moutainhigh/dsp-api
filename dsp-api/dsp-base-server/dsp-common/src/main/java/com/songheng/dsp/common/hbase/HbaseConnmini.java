package com.songheng.dsp.common.hbase;

import com.songheng.dsp.common.enums.ClusterEnum;
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
     * Configuration B CLUSTER
     */
    private static Configuration conf_B = HBaseConfiguration.create();

    /**
     * List<HConnection> connections B CLUSTER
     */
    private static List<HConnection> connections_B = new ArrayList<>();

    /**
     * Configuration E CLUSTER
     */
    private static Configuration conf_E = HBaseConfiguration.create();
    /**
     * List<HConnection> connections E CLUSTER
     */
    private static List<HConnection> connections_E = new ArrayList<>();

    /**
     * 获取hbase连接 B CLUSTER
     * @return
     */
    public static HConnection getConn_B() {
        if (connections_B.size() == 0){
            return null;
        }
        int id = (int) (System.currentTimeMillis() % connections_B.size());
        return connections_B.get(id);
    }

    /**
     * 获取hbase连接 E CLUSTER
     * @return
     */
    public static HConnection getConn_E() {
        if (connections_E.size() == 0){
            return null;
        }
        int id = (int) (System.currentTimeMillis() % connections_E.size());
        return connections_E.get(id);
    }

    /**
     * 获取 Hbase Table B CLUSTER
     * @param tblName
     * @return
     * @throws Exception
     */
    public static HTableInterface getHbaseTable_B(String tblName) throws IOException {
        return getConn_B().getTable(tblName);
    }

    /**
     * 获取 Hbase Table E CLUSTER
     * @param tblName
     * @return
     * @throws Exception
     */
    public static HTableInterface getHbaseTable_E(String tblName) throws IOException {
        return getConn_E().getTable(tblName);
    }

    /**
     * 初始化创建hbase连接
     * @param cluster
     */
    public static void createHbaseLink(String[] cluster){
        String zkQuorum_B = PropertyPlaceholder.getProperty("hbase.zk.quorum_B");
        String zkQuorum_E = PropertyPlaceholder.getProperty("hbase.zk.quorum_E");
        if (cluster.length >= 2){
            //设置zk集群
            conf_B.set("hbase.zookeeper.quorum", zkQuorum_B);
            conf_E.set("hbase.zookeeper.quorum", zkQuorum_E);
        } else {
            String clusterName = cluster[0];
            if (ClusterEnum.CLUSTER_E.name().equalsIgnoreCase(clusterName)){
                conf_E.set("hbase.zookeeper.quorum", zkQuorum_E);
            } else {
                conf_B.set("hbase.zookeeper.quorum", zkQuorum_B);
            }
        }
        String zkPort = PropertyPlaceholder.getProperty("hbase.zk.clientPort");
        //设置zk端口号
        if (!"localhost".equalsIgnoreCase(conf_B.get("hbase.zookeeper.quorum"))){
            conf_B.set("hbase.zookeeper.property.clientPort", zkPort);
        }
        if (!"localhost".equalsIgnoreCase(conf_E.get("hbase.zookeeper.quorum"))){
            conf_E.set("hbase.zookeeper.property.clientPort", zkPort);
        }
        int linknum = Integer.parseInt(PropertyPlaceholder.getProperty("hbase.linknum"));
        for(int i=0; i<linknum; i++){
            try {
                if (!"localhost".equalsIgnoreCase(conf_B.get("hbase.zookeeper.quorum"))){
                    conf_B.setInt("hbase.client.instance.id",i);
                    connections_B.add(HConnectionManager.createConnection(conf_B));
                }
                if (!"localhost".equalsIgnoreCase(conf_E.get("hbase.zookeeper.quorum"))){
                    conf_E.setInt("hbase.client.instance.id",i);
                    connections_E.add(HConnectionManager.createConnection(conf_E));
                }
                log.info("正常创建第"+(i+1)+"个HBASE连接:"+zkQuorum_B+"\t"+zkQuorum_E+"\t"+zkPort+"\t"+linknum);
                System.out.println("正常创建第"+(i+1)+"个HBASE连接:"+zkQuorum_B+"\t"+zkQuorum_E+"\t"+zkPort+"\t"+linknum);
            } catch (IOException e) {
                log.error("创建第"+(i+1)+"个HBASE连接异常："+e.getMessage());
                System.out.println("创建第"+(i+1)+"个HBASE连接异常："+e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
