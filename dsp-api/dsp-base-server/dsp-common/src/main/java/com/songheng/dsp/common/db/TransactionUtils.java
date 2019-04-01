package com.songheng.dsp.common.db;

import java.sql.Connection;

/**
 * @author: luoshaobing
 * @date: 2019/1/30 17:36
 * @description: JDBC事务支持
 * 使用方法：一个service方法跨多个dao方法调用
 * 先调用TransactionUtils类的openTransaction方法；
 * 再对所有的dao层调用方法都try ... catch...finally下；
 * try语句最后调用commit方法；
 * catch中调用rollback方法；
 * finally里中调用 release方法。
 */
public class TransactionUtils {

    /**
     * ThreadLocal 共享 Connection
     */
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 获取连接
     * @param dsName 数据库名
     * @return
     */
    public static Connection getConnection(String dsName) {
        // 从ThreadLoacl中获取，如果没有再从DataSource中获取
        Connection conn = threadLocal.get();
        if (conn == null) {
            try {
                conn = DruidConfiguration.getConnection(dsName);
                // 存入ThreadLoacl
                threadLocal.set(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 开启事务
     * @param dsName 数据库名
     */
    public static void startTransaction(String dsName) {
        try {
            Connection conn = threadLocal.get();
            //如果ThreadLoacl中没有，就从DataSource中获取
            if(conn == null) {
                conn = DruidConfiguration.getConnection(dsName);
                //存入
                threadLocal.set(conn);
            }
            conn.setAutoCommit(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        try {
            Connection conn = threadLocal.get();
            if (conn != null) {
                conn.rollback();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public static void commit() {
        try {
            Connection conn = threadLocal.get();
            if (conn != null) {
                conn.commit();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接
     */
    public static void release() {
        try {
            Connection conn = threadLocal.get();
            if(conn != null) {
                conn.close();
                threadLocal.remove();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
