package com.civicflow.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "processed_events",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_processed_event_event_id",
                        columnNames = "event_id"
                )
        }
)
public class ProcessedEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "event_id",
            nullable = false
    )
    private UUID eventId;

    @Column(
            name = "event_type",
            nullable = false
    )
    private String eventType;

    @Column(
            name = "processed_at",
            nullable = false
    )
    private Instant processedAt;

    protected ProcessedEventEntity() {
    }

    public ProcessedEventEntity(
            UUID eventId,
            String eventType,
            Instant processedAt
    ) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.processedAt = processedAt;
    }

    public Long getId() {
        return id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}