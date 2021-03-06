package com.songheng.dsp.common.db;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/1/22 22:54
 * @description: DB工具类
 * 注：保持Bean属性名与查询sql别名一致
 */
@Slf4j
public class DbUtils {

    /**
     * 单个对象查询
     * @param dsName 数据库名
     * @param sql
     * @param paras
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T queryById(String dsName, String sql, Class<T> cls, Object... paras) {
        Connection conn = DruidConfiguration.getConnection(dsName);
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
     * @param dsName 数据库名
     * @param sql
     * @param paras
     * @return
     */
    public static boolean selectExists(String dsName, String sql, Object... paras){
        Integer num = queryById(dsName, sql, Integer.class, paras);
        return num > 0 ? true : false;
    }

    /**
     * 查询count
     * @param dsName 数据库名
     * @param sql
     * @param paras
     * @return
     */
    public static int selectCount(String dsName, String sql, Object... paras){
        Integer num = queryById(dsName, sql, Integer.class, paras);
        return num.intValue();
    }

    /**
     * 列表查询
     * @param dsName 数据库名
     * @param sql
     * @param paras
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(String dsName, String sql, Class<T> cls, Object... paras){
        Connection conn = DruidConfiguration.getConnection(dsName);
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
     * @param dsName 数据库名
     * @param sql
     * @param jsonObject
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> queryList(String dsName, String sql, Class<T> cls, JSONObject jsonObject){
        Connection conn = DruidConfiguration.getConnection(dsName);
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
                    case "java.util.Date":
                        list.add((T) new java.util.Date(rs.getDate(1).getTime()));
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
                    case "java.math.BigDecimal":
                        list.add((T) rs.getBigDecimal(1));
                        break;
                    default:
                        singleObject = cls.newInstance();
                        for(int i=1; i<=columnCount; i++) {
                            //获取查询sql别名字段
                            String columnName = rsmd.getColumnLabel(i);
                            Field field = getFieldByColumnName(cls, columnName);
                            if (null == field){
                                continue;
                            }
                            field.setAccessible(true);
                            String fieldName = field.getType().getName();
                            switch (fieldName) {
                                case "java.lang.String":
                                    field.set(singleObject, rs.getString(i));
                                    break;
                                case "java.sql.Timestamp":
                                    field.set(singleObject, rs.getTimestamp(i));
                                    break;
                                case "java.sql.Date":
                                    field.set(singleObject, rs.getDate(i));
                                    break;
                                case "java.util.Date":
                                    field.set(singleObject, new java.util.Date(rs.getDate(i).getTime()));
                                    break;
                                case "java.sql.Time":
                                    field.set(singleObject, rs.getTime(i));
                                    break;
                                case "java.lang.Integer":
                                    field.set(singleObject, rs.getInt(i));
                                    break;
                                case "int":
                                    field.set(singleObject, rs.getInt(i));
                                    break;
                                case "java.lang.Long":
                                    field.set(singleObject, rs.getLong(i));
                                    break;
                                case "long":
                                    field.set(singleObject, rs.getLong(i));
                                    break;
                                case "java.lang.Double":
                                    field.set(singleObject, rs.getDouble(i));
                                    break;
                                case "double":
                                    field.set(singleObject, rs.getDouble(i));
                                    break;
                                case "java.lang.Float":
                                    field.set(singleObject, rs.getFloat(i));
                                    break;
                                case "float":
                                    field.set(singleObject, rs.getFloat(i));
                                    break;
                                case "java.lang.Boolean":
                                    field.set(singleObject, rs.getBoolean(i));
                                    break;
                                case "boolean":
                                    field.set(singleObject, rs.getBoolean(i));
                                    break;
                                case "java.math.BigDecimal":
                                    field.set(singleObject, rs.getBigDecimal(i));
                                    break;
                                default:
                                    field.set(singleObject, rs.getObject(i));
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
     * 根据属性名获取Field，包含父类，以及父类的父类直至Object
     * @param cls
     * @param columnName
     * @return
     */
    public static Field getFieldByColumnName(Class<?> cls, String columnName){
        try {
            for (; null != cls && cls != Object.class; cls = cls.getSuperclass()) {
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    int mod = field.getModifiers();
                    //static final 属性跳过
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    if (field.getName().equalsIgnoreCase(columnName)){
                        return field;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析查询结果集，返回Map
     * @param rs
     * @param rltMap
     *
     */
    public static void analysisRst2Map(ResultSet rs, Map<String, String> rltMap){
        if (null == rs){
            return;
        }
        try {
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                rltMap.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("analysisRst2Map failure, ResultSet={}&className={}\t{}", rs, "java.util.Map", e);
        }
    }

    /**
     * 查询，返回Map
     * @param dsName 数据库名
     * @param sql
     * @param paras
     *
     */
    public static void query2Map(String dsName, String sql, Map<String, String> rltMap, Object... paras){
        Connection conn = DruidConfiguration.getConnection(dsName);
        if (null == conn) {
            return;
        }
        PreparedStatement pst = null;
        ResultSet rs = null;
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
            analysisRst2Map(rs, rltMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryList failure, sql={}&parameters={}&className={}\t{}", sql, paras, "java.util.Map", e);
        } finally {
            DruidConfiguration.closeResource(conn, pst, rs);
        }
    }

    /**
     * 存储过程调用，返回Map
     * @param dsName 数据库名
     * @param sql
     * @param paras
     *
     */
    public static void callProcedure2Map(String dsName, String sql, Map<String, String> rltMap, Object... paras){
        Connection conn = DruidConfiguration.getConnection(dsName);
        if (null == conn) {
            return;
        }
        CallableStatement cst = null;
        ResultSet rs = null;
        int index = 1;
        try {
            cst = conn.prepareCall(sql);
            if(paras != null && paras.length > 0) {
                cst.clearParameters();
                for(int i=0; i<paras.length; i++) {
                    cst.setObject(index++, paras[i]);
                }
            }
            rs = cst.executeQuery();
            analysisRst2Map(rs, rltMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("callProcedure failure, sql={}&parameters={}&className={}\t{}", sql, paras, "java.util.Map", e);
        } finally {
            DruidConfiguration.closeResource(conn, cst, rs);
        }
    }

    /**
     * 存储过程调用
     * @param dsName 数据库名
     * @param sql
     * @param cls
     * @param paras
     * @param <T>
     * @return
     */
    public static <T> List<T> callProcedure(String dsName, String sql, Class<T> cls, Object... paras){
        Connection conn = DruidConfiguration.getConnection(dsName);
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
     * 添加事务
     * @param dsName 数据库名
     * @param sql
     * @param paras
     * @return
     */
    public static boolean executeNamingSql(String dsName, String sql, Object... paras) {
        Connection conn = TransactionUtils.getConnection(dsName);
        boolean flag = false;
        int index = 1;
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
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
        }
        return flag;
    }

    /**
     * 批量插入数据
     * @param dsName 数据库名
     * @param sql
     * @param jsonArray
     * @return
     */
    public static void insertBatch(String dsName, String sql, JSONArray jsonArray) {
        Connection conn = DruidConfiguration.getConnection(dsName);
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
