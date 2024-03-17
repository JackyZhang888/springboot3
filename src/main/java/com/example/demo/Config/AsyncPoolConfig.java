package com.example.demo.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync // 启用异步注解
@Configuration // 表示这是一个配置类
public class AsyncPoolConfig {
    /**
     * 创建并配置一个线程池任务执行器，用于处理异步任务。
     *
     * @return 返回配置好的Executor实例，用于异步任务的执行。
     */
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 配置线程池的基本属性
        executor.setCorePoolSize(2); // 核心线程数设置为2
        executor.setMaxPoolSize(2); // 最大线程数设置为2
        executor.setQueueCapacity(10); // 队列容量设置为10
        executor.setKeepAliveSeconds(60); // 线程的空闲时间设置为60秒

        // 配置线程名称前缀
        executor.setThreadNamePrefix("executor-");

        // 设置拒绝执行处理器为CallerRunsPolicy，即调用者运行策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return executor;
    }
}
