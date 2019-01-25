package com.songheng.dsp.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.songheng.dsp.common.utils.PropertyPlaceholder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 22:19
 * @description:
 */
public class DruidConfiguration {

    /**
     * DataSource
     */
    private static DataSource dataSource;

    /**
     * 初始化 数据源
     * @return
     */
    public static void initDataSource(){
        if (null == dataSource){
            synchronized (DruidDataSource.class) {
                if (null == dataSource){
                    DruidConfiguration.druidDataSource();
                }
            }
        }
    }

    /**
     * 初始化 DruidDataSource
     * @return
     */
    private static void druidDataSource(){
        DruidDataSource dds = new DruidDataSource();
        dds.setUrl(PropertyPlaceholder.getProperty("ds.url"));
        dds.setUsername(PropertyPlaceholder.getProperty("ds.username"));
        dds.setPassword(PropertyPlaceholder.getProperty("ds.password"));
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
        }
        dataSource = dds;
    }

    /**
     * 获取数据库链接
     * @return
     *
     */
    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
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
