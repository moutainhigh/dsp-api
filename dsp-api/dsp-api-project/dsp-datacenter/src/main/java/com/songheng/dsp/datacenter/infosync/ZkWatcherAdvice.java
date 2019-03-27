package com.songheng.dsp.datacenter.infosync;

import com.songheng.dsp.common.utils.HostIpUtils;
import com.songheng.dsp.common.utils.MsgNotifyUtils;
import com.songheng.dsp.common.utils.ZkClientUtils;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/3/26 11:58
 * @description: Zk监听机制
 */
@Component
public class ZkWatcherAdvice implements InitializingBean {

    @Value("${zk.cluster.address}")
    private String zkClusterAddress;

    @Value("${zk.connect.timeout}")
    private int zkConnectTimeOut;

    @Value("${zk.session.timeout}")
    private int zkSessionTimeOut;

    @Value("${zk.subscribe.minutepath}")
    private String zkAdviceMinPath;

    @Value("${zk.subscribe.secondspath}")
    private String zkAdviceSecPath;

    /**
     * zkClient
     */
    private ZkClient zkClient;
    /**
     * minPath
     */
    private String minPath;
    /**
     * secPath
     */
    private String secPath;



    public ZkClient getZkClient() {
        return zkClient;
    }

    public String getMinPath() {
        return minPath;
    }

    public String getSecPath() {
        return secPath;
    }

    /**
     * 注册zk节点内容
     */
    private void register(){
        //获取zkClient连接
        zkClient = ZkClientUtils.getClient(zkClusterAddress, zkConnectTimeOut, zkSessionTimeOut);
        //创建节点
        minPath = ZkClientUtils.createNode(zkClient, zkAdviceMinPath, true,
                System.currentTimeMillis(), CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("创建节点："+minPath);
        secPath = ZkClientUtils.createNode(zkClient, zkAdviceSecPath, true,
                System.currentTimeMillis(), CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("创建节点："+secPath);
        //订阅zk节点连接及状态的变化情况
        zkClient.subscribeStateChanges(new ZkStateListener());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register();
    }

    /**
     * 订阅zk节点连接及状态的变化情况
     */
    private class ZkStateListener implements IZkStateListener {

        /**
         * zk节点连接及状态变化 处理
         * @param keeperState
         * @throws Exception
         */
        @Override
        public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
            if (Watcher.Event.KeeperState.Disconnected == keeperState
                    || Watcher.Event.KeeperState.Expired == keeperState){
                StringBuffer msg = new StringBuffer("【ip:")
                        .append(HostIpUtils.getServerIp())
                        .append("】【dsp-datacenter】【监听zk连接状态：")
                        .append(keeperState.name())
                        .append("】\r\n").append("zk连接下线");
                MsgNotifyUtils.sendMsg(msg.toString());
            }
            System.out.println("zk节点连接及状态变化："+keeperState.name());
        }

        /**
         * 节点Session失效后重连 处理
         * @throws Exception
         */
        @Override
        public void handleNewSession() throws Exception {
            System.out.println("节点Session失效后重连...");
            register();
        }

        @Override
        public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

        }
    }
}
