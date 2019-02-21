package com.songheng.dsp.common.hbase;

/**
 * @author: luoshaobing
 * @date: 2019/2/21 10:23
 * @description: Hbase 写入操作类型 枚举
 */
public enum HbaseExecType {
    PUT,
    INCREMENT,
    BATCH,
    BATCH_CALLBACK,
    DELETE
}
