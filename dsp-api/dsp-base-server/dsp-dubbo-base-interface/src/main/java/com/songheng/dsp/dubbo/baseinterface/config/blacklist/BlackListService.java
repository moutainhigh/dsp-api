package com.songheng.dsp.dubbo.baseinterface.config.blacklist;

import java.util.List;
import java.util.Map;

/**
 * @description: 黑名单
 * @author: zhangshuai@021.com
 * @date: 2019-04-01 20:53
 **/
public interface BlackListService {

    /**
     * 获取所有黑名单列表
     * key:黑名单文件名 value:黑名单列表
     * @return
     */
    Map<String, List<String>> getAllBlackList();
}
