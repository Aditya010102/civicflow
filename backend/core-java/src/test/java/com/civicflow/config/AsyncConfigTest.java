package com.civicflow.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AsyncConfigTest {

    @Autowired
    private TaskExecutor notificationExecutor;

    @Test
    void notificationExecutorShouldExist() {
        assertNotNull(notificationExecutor);
    }
}