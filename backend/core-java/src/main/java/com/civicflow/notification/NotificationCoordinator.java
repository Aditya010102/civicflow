package com.civicflow.notification;

import com.civicflow.entity.NotificationEntity;
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

    private final NotificationPersistenceService
            notificationPersistenceService;

    public NotificationCoordinator(
            NotificationService notificationService,
            NotificationPersistenceService
                    notificationPersistenceService
    ) {
        this.notificationService =
                notificationService;

        this.notificationPersistenceService =
                notificationPersistenceService;
    }

    public void sendIssueCreatedNotification(
            Long issueId,
            String recipient,
            String message
    ) {

        NotificationEntity notification =
                notificationPersistenceService.createPending(
                        issueId,
                        recipient,
                        message
                );

        notificationPersistenceService.incrementAttempt(
                notification.getId()
        );

        CompletableFuture<Boolean> notificationFuture =
                notificationService.send(
                        recipient,
                        message
                );

        notificationFuture.whenComplete(
                (success, exception) -> {

                    if (exception != null) {

                        notificationPersistenceService
                                .markFailed(
                                        notification.getId()
                                );

                        logger.error(
                                "Notification failed: notificationId={}, issueId={}",
                                notification.getId(),
                                issueId,
                                exception
                        );

                        return;
                    }

                    if (Boolean.TRUE.equals(success)) {

                        notificationPersistenceService
                                .markSent(
                                        notification.getId()
                                );

                        logger.info(
                                "Notification sent: notificationId={}, issueId={}",
                                notification.getId(),
                                issueId
                        );

                    } else {

                        notificationPersistenceService
                                .markFailed(
                                        notification.getId()
                                );

                        logger.warn(
                                "Notification unsuccessful: notificationId={}, issueId={}",
                                notification.getId(),
                                issueId
                        );
                    }
                }
        );
    }
}