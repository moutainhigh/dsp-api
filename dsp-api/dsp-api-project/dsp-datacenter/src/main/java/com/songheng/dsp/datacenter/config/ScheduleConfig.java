package com.songheng.dsp.datacenter.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * @author: luoshaobing
 * @date: 2019/3/13 17:05
 * @description: 配置 Spring Boot Schedule 多线程定时任务
 */
@Configuration
@EnableAsync
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {

    /**
     * 并行执行任务
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
    }

    /**
     * 多线程配置
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //设置线程池线程数
        scheduler.setPoolSize(10);
        //设置线程名前缀
        scheduler.setThreadNamePrefix("dsp-dc-task-");
        //线程内容执行完后60秒结束
        scheduler.setAwaitTerminationSeconds(60);
        //等待所有线程执行完
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    /**
     * 异步任务
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        return taskScheduler();
    }

    /**
     * 异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
