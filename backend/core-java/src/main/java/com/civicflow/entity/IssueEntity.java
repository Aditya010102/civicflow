package com.civicflow.entity;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import jakarta.persistence.Index;

@Entity
@Table(
        name = "issues",
        indexes = {
                @Index(name = "idx_issues_status", columnList = "status"),
                @Index(name = "idx_issues_priority", columnList = "priority"),
                @Index(name = "idx_issues_department_id", columnList = "department_id"),
                @Index(name = "idx_issues_created_at", columnList = "created_at")
        }
)
@EntityListeners(AuditingEntityListener.class)
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssuePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus status;

    @Version
    @Column(nullable = false)
    private Long version;

    @Column(name = "department_id")
    private Long departmentId;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    protected IssueEntity() {
        // Required by JPA
    }

    public IssueEntity(
            String title,
            String description,
            IssuePriority priority,
            IssueStatus status
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Long getVersion() {
        return version;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void assignDepartment(Long departmentId) {
        this.departmentId = departmentId;
    }

    public void updateStatus(IssueStatus status) {
        this.status = status;
    }
}