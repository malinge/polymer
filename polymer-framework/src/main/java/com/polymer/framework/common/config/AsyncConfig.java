package com.polymer.framework.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 注：Spring 代理机制导致的 @Async 失效：同一个类内部直接调用 @Async 方法时，
 * 实际执行的是原始方法（同步），而不是通过代理执行的异步逻辑
 * 被 this 直接调用，Spring 的代理对象并未参与，所以 @Async 注解被忽略，
 * 方法在事件发布者的当前线程（通常是 Web 容器线程）中同步执行
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean("tokenExecutor")
    public Executor tokenExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("token-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean("logExecutor")
    public Executor logExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("log-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy()); // 队列满时丢弃，不阻塞
        executor.initialize();
        return executor;
    }
}
