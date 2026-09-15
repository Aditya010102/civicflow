package com.civicflow.event;

import com.civicflow.notification.NotificationCoordinator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IssueCreatedEventListener {

    private final NotificationCoordinator notificationCoordinator;

    public IssueCreatedEventListener(
            NotificationCoordinator notificationCoordinator
    ) {
        this.notificationCoordinator =
                notificationCoordinator;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleIssueCreated(
            IssueCreatedEvent event
    ) {

        notificationCoordinator.sendIssueCreatedNotification(
                event.issueId(),
                event.recipient(),
                event.message()
        );
    }
}