package com.songheng.dsp.common.utils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.CreateMode;

/**
 * @author: luoshaobing
 * @date: 2019/3/26 11:32
 * @description: ZkClientUtils
 */
public class ZkClientUtils {

    /**
     * 获取zk客户端
     * @param zkServers
     * @param sessionTimeout
     * @param connectionTimeout
     * @return
     */
    public static ZkClient getClient(String zkServers, int sessionTimeout, int connectionTimeout){
        return new ZkClient(zkServers, sessionTimeout, connectionTimeout);
    }

    /**
     * 获取zk客户端
     * @param zkServers
     * @param sessionTimeout
     * @param connectionTimeout
     * @param zkSerializer
     * @return
     */
    public static ZkClient getClient(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer){
        return new ZkClient(zkServers, sessionTimeout, connectionTimeout, zkSerializer);
    }

    /**
     * 获取zk客户端
     * @param zkServers
     * @param sessionTimeout
     * @param connectionTimeout
     * @param zkSerializer
     * @param operationRetryTimeout
     * @return
     */
    public static ZkClient getClient(String zkServers, int sessionTimeout, int connectionTimeout, ZkSerializer zkSerializer, long operationRetryTimeout){
        return new ZkClient(zkServers, sessionTimeout, connectionTimeout, zkSerializer, operationRetryTimeout);
    }

    /**
     * 创建zk节点
     * 如果叶子节点为临时节点，则父节点默认都为永久节点
     * @param zkClient zk客户端
     * @param path 节点路径
     * @param createParents 是否递归创建父节点
     * @param data 节点数据
     * @param mode 节点类型 PERSISTENT|PERSISTENT_SEQUENTIAL|EPHEMERAL|EPHEMERAL_SEQUENTIAL
     * @return
     */
    public static String createNode(ZkClient zkClient, String path, boolean createParents, Object data, CreateMode mode){
        if (null == zkClient || StringUtils.isBlank(path)){
            return null;
        }
        String parentPath = getParentPath(path);
        if (StringUtils.isNotBlank(parentPath) && !zkClient.exists(parentPath)){
            //递归创建父节点，父节点默认都是永久节点
            zkClient.createPersistent(parentPath, createParents);
        }
        if (!zkClient.exists(path)){
            return zkClient.create(path, String.valueOf(data), mode);
        }
        return null;
    }

    /**
     * 获取父节点
     * @param path
     * @return
     */
    public static String getParentPath(String path) {
        int lastIndexOf = path.lastIndexOf("/");
        return lastIndexOf != -1 && lastIndexOf != 0 ? path.substring(0, lastIndexOf) : null;
    }

    /**
     * 删除节点
     * @param zkClient
     * @param path
     * @param recursive 是否递归删除子节点
     */
    public static void deleteNode(ZkClient zkClient, String path, boolean recursive){
        if (null == zkClient || StringUtils.isBlank(path)
                || !zkClient.exists(path)){
            return;
        }
        if (recursive){
            //递归删除所有子节点
            zkClient.deleteRecursive(path);
        } else {
            zkClient.delete(path);
        }
    }

    /**
     * 更新节点内容
     * @param zkClient
     * @param path
     * @param data
     */
    public static void updateNode(ZkClient zkClient, String path, Object data){
        if (null == zkClient || StringUtils.isBlank(path)
                || !zkClient.exists(path)){
            return;
        }
        zkClient.writeData(path, String.valueOf(data));
    }

    /**
     * 读取节点内容
     * @param zkClient
     * @param path
     * @return
     */
    public static String readData(ZkClient zkClient, String path){
        if (null == zkClient || StringUtils.isBlank(path)
                || !zkClient.exists(path)){
            return null;
        }
        return zkClient.readData(path);
    }

}
