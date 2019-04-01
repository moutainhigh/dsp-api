package com.songheng.dsp.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.songheng.dsp.common.enums.ProjectEnum;
import com.songheng.dsp.common.utils.PropertyPlaceholder;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 22:19
 * @description: Druid数据库连接池配置
 */
@Slf4j
public class DruidConfiguration {

    /**
     * DataSource
     */
    private static DataSource dataSource;
    /**
     * ctr DataSource
     */
    private static DataSource ctrDataSource;
    /**
     * cloud control DataSource
     */
    private static DataSource ccDataSource;


    /**
     * 初始化 DruidDataSource
     * @param projectName 项目名称
     * @return
     */
    public static void initDataSource(String projectName){
        if (ProjectEnum.H5.getProjectName().equalsIgnoreCase(projectName)
                || ProjectEnum.DATACENTER.getProjectName().equalsIgnoreCase(projectName)){
            dataSource = DruidConfiguration.loadDataSource("advertise");
            ctrDataSource = DruidConfiguration.loadDataSource("ctrmodel");
        } else if (ProjectEnum.CLOUDCONTROL.getProjectName().equalsIgnoreCase(projectName)){
            ccDataSource = DruidConfiguration.loadDataSource("cloudcontrol");
        } else {
            dataSource = DruidConfiguration.loadDataSource("advertise");
        }
    }

    /**
     * 加载druid数据源
     * @param dsName 数据库名
     * @return
     */
    private static DataSource loadDataSource(String dsName){
        log.debug("初始化加载druid数据库 db name ==> {} 连接池配置...", dsName);
        DruidDataSource dds = new DruidDataSource();
        if ("advertise".equals(dsName)) {
            //default
            dds.setUrl(PropertyPlaceholder.getProperty("ds.url"));
            dds.setUsername(PropertyPlaceholder.getProperty("ds.username"));
            dds.setPassword(PropertyPlaceholder.getProperty("ds.password"));
        } else if ("ctrmodel".equals(dsName)){
            //ctr 数据源
            dds.setUrl(PropertyPlaceholder.getProperty("dspctr.url"));
            dds.setUsername(PropertyPlaceholder.getProperty("dspctr.uname"));
            dds.setPassword(PropertyPlaceholder.getProperty("dspctr.pwd"));
        } else if ("cloudcontrol".equals(dsName)){
            //cloud control
            dds.setUrl(PropertyPlaceholder.getProperty("cc.db.url"));
            dds.setUsername(PropertyPlaceholder.getProperty("cc.db.username"));
            dds.setPassword(PropertyPlaceholder.getProperty("cc.db.password"));
        }
        dds.setDriverClassName(PropertyPlaceholder.getProperty("ds.driver"));
        //configuration
        dds.setInitialSize(Integer.parseInt(PropertyPlaceholder.getProperty("ds.initialSize")));
        dds.setMinIdle(Integer.parseInt(PropertyPlaceholder.getProperty("ds.minIdle")));
        dds.setMaxActive(Integer.parseInt(PropertyPlaceholder.getProperty("ds.maxActive")));
        dds.setMaxWait(Long.parseLong(PropertyPlaceholder.getProperty("ds.maxWait")));
        dds.setTimeBetweenEvictionRunsMillis(Long.parseLong(PropertyPlaceholder.getProperty("ds.timeBetweenEvictionRunsMillis")));
        dds.setMinEvictableIdleTimeMillis(Long.parseLong(PropertyPlaceholder.getProperty("ds.minEvictableIdleTimeMillis")));
        dds.setValidationQuery(PropertyPlaceholder.getProperty("ds.validationQuery"));
        dds.setTestWhileIdle(Boolean.parseBoolean(PropertyPlaceholder.getProperty("ds.testWhileIdle")));
        dds.setTestOnBorrow(Boolean.parseBoolean(PropertyPlaceholder.getProperty("ds.testOnBorrow")));
        dds.setTestOnReturn(Boolean.parseBoolean(PropertyPlaceholder.getProperty("ds.testOnReturn")));
        dds.setPoolPreparedStatements(Boolean.parseBoolean(PropertyPlaceholder.getProperty("ds.poolPreparedStatements")));
        dds.setMaxOpenPreparedStatements(Integer.parseInt(PropertyPlaceholder.getProperty("ds.maxOpenPreparedStatements")));
        try {
            dds.setFilters(PropertyPlaceholder.getProperty("ds.filters"));
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: "+ e);
            log.error("druid configuration initialization filter: ", e);
        }
        log.debug("加载druid数据库 db name ==> {} 连接池完毕...", dsName);
        return dds;
    }

    /**
     * 获取数据源 advertise
     * @return
     */
    public static DataSource getDataSource(){
        return dataSource;
    }

    /**
     * 获取数据源 ctrmodel
     * @return
     */
    public static DataSource getCtrDataSource(){
        return ctrDataSource;
    }

    /**
     * 获取数据源 cloudcontrol
     * @return
     */
    public static DataSource getCcDataSource(){
        return ccDataSource;
    }

    /**
     * 获取数据库链接
     * @param dsName 数据库名
     * @return
     *
     */
    public static Connection getConnection(String dsName) {
        Connection connection = null;
        try {
            if ("advertise".equals(dsName)) {
                connection = dataSource.getConnection();
            } else if ("ctrmodel".equals(dsName)){
                connection = ctrDataSource.getConnection();
            } else if ("cloudcontrol".equals(dsName)){
                connection = ccDataSource.getConnection();
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("获取Connection失败", e);
        }
        return null;
    }

    /**
     * 关闭资源
     * @param conn
     * @param stat
     */
    public static void closeResource(Connection conn, Statement stat){
        if (null != stat){
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != conn){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭资源
     * @param conn
     * @param stat
     * @param rs
     */
    public static void closeResource(Connection conn, Statement stat, ResultSet rs){
        if (null != rs){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeResource(conn, stat);
    }
}
