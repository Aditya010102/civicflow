package com.civicflow.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@Profile("dev")
public class DevNotificationService
        implements NotificationService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    DevNotificationService.class
            );

    @Async("notificationExecutor")
    @Override
    public CompletableFuture<Boolean> send(
            String recipient,
            String message
    ) {

        logger.info(
                "[DEV NOTIFICATION] thread={}, recipient={}, message={}",
                Thread.currentThread().getName(),
                recipient,
                message
        );

        return CompletableFuture.completedFuture(true);
    }
}