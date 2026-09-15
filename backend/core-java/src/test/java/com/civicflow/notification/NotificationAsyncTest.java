package com.civicflow.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("dev")
class NotificationAsyncTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void notificationShouldCompleteSuccessfully()
            throws Exception {

        CompletableFuture<Boolean> result =
                notificationService.send(
                        "citizen@example.com",
                        "Your issue was created."
                );

        assertTrue(
                result.get(
                        5,
                        TimeUnit.SECONDS
                )
        );
    }
}