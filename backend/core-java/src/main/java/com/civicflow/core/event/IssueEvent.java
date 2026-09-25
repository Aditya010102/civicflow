package com.civicflow.core.event;

import java.time.Instant;
import java.util.UUID;

public record IssueEvent(
        UUID eventId,
        EventType eventType,
        Instant occurredAt,
        Long aggregateId,
        IssueEventPayload payload
) {
}