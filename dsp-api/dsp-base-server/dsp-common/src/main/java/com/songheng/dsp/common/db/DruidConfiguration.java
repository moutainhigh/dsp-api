package com.songheng.dsp.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class DruidConfiguration {

    /**
     * 注意默认是读取application.properties配置文件。
     * 如果配置文件不在默认文件中。
     * 需要在类中引入配置文件例如：@PropertySource(value = "classpath:druid.properties")
     * @return
     */
    @Bean(destroyMethod = "close",initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * 获取数据库链接
     * @return
     *
     */
    public Connection getConnection() {
        DataSource dataSource = druidDataSource();
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
    public void closeResource(Connection conn, Statement stat){
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
    public void closeResource(Connection conn, Statement stat, ResultSet rs){
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
