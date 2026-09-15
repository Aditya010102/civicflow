package com.civicflow.notification;

import com.civicflow.entity.NotificationEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class NotificationCoordinatorTest {

    @Test
    void shouldSendNotificationSuccessfully() {

        NotificationService notificationService =
                mock(NotificationService.class);

        NotificationPersistenceService
                notificationPersistenceService =
                mock(NotificationPersistenceService.class);

        NotificationEntity notification =
                mock(NotificationEntity.class);

        when(
                notificationPersistenceService.createPending(
                        1L,
                        "citizen@example.com",
                        "Issue created"
                )
        ).thenReturn(notification);

        when(notification.getId())
                .thenReturn(100L);

        when(
                notificationService.send(
                        "citizen@example.com",
                        "Issue created"
                )
        ).thenReturn(
                CompletableFuture.completedFuture(true)
        );

        NotificationCoordinator coordinator =
                new NotificationCoordinator(
                        notificationService,
                        notificationPersistenceService
                );

        coordinator.sendIssueCreatedNotification(
                1L,
                "citizen@example.com",
                "Issue created"
        );

        verify(
                notificationPersistenceService
        ).createPending(
                1L,
                "citizen@example.com",
                "Issue created"
        );

        verify(
                notificationPersistenceService
        ).incrementAttempt(100L);

        verify(
                notificationPersistenceService
        ).markSent(100L);

        verify(
                notificationService
        ).send(
                "citizen@example.com",
                "Issue created"
        );
    }
}