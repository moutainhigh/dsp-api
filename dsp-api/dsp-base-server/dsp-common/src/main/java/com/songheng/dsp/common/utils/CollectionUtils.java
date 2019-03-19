package com.songheng.dsp.common.utils;

import com.google.common.collect.Lists;

import java.util.*;

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
    /**
     * list转treeset
     * */
    public static<T> TreeSet<T> listToSet(List<T> lists){
        if(lists!=null && lists.size()>0) {
            return new TreeSet<>(lists);
        }else{
            return new TreeSet<>();
        }
    }
    /**
     * list转set num
     * */
    public static Set<Integer> listToSetNum(List<String> lists){
       Set<Integer> result = new TreeSet<>();
        for(int i = 0 , len = lists.size(); i<len ; i++){
            String str = lists.get(i);
            if(StringUtils.isNumeric(str)){
                result.add(Integer.parseInt(str));
            }
        }
        return result;
    }
    public static<T> List<T> objsToList(T... objs){
        return Lists.newArrayList(objs);
    }

    public static void main(String[] args) {
//        List<String> list = Lists.newArrayList("A","B","C","D","C");
//        //removeDuplicate(list);
//        System.out.println(list);
//        System.out.println(listToSet(list));
        List<String> str = objsToList("a");
        System.out.println(str);
        //List<String> strs = objsToList("1","2","3","1");
        //System.out.println(strs);
    }
}
