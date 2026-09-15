package com.civicflow.event;

import com.civicflow.notification.NotificationCoordinator;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class IssueCreatedEventListenerTest {

    @Test
    void shouldDelegateIssueCreatedEventToCoordinator() {

        NotificationCoordinator notificationCoordinator =
                mock(NotificationCoordinator.class);

        IssueCreatedEventListener listener =
                new IssueCreatedEventListener(
                        notificationCoordinator
                );

        IssueCreatedEvent event =
                new IssueCreatedEvent(
                        1L,
                        "citizen@example.com",
                        "Your CivicFlow issue has been created."
                );

        listener.handleIssueCreated(event);

        verify(
                notificationCoordinator
        ).sendIssueCreatedNotification(
                1L,
                "citizen@example.com",
                "Your CivicFlow issue has been created."
        );
    }
}