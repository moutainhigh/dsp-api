package com.songheng.dsp.common.hbase;

import com.songheng.dsp.common.enums.ClusterEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.coprocessor.Batch;

import java.util.List;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 10:39
 * @description: Hbase 写入操作 参数类型
 */
@Getter
@Setter
public class HbaseExecArgs {

    /**
     *  所属集群
     */
    private ClusterEnum clusterEnum;
    /**
     * hbase 表名
     */
    private String tblName;
    /**
     * hbase sentinel 资源名
     */
    private String resourceKey;
    /**
     * hbase 写入操作类型
     */
    private HbaseExecType hbaseExecType;
    /**
     * Put
     */
    private Put put;
    /**
     * Increment
     */
    private Increment increment;
    /**
     * Delete
     */
    private Delete delete;
    /**
     * List<? extends Row>
     */
    private List<? extends Row> batch;
    /**
     * Object[]
     */
    private Object[] batchArgs;
    /**
     * Batch.Callback<Object>
     */
    private Batch.Callback<Object> callBack;


    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType,  Put put){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.put = put;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType, Increment increment){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.increment = increment;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType, Delete delete){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.delete = delete;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType, List<? extends Row> batch){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.batch = batch;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType,
                         List<? extends Row> batch, Object[] batchArgs){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.batch = batch;
        this.batchArgs = batchArgs;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType,
                         List<? extends Row> batch, Batch.Callback<Object> callBack){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.batch = batch;
        this.callBack = callBack;
    }

    public HbaseExecArgs(ClusterEnum clusterEnum,String tblName, String resourceKey, HbaseExecType hbaseExecType,
                         List<? extends Row> batch, Object[] batchArgs, Batch.Callback<Object> callBack){
        this.clusterEnum = clusterEnum;
        this.tblName = tblName;
        this.resourceKey = resourceKey;
        this.hbaseExecType = hbaseExecType;
        this.batch = batch;
        this.batchArgs = batchArgs;
        this.callBack = callBack;
    }

}
