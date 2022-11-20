package com.killstan.bolg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Kill_Stan
 * @version 1.0
 * @description: 异步线程池
 * @date 2022/6/28 18:35
 */
//@Configuration
public class AysncConfigPool {

    @Bean("asyncPoolTaskExecutor")
    public ThreadPoolTaskExecutor asyncPoolTaskExecutor(){
        //创建线程池
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        //核心线程数量（默认1）
        pool.setCorePoolSize(5);
        //线程池维护的最大线程数
        pool.setMaxPoolSize(20);
        //缓存队列，等待线程数
        pool.setQueueCapacity(20);
        //超出核心线程，空闲时最大存活 时间 单位 秒
        pool.setKeepAliveSeconds(60);
        //线程名
        pool.setThreadNamePrefix("asyncThread");
        // 是不是等待所有线程执行完毕才关闭线程池，默认时false
        pool.setWaitForTasksToCompleteOnShutdown(true);
        // 等待 任务完成关闭的时长，默认时0，不等待
        pool.setAwaitTerminationSeconds(60);
        // 没有线程可被使用时的处理策略（任务拒绝策略）
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        pool.initialize();
        return pool;
    }

}
