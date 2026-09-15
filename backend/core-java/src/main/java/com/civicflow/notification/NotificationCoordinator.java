package com.civicflow.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationCoordinator {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    NotificationCoordinator.class
            );

    private final NotificationService notificationService;

    public NotificationCoordinator(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    public void sendIssueCreatedNotification(
            String recipient,
            String message
    ) {

        CompletableFuture<Boolean> future =
                notificationService.send(
                        recipient,
                        message
                );

        future.whenComplete(
                (success, exception) -> {

                    if (exception != null) {

                        logger.error(
                                "Issue notification failed: recipient={}",
                                recipient,
                                exception
                        );

                        return;
                    }

                    if (Boolean.TRUE.equals(success)) {

                        logger.info(
                                "Issue notification completed successfully: recipient={}",
                                recipient
                        );

                    } else {

                        logger.warn(
                                "Issue notification was not successful: recipient={}",
                                recipient
                        );
                    }
                }
        );
    }
}