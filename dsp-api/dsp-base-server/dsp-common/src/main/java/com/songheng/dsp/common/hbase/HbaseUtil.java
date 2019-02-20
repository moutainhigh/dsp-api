package com.songheng.dsp.common.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: luoshaobing
 * @date: 2019/2/20 15:30
 * @description:
 */
@Slf4j
public class HbaseUtil {

    /**
     * 解析hbase 查询结果集
     * 返回 以 qualifier.rowKey 为 key，byte[] 为 value（自行转换成Long/String） 的 Map
     * @param result
     * @return
     */
    public static Map<String, byte[]> analysisRlt(Result result){
        Map<String, byte[]> rltMap = new HashMap<>(16);
        if (null == result || null == result.getRow()){
            return rltMap;
        }
        String qualifier = null;
        String rowKey = Bytes.toString(result.getRow());
        for (Cell cell : result.listCells()){
            //查询一列或者多列
            qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            if (null != qualifier){
                rltMap.put(String.format("%s%s%s", qualifier, ".", rowKey), CellUtil.cloneValue(cell));
            }
        }
        return rltMap;
    }

    /**
     * Hbase Get
     * @param tblName
     * @param args0
     * @return
     */
    public static Map<String, byte[]> get(String tblName, Get args0){
        Map<String, byte[]> rltMap = new HashMap<>(16);
        if (StringUtils.isBlank(tblName) || null == args0){
            return  rltMap;
        }
        HTableInterface htab = null;
        try {
            htab = HbaseConnmini.getHbaseTable(tblName);
            Result rs = htab.get(args0);
            rltMap.putAll(analysisRlt(rs));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != htab){
                try {
                    htab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  rltMap;
    }

    /**
     * Hbase 批量 Get
     * @param tblName
     * @param args0
     * @return
     */
    public static  Map<String, byte[]> get(String tblName, List<Get> args0){
        Map<String, byte[]> rltMap = new HashMap<>(16);
        if (StringUtils.isBlank(tblName) || null == args0 || args0.size() == 0){
            return  rltMap;
        }
        HTableInterface htab = null;
        try {
            htab = HbaseConnmini.getHbaseTable(tblName);
            Result[] rs = htab.get(args0);
            for (Result r : rs){
                rltMap.putAll(analysisRlt(r));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != htab){
                try {
                    htab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  rltMap;
    }

    /**
     * Hbase Scan
     * @param tblName
     * @param args0
     * @return
     */
    public static Map<String, byte[]> scan(String tblName, Scan args0){
        Map<String, byte[]> rltMap = new HashMap<>(16);
        if (StringUtils.isBlank(tblName) || null == args0){
            return  rltMap;
        }
        HTableInterface htab = null;
        try {
            htab = HbaseConnmini.getHbaseTable(tblName);
            ResultScanner rltScan = htab.getScanner(args0);
            for (Result rs : rltScan){
                rltMap.putAll(analysisRlt(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != htab){
                try {
                    htab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  rltMap;
    }


}
