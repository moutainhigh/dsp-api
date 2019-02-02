package com.songheng.dsp.common.utils;

import com.google.common.collect.Lists;

import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @description: 集合工具类
 * @author: zhangshuai@021.com
 * @date: 2019-01-25 12:54
 **/
public class CollectionUtils {

    public static<T> List<T> removeDuplicate(List<T> lists){
        if(lists!=null && lists.size()>0) {
            LinkedHashSet<T> set = new LinkedHashSet<>(lists.size());
            set.addAll(lists);
            lists.clear();
            lists.addAll(set);
        }
        return lists;
    }

    public static<T> List<T> objsToList(T... objs){
        return Lists.newArrayList(objs);
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("A","B","C","D","C");
        removeDuplicate(list);
        System.out.println(list);
        List<String> strs = objsToList("1","2","3");
        System.out.println(strs);
    }
}
