package com.songheng.dsp.partner.dc.infosync;

import com.songheng.dsp.common.utils.HostIpUtils;
import com.songheng.dsp.common.utils.MsgNotifyUtils;
import com.songheng.dsp.common.utils.ZkClientUtils;
import com.songheng.dsp.partner.dc.invoke.DspUserInvoke;
import com.songheng.dsp.partner.dc.invoke.OtherDspAdvInvoke;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/3/27 21:21
 * @description: Zk订阅
 */
@Component
public class ZkWatcherSubscriber implements InitializingBean {

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

    @Autowired
    private DspUserInvoke dspUserInvoke;

    @Autowired
    private OtherDspAdvInvoke otherDspAdvInvoke;



    /**
     * zkClient
     */
    private ZkClient zkClient;


    /**
     * getZkClient
     * @return
     */
    public ZkClient getZkClient() {
        return zkClient;
    }

    /**
     * 订阅zk节点信息
     */
    private void subscriber(){
        //获取zkClient连接
        zkClient = ZkClientUtils.getClient(zkClusterAddress, zkConnectTimeOut, zkSessionTimeOut);
        String parentMinPath = ZkClientUtils.getParentPath(zkAdviceMinPath);
        String parentSecPath = ZkClientUtils.getParentPath(zkAdviceSecPath);
        //获得子节点
        List<String> childListByMinPath = zkClient.getChildren(parentMinPath);
        List<String> childListBySecPath = zkClient.getChildren(parentSecPath);
        for (String childNode : childListByMinPath){
            zkClient.subscribeDataChanges(parentMinPath+"/"+childNode, new ZkDataListener());
        }
        for (String childNode : childListBySecPath){
            zkClient.subscribeDataChanges(parentSecPath+"/"+childNode, new ZkDataListener());
        }
        zkClient.subscribeStateChanges(new ZkStateListener());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        subscriber();
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
            subscriber();
        }

        @Override
        public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

        }
    }

    /**
     * 订阅zk节点数据变化
     */
    private class ZkDataListener implements IZkDataListener {

        /**
         * 节点数据变化 处理
         * @param dataPath
         * @param data
         * @throws Exception
         */
        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            System.out.println("节点数据变化："+dataPath+"-数据："+data.toString());
            //更新缓存
            dspUserInvoke.updateDspUsers();
            otherDspAdvInvoke.updateOtherDspAdv();
        }

        /**
         * 节点删除 处理
         * @param dataPath
         * @throws Exception
         */
        @Override
        public void handleDataDeleted(String dataPath) throws Exception {
            System.out.println("节点数据删除："+dataPath);
        }
    }

}
