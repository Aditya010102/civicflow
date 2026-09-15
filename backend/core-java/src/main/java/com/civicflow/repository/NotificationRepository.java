package com.civicflow.repository;

import com.civicflow.entity.NotificationEntity;
import com.civicflow.notification.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByStatus(
            NotificationStatus status
    );

    List<NotificationEntity> findByIssueId(
            Long issueId
    );
}