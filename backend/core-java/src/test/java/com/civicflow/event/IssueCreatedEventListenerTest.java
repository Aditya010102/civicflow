package com.civicflow.event;

import com.civicflow.notification.NotificationCoordinator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class IssueCreatedEventListenerTest {

    @Test
    void shouldDelegateNotification() {

        NotificationCoordinator coordinator =
                Mockito.mock(
                        NotificationCoordinator.class
                );

        IssueCreatedEventListener listener =
                new IssueCreatedEventListener(
                        coordinator
                );

        IssueCreatedEvent event =
                new IssueCreatedEvent(
                        100L,
                        "citizen@example.com",
                        "Issue created"
                );

        listener.handleIssueCreated(event);

        Mockito.verify(coordinator)
                .sendIssueCreatedNotification(
                        "citizen@example.com",
                        "Issue created"
                );
    }
}