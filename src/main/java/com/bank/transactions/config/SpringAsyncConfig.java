package com.bank.transactions.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {
    private static final int N_CPUS = Runtime.getRuntime().availableProcessors();
    private final int corePoolSize;

    public SpringAsyncConfig(@Value("${spring.task.execution.pool.core-size}") int corePoolSize) {
        this.corePoolSize = corePoolSize <= 0 ? N_CPUS + 1 : corePoolSize;
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
        return (e, method, params) -> log.error("Async exception handler. Exception: {}", e.getMessage());
    }
}