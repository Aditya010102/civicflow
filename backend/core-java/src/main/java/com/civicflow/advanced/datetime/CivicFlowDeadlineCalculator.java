package com.civicflow.advanced.datetime;

import com.civicflow.core.model.IssuePriority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CivicFlowDeadlineCalculator {

    public Instant calculateDeadline(
            Instant createdAt,
            IssuePriority priority
    ) {

        return createdAt.plus(
                priority.escalationHours(),
                ChronoUnit.HOURS
        );
    }

    public boolean isOverdue(
            Instant deadline
    ) {

        return Instant.now()
                .isAfter(deadline);
    }
}