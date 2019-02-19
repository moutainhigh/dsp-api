package com.songheng.dsp.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description: 线程池工具类
 * @author: zhangshuai@021.com
 * @date: 2019-02-13 15:58
 */
@Slf4j
public class ThreadPoolUtils {

    private ThreadPoolUtils(){}

    /**
     * 根据cpu的数量
     */
    //private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 40;
    /**
     * 线程池最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2;

    /**
     * 任务耗时 s
     */
    private static final double TASK_COST = 0.1;

    /**
    *能够容忍的响应时间 s
    */
    private static final double RESPONSE_TIME = 0.5;
    /**
     * 非核心线程闲置时超时1s
     */
    private static final int KEEP_ALIVE = 1;

    /**
     * 队列容器大小 200
     */
    private static final int QUEUE_CAPACITY = (int)(CORE_POOL_SIZE/TASK_COST*RESPONSE_TIME);

    /**
     * 最大的线程池模块数量
     * */
    private static final int MAX_MODULE_NUM = 100;

    /**
     * 线程池对象
     * */
    private static ConcurrentHashMap<String,ThreadPoolExecutor> threadPoolExecutorMap = new ConcurrentHashMap<>(20);

    /**
     * 初始化线程池
     * */
    static{
        String[] moduleNames = {"default"};
        for(String moduleName : moduleNames) {
            putThreadPool(moduleName);
        }
    }


    /**
     * 获取线程池对象
     * */
    private static ThreadPoolExecutor getThreadPoolExecutor(String moduleName){
        moduleName = StringUtils.replaceInvalidString(moduleName,"default");
        //创建该模块的线程池
        putThreadPool(moduleName);
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(moduleName);
        if(threadPoolExecutor==null){
            threadPoolExecutor = threadPoolExecutorMap.get("default");
        }
        return threadPoolExecutor;
    }
    /**
     * 创建线程池
     * */
    private static void putThreadPool(String moduleName){
        if(!threadPoolExecutorMap.containsKey(moduleName) && threadPoolExecutorMap.size() < MAX_MODULE_NUM){
            //自定义线程工厂
            ThreadFactory threadFactory = new ThreadFactoryBuilder()
                    .setNameFormat(moduleName + "-pool-%d")
                    .build();
            //构建线程池
            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                    CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE,
                    KEEP_ALIVE,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUE_CAPACITY),
                    threadFactory,
                    new DiscardOldestPolicy());
            threadPoolExecutorMap.put(moduleName, threadPool);
            log.info("[创建线程池]:moduleName={}&coreSize={}&maxSize:{}&queueSize:{}",moduleName,CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,QUEUE_CAPACITY);
        }
    }

    /**
     * 执行带返回值对象的任务
     * */
    public static <T> Future<T> submit(String moduleName,Callable<T> callable){
        try {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor(moduleName);
            if(threadPoolExecutor!=null) {
                return threadPoolExecutor.submit(callable);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 执行不带返回值对象的任务
     * */
    public static void submit(String moduleName,Runnable runnable){
        getThreadPoolExecutor(moduleName).execute(runnable);
    }
    /**
     * 获取所有线程模块
     * */
    public static List<String> getAllModuleName(){
        List<String> moduleNames = new ArrayList<>();
        if(threadPoolExecutorMap!=null && threadPoolExecutorMap.size()>0){
            Enumeration<String> enumeration =  threadPoolExecutorMap.keys();
            while(enumeration.hasMoreElements()){
                String moduleName = enumeration.nextElement();
                moduleNames.add(moduleName);
            }
        }
        return moduleNames;
    }
    /**
     * 关闭线程池模块
     * */
    public static void shutdown(){
        List<String> moduleNames =  getAllModuleName();
        for (String module:moduleNames) {
            threadPoolExecutorMap.get(module).shutdown();
            log.info("[关闭线程池]:moduleName={}&coreSize={}&maxSize:{}&queueSize:{}",module,CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,QUEUE_CAPACITY);
        }
    }
    /***
     * 线程拒绝策略:拒绝队列头的任务
     * */
    static class DiscardOldestPolicy implements RejectedExecutionHandler{
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                //移除队头元素
                executor.getQueue().poll();
                log.info("[线程池拒绝任务]:taskCount={}&coreSize={}&maxSize:{}&queueSize:{}",executor.getTaskCount(),CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,QUEUE_CAPACITY);
            }
        }
    }

}
