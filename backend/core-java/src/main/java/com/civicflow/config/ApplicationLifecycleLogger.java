package com.civicflow.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLifecycleLogger {

    private static final Logger logger =
            LoggerFactory.getLogger(ApplicationLifecycleLogger.class);

    @PostConstruct
    public void initialize() {

        logger.info(
                "CivicFlow lifecycle bean initialized."
        );
    }

    @PreDestroy
    public void cleanup() {

        logger.info(
                "CivicFlow lifecycle bean destroyed."
        );
    }
}