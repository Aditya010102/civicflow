package com.civicflow.entity;

import com.civicflow.notification.NotificationStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long issueId;

    @Column(nullable = false, length = 150)
    private String recipient;

    @Column(nullable = false, length = 2000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NotificationStatus status;

    @Column(nullable = false)
    private int attemptCount;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant sentAt;

    private Instant failedAt;

    protected NotificationEntity() {
        // Required by JPA
    }

    public NotificationEntity(
            Long issueId,
            String recipient,
            String message
    ) {
        this.issueId = issueId;
        this.recipient = recipient;
        this.message = message;
        this.status = NotificationStatus.PENDING;
        this.attemptCount = 0;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public Instant getFailedAt() {
        return failedAt;
    }

    public void markSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
    }

    public void markFailed() {
        this.status = NotificationStatus.FAILED;
        this.failedAt = Instant.now();
    }

    public void incrementAttemptCount() {
        this.attemptCount++;
    }
}