package com.songheng.dsp.datacenter.job;

import com.songheng.dsp.common.utils.StringUtils;
import com.songheng.dsp.common.utils.ZkClientUtils;
import com.songheng.dsp.datacenter.config.db.DbConfigLoader;
import com.songheng.dsp.datacenter.config.props.PropertiesLoader;
import com.songheng.dsp.datacenter.infosync.ZkWatcherAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: luoshaobing
 * @date: 2019/2/27 10:42
 * @description: 定时任务更新配置文件
 */
@Slf4j
@Component
public class UpdateConfigCache {

    /**
     * zkWatcherAdvice
     */
    @Autowired
    private ZkWatcherAdvice zkWatcherAdvice;


    /**
     * 定时任务更新配置文件缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updatePropertiesConfig(){
        log.debug("开始更新配置文件缓存数据...");
        try {
            PropertiesLoader.loadAllProperties("conf/adxpro.properties");
        } catch (Exception e) {
            log.error("更新配置文件缓存数据失败\n{}",e);
        }
        log.debug("更新配置文件缓存数据成功！");
    }

    /**
     * 定时任务更新DBCONFIG缓存
     * 60秒更新一次
     */
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    public void updateDbConfig(){
        log.debug("开始更新DBCONFIG缓存数据...");
        try {
            DbConfigLoader.loadAllDBConfig();
        } catch (Exception e) {
            log.error("更新DBCONFIG缓存数据失败\n{}",e);
        }
        long startTs = System.currentTimeMillis();
        try {
            String nodeData = ZkClientUtils.readData(zkWatcherAdvice.getZkClient(), zkWatcherAdvice.getSecPath());
            long remoteTs = Long.parseLong(StringUtils.replaceInvalidString(nodeData, "0"));
            if ((startTs - remoteTs) > 60000L){
                //更新节点数据，通知监听该节点的所有客户端更新缓存
                ZkClientUtils.updateNode(zkWatcherAdvice.getZkClient(), zkWatcherAdvice.getSecPath(), startTs);
            }
        } catch (Exception e) {
            log.error("更新zk节点数据失败\tnode path ==>{}\n{}", zkWatcherAdvice.getSecPath(), e);
        }
        log.debug("更新DBCONFIG缓存数据成功！");
    }

}
