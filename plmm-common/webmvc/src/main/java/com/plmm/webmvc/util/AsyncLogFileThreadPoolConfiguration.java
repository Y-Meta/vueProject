package com.plmm.webmvc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncLogFileThreadPoolConfiguration {
    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 自定义异步线程池
     *
     * @return
     */
    @Bean("tpAsyncLogFileExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("tpAsyncLogFileExecutor-");
        executor.setMaxPoolSize(10);
        executor.setCorePoolSize(5);
        executor.setQueueCapacity(2000);

        // 设置拒绝策略,超过超过线程数
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 使用预定义的异常处理类
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        logger.info("executor {} started...", executor.getThreadNamePrefix());
        return executor;
    }
}
