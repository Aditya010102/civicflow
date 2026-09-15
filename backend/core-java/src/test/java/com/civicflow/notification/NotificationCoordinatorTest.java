package com.civicflow.notification;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

class NotificationCoordinatorTest {

    @Test
    void shouldHandleNotificationFailure() {

        NotificationService notificationService =
                Mockito.mock(NotificationService.class);

        Mockito.when(
                notificationService.send(
                        Mockito.anyString(),
                        Mockito.anyString()
                )
        ).thenReturn(
                CompletableFuture.failedFuture(
                        new RuntimeException(
                                "Notification provider unavailable"
                        )
                )
        );

        NotificationCoordinator coordinator =
                new NotificationCoordinator(
                        notificationService
                );

        coordinator.sendIssueCreatedNotification(
                "citizen@example.com",
                "Issue created"
        );
    }
}