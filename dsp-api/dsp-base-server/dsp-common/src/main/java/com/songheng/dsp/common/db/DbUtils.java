package com.songheng.dsp.common.db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 22:54
 * @description: DB工具类
 */
@Slf4j
public class DbUtils {

    /**
     * 单个对象查询
     * @param sql
     * @param paras
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T queryById(String sql, Class<T> cls, Object... paras) {
        Connection conn = DruidConfiguration.getConnection();
        if (null == conn) {
            return null;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        T singleObject = null;
        int index = 1;

        try {
            pst = conn.prepareStatement(sql);
            if(paras != null && paras.length > 0) {
                pst.clearParameters();
                for(int i=0; i<paras.length; i++) {
                    pst.setObject(index++, paras[i]);
                }
            }
            rs = pst.executeQuery();
            List<T> list = analysisRst(rs, cls);
            singleObject = null != list ? list.get(0) : null;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryById failure, sql={}&parameters={}&className={}\t{}", sql, paras, cls.getName(), e);
        } finally {
            DruidConfiguration.closeResource(conn, pst, rs);
        }
        return singleObject;
    }

    /**
     * 查询是否存在
     * @param sql
     * @param paras
     * @return
     */
    public static boolean selectExists(String sql, Object... paras){
        Integer num = queryById(sql, Integer.class, paras);
        return num > 0 ? true : false;
    }

    /**
     * 查询count
     * @param sql
     * @param paras
     * @return
     */
    public static int selectCount(String sql, Object... paras){
        Integer num = queryById(sql, Integer.class, paras);
        return num.intValue();
    }

    /**
     * 列表查询
     * @param sql
     * @param paras
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(String sql, Class<T> cls, Object... paras){
        Connection conn = DruidConfiguration.getConnection();
        if (null == conn) {
            return null;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        int index = 1;
        List<T> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            if(paras != null && paras.length > 0) {
                pst.clearParameters();
                for(int i=0; i<paras.length; i++) {
                    pst.setObject(index++, paras[i]);
                }
            }
            rs = pst.executeQuery();
            List<T> tmp = analysisRst(rs, cls);
            if (null != tmp){
                list.addAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryList failure, sql={}&parameters={}&className={}\t{}", sql, paras, cls.getName(), e);
        } finally {
            DruidConfiguration.closeResource(conn, pst, rs);
        }
        return list;
    }

    /**
     * 列表查询
     * @param sql
     * @param jsonObject
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(String sql, Class<T> cls, JSONObject jsonObject){
        Connection conn = DruidConfiguration.getConnection();
        if (null == conn) {
            return null;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
        int index = 1;
        List<T> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            if(jsonObject != null && jsonObject.size() > 0) {
                pst.clearParameters();
                for(int i=0; i<jsonObject.size(); i++) {
                    //jsonObject key 从下标0开始，与预编译参数?顺序一一对应
                    pst.setObject(index++, jsonObject.get(String.valueOf(i)));
                }
            }
            rs = pst.executeQuery();
            List<T> tmp = analysisRst(rs, cls);
            if (null != tmp){
                list.addAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryList failure, sql={}&parameters={}&className={}\t{}", sql, jsonObject, cls.getName(), e);
        } finally {
            DruidConfiguration.closeResource(conn, pst, rs);
        }
        return list;
    }

    /**
     * 解析sql查询结果
     * @param rs
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> analysisRst(ResultSet rs, Class<T> cls){
        if (null == rs){
            return null;
        }
        List<T> list = new ArrayList<>();
        T singleObject = null;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while(rs.next()) {
                switch (cls.getName()) {
                    case "java.lang.String":
                        list.add((T) rs.getString(1));
                        break;
                    case "java.sql.Timestamp":
                        list.add((T) rs.getTimestamp(1));
                        break;
                    case "java.sql.Date":
                        list.add((T) rs.getDate(1));
                        break;
                    case "java.sql.Time":
                        list.add((T) rs.getTime(1));
                        break;
                    case "java.lang.Boolean":
                        list.add((T) Boolean.valueOf(rs.getBoolean(1)));
                        break;
                    case "java.lang.Integer":
                        list.add((T) Integer.valueOf(rs.getInt(1)));
                        break;
                    case "java.lang.Long":
                        list.add((T) Long.valueOf(rs.getLong(1)));
                        break;
                    case "java.lang.Double":
                        list.add((T) Double.valueOf(rs.getDouble(1)));
                        break;
                    case "java.lang.Float":
                        list.add((T) Float.valueOf(rs.getFloat(1)));
                        break;
                    default:
                        singleObject = cls.newInstance();
                        for(int i=0; i<columnCount; i++) {
                            String columnName = rsmd.getColumnName(i+1);
                            Object columnValue = rs.getObject(columnName);
                            Field field = cls.getDeclaredField(columnName);
                            field.setAccessible(true);
                            switch (field.getType().getName()) {
                                case "java.lang.String":
                                    field.set(singleObject, String.valueOf(columnValue));
                                    break;
                                case "java.sql.Timestamp":
                                    if (columnValue instanceof java.sql.Date){
                                        field.set(singleObject, new Timestamp(((Date) columnValue).getTime()));
                                        break;
                                    }
                                    field.set(singleObject, columnValue);
                                    break;
                                case "java.sql.Date":
                                    if (columnValue instanceof java.sql.Timestamp){
                                        field.set(singleObject, new Date(((Timestamp) columnValue).getTime()));
                                        break;
                                    }
                                    field.set(singleObject, columnValue);
                                    break;
                                case "java.sql.Time":
                                    if (columnValue instanceof java.sql.Timestamp){
                                        field.set(singleObject, new Time(((Timestamp) columnValue).getTime()));
                                        break;
                                    } else if (columnValue instanceof java.sql.Date){
                                        field.set(singleObject, new Time(((Date) columnValue).getTime()));
                                        break;
                                    }
                                    field.set(singleObject, columnValue);
                                    break;
                                case "java.lang.Integer":
                                    if (columnValue instanceof java.lang.Integer){
                                        field.set(singleObject, columnValue);
                                        break;
                                    }
                                    field.set(singleObject, new Double(columnValue.toString()).intValue());
                                    break;
                                case "java.lang.Long":
                                    if (columnValue instanceof java.lang.Long){
                                        field.set(singleObject, columnValue);
                                        break;
                                    }
                                    field.set(singleObject, new Double(columnValue.toString()).longValue());
                                    break;
                                case "java.lang.Double":
                                    field.set(singleObject, Double.valueOf(columnValue.toString()));
                                    break;
                                case "java.lang.Float":
                                    field.set(singleObject, Float.valueOf(columnValue.toString()));
                                    break;
                                default:
                                    field.set(singleObject, columnValue);
                                    break;
                            }
                        }
                        list.add(singleObject);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("analysisRst failure, ResultSet={}&className={}\t{}", rs, cls.getName(), e);
        }
        return list;
    }

    /**
     * 存储过程调用
     * @param sql
     * @param cls
     * @param paras
     * @param <T>
     * @return
     */
    public static <T> List<T> callProcedure(String sql, Class<T> cls, Object... paras){
        Connection conn = DruidConfiguration.getConnection();
        if (null == conn) {
            return null;
        }
        CallableStatement cst = null;
        ResultSet rs = null;
        int index = 1;
        List<T> list = new ArrayList<>();
        try {
            cst = conn.prepareCall(sql);
            if(paras != null && paras.length > 0) {
                cst.clearParameters();
                for(int i=0; i<paras.length; i++) {
                    cst.setObject(index++, paras[i]);
                }
            }
            rs = cst.executeQuery();
            List<T> tmp = analysisRst(rs, cls);
            if (null != tmp){
                list.addAll(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("callProcedure failure, sql={}&parameters={}&className={}\t{}", sql, paras, cls.getName(), e);
        } finally {
            DruidConfiguration.closeResource(conn, cst, rs);
        }
        return list;
    }

    /**
     * 执行 INSERT/UPDATE/DELETE 语句
     * @param sql
     * @param paras
     * @return
     */
    public static boolean executeNamingSql(String sql, Object... paras) {
        Connection conn = DruidConfiguration.getConnection();
        PreparedStatement pst = null;
        boolean flag = false;
        int index = 1;
        try {
            pst = conn.prepareStatement(sql);
            if(paras != null && paras.length > 0) {
                pst.clearParameters();
                for(int i=0; i<paras.length; i++) {
                    pst.setObject(index++, paras[i]);
                }
            }
            int result = pst.executeUpdate();
            flag = result > 0 ? true : false;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("executeNamingSql failure, sql={}&parameters={}\t{}", sql, paras, e);
        } finally {
            DruidConfiguration.closeResource(conn, pst);
        }
        return flag;
    }

    /**
     * 批量插入数据
     * @param sql
     * @param jsonArray
     * @return
     */
    public static void insertBatch(String sql, JSONArray jsonArray) {
        Connection conn = DruidConfiguration.getConnection();
        PreparedStatement pst = null;
        int index = 1;
        try {
            //关闭自动提交
            conn.setAutoCommit(false);
            pst = conn.prepareStatement(sql);
            for (int i=0; i<jsonArray.size(); i++){
                // 每1000条记录插入一次
                if (i % 1000 == 0 && i != 0){
                    pst.executeBatch();
                    conn.commit();
                    pst.clearBatch();
                }
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                for (int j=0; j<jsonObject.size(); j++){
                    //jsonObject key 从下标0开始，与预编译参数?顺序一一对应
                    pst.setObject(index++, jsonObject.get(String.valueOf(j)));
                }
                pst.addBatch();
                index = 1;
            }
            // 剩余数量不足1000
            pst.executeBatch();
            conn.commit();
            pst.clearBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("insertBatch failure, sql={}&parameters={}\t{}", sql, jsonArray, e);
        } finally {
            DruidConfiguration.closeResource(conn, pst);
        }
    }

}
