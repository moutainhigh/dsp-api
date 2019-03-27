package com.songheng.dsp.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

    /**
     * 取两个集合的交集
     * @param sets1
     * @param sets2
     * @return
     */
    public static<T> Set<T> intersection(Set<T> sets1,Set<T> sets2){
        return Sets.intersection(sets1, sets2);
    }
    /**
     * 取两个集合的差集
     * @param sets1
     * @param sets2
     * @return
     */
    public static<T> Set<T> difference(Set<T> sets1,Set<T> sets2){
        return Sets.difference(sets1, sets2);
    }
    /**
     * 取两个集合的并集
     * @param sets1
     * @param sets2
     * @return
     */
    public static<T> Set<T> union(Set<T> sets1,Set<T> sets2){
        return Sets.union(sets1, sets2);
    }

    public static void main(String[] args) {
//        List<String> list = Lists.newArrayList("A","B","C","D","C");
//        //removeDuplicate(list);
//        System.out.println(list);
//        System.out.println(listToSet(list));
//        List<String> str = objsToList("a");
//        System.out.println(str);
        //List<String> strs = objsToList("1","2","3","1");
        //System.out.println(strs);

        Set<String> sets = Sets.newHashSet("1", "2", "3", "4", "5", "6");
        Set<String> sets2 = Sets.newHashSet("3", "4", "5", "6", "7", "8", "9");
        System.out.println(intersection(sets,sets2));
        System.out.println(difference(sets,sets2));
        System.out.println(union(sets,sets2));
    }
}
