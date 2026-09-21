package com.civicflow.observability;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class IssueMetrics {

    private final Counter issuesCreated;

    public IssueMetrics(
            MeterRegistry meterRegistry
    ) {
        this.issuesCreated =
                Counter.builder("civicflow.issues.created")
                        .description(
                                "Number of civic issues created"
                        )
                        .register(meterRegistry);
    }

    public void recordIssueCreated() {
        issuesCreated.increment();
    }
}