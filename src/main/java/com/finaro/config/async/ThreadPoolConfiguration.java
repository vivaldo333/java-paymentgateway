package com.finaro.config.async;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
@RequiredArgsConstructor
public class ThreadPoolConfiguration extends AsyncConfigurerSupport {
    private final ThreadPoolProperties threadPoolProperties;

    //@Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        threadPoolTaskExecutor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        threadPoolTaskExecutor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
