package com.civicflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor() {

        ThreadPoolTaskExecutor executor =
                new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(100);

        executor.setThreadNamePrefix(
                "civicflow-notification-"
        );

        executor.setTaskDecorator(
                mdcTaskDecorator()
        );

        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);

        executor.initialize();

        return executor;
    }

    @Bean
    public TaskDecorator mdcTaskDecorator() {

        return runnable -> {

            Map<String, String> contextMap =
                    MDC.getCopyOfContextMap();

            return () -> {

                try {

                    if (contextMap != null) {
                        MDC.setContextMap(
                                contextMap
                        );
                    }

                    runnable.run();

                } finally {

                    MDC.clear();
                }
            };
        };
    }
}