package com.songheng.dsp.common.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @title: CollectionUtils
 * @package com.songheng.dsp.utils
 * @description: 文件操作工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-25 12:54
 * @version V1.0
 **/
public final class CollectionUtils {

    private CollectionUtils(){}

    /**
     * @description: 将lists集合去重
     * @param lists 需要处理的集合
     * @return 去重后的list集合
     */
    public static<T> List<T> removeDuplicate(List<T> lists){
        if(lists!=null && lists.size()>0) {
            LinkedHashSet<T> set = new LinkedHashSet<>(lists.size());
            set.addAll(lists);
            lists.clear();
            lists.addAll(set);
        }
        return lists;
    }

    public static void main(String[] args) {

    }
}
