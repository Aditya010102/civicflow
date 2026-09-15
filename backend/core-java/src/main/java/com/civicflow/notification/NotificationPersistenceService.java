package com.civicflow.notification;

import com.civicflow.entity.NotificationEntity;
import com.civicflow.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationPersistenceService {

    private final NotificationRepository notificationRepository;

    public NotificationPersistenceService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository =
                notificationRepository;
    }

    @Transactional
    public NotificationEntity createPending(
            Long issueId,
            String recipient,
            String message
    ) {

        return notificationRepository.save(
                new NotificationEntity(
                        issueId,
                        recipient,
                        message
                )
        );
    }

    @Transactional
    public void markSent(Long notificationId) {

        NotificationEntity notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow();

        notification.markSent();
    }

    @Transactional
    public void markFailed(Long notificationId) {

        NotificationEntity notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow();

        notification.markFailed();
    }

    @Transactional
    public void incrementAttempt(Long notificationId) {

        NotificationEntity notification =
                notificationRepository
                        .findById(notificationId)
                        .orElseThrow();

        notification.incrementAttemptCount();
    }
}