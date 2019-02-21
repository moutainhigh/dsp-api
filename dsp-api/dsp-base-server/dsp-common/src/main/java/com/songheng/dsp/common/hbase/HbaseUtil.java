package com.songheng.dsp.common.hbase;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
            StringBuffer sb = new StringBuffer();
            sb.append("Get").append("\t").append("read").append("\t").append(tblName).append("\t").append(args0)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
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
            StringBuffer sb = new StringBuffer();
            sb.append("Get Batch").append("\t").append("read").append("\t").append(tblName).append("\t").append(args0)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
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
            StringBuffer sb = new StringBuffer();
            sb.append("Scan").append("\t").append("read").append("\t").append(tblName).append("\t").append(args0)
                    .append("\t").append(null==e.getMessage()?"message:【null】":"message:【"+e.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
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
     * Hbase 写入执行操作  Sentinel 流量监控
     * @param args0
     */
    public static void writeExec(HbaseExecArgs args0){

        if(null == args0 || StringUtils.isBlank(args0.getTblName())
                || StringUtils.isBlank(args0.getResourceKey()) || null == args0.getHbaseExecType()){
            return;
        }

        HTableInterface htab = null;
        Entry entry = null;

        try{
            //对指定资源名进行流量控制，EntryType.IN：“入站”方式
            entry = SphU.entry(args0.getResourceKey(), EntryType.IN);
            htab = HbaseConnmini.getHbaseTable(args0.getTblName());
            switch (args0.getHbaseExecType()){

                case PUT:
                    if (null != args0.getPut()) {
                        htab.put(args0.getPut());
                    }
                    break;
                case INCREMENT:
                    if (null != args0.getIncrement()) {
                        htab.increment(args0.getIncrement());
                    }
                    break;
                case DELETE:
                    if (null != args0.getDelete()) {
                        htab.delete(args0.getDelete());
                    }
                    break;
                case BATCH:
                    if (null != args0.getBatch() && args0.getBatch().size() > 0){
                        if (null != args0.getBatchArgs()){
                            htab.batch(args0.getBatch(), args0.getBatchArgs());
                            break;
                        }
                        htab.batch(args0.getBatch());
                    }
                    break;
                case BATCH_CALLBACK:
                    if (null != args0.getBatch() && args0.getBatch().size() > 0){
                        if (null != args0.getBatchArgs()){
                            htab.batchCallback(args0.getBatch(), args0.getBatchArgs(), args0.getCallBack());
                            break;
                        }
                        htab.batchCallback(args0.getBatch(), args0.getCallBack());
                    }
                    break;
                default:
                    break;

            }

            htab.flushCommits();

        } catch (BlockException e1) {
            //如排队超时，则将未处理数据回写进队列，通过定时任务再次提交指令
            HbaseWriteBack.putWriteBackInfo(args0.getResourceKey(), args0);
        } catch (Exception e2) {
            e2.printStackTrace();
            StringBuffer sb = new StringBuffer();
            sb.append(args0.getHbaseExecType().name()).append("\t").append("write").append("\t").append(args0)
                    .append("\t").append(null==e2.getMessage()?"message:【null】":"message:【"+e2.getMessage().split("\r\n")[0]+"】");
            log.error(sb.toString());
        } finally{
            if (null != htab) {
                try {
                    htab.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != entry) {
                entry.exit();
            }
        }
    }

}
