package com.bank.transactions.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {
    private static final int DEFAULT_POOL_SIZE = 1;
    private final int corePoolSize;

    public SpringAsyncConfig(@Value("${spring.task.execution.pool.core-size}") int corePoolSize) {
        if (corePoolSize <= 0) {
            corePoolSize = DEFAULT_POOL_SIZE;
        }
        this.corePoolSize = corePoolSize;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> System.out.println("Async Exception Handler : " + ex.getMessage());
    }
}