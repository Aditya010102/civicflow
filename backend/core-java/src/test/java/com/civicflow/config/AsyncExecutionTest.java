package com.civicflow.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
class AsyncExecutionTest {

    @Autowired
    private AsyncTestService asyncTestService;

    @Test
    void shouldExecuteOnNotificationExecutor()
            throws Exception {

        String threadName =
                asyncTestService
                        .getThreadName()
                        .get(5, TimeUnit.SECONDS);

        assertTrue(
                threadName.startsWith(
                        "civicflow-notification-"
                )
        );
    }
}