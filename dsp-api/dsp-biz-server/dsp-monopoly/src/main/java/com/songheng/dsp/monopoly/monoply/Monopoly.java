package com.songheng.dsp.monopoly.monoply;


/**
 * @description: 垄断业务
 * @author: zhangshuai@021.com
 * @date: 2019-02-02 13:05
 **/
public abstract class Monopoly {
    /**
     * 具体的整个过程
     */
    protected void doMonopoly(){
        this.getMonopolyAdvByPgType();
        this.doing();
        this.carriedDishes();
    }
    /**
     * 获取这个位置的所有垄断广告
     */
    public abstract void getMonopolyAdvByPgType();
    /**
     * 做菜
     */
    public abstract void doing();
    /**
     * 上菜
     */
    public abstract void carriedDishes ();
}
