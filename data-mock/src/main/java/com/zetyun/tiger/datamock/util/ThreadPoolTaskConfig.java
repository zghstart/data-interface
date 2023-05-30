package com.zetyun.tiger.datamock.util;

import java.util.concurrent.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolTaskConfig {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolTaskConfig.class);

    public ThreadPoolTaskConfig() {
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        log.info("start taskExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);
        executor.setMaxPoolSize(core * 2 + 1);
        executor.setKeepAliveSeconds(3);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("thread-execute");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }
}
