package com.civicflow.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class ApplicationLifecycleLogger {

    @PostConstruct
    public void initialize() {
        System.out.println(
                "CivicFlow lifecycle bean initialized."
        );
    }

    @PreDestroy
    public void cleanup() {
        System.out.println(
                "CivicFlow lifecycle bean destroyed."
        );
    }
}