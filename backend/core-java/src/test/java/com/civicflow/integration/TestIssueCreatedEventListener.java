package com.civicflow.integration;

import com.civicflow.event.IssueCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class TestIssueCreatedEventListener {

    private final AtomicReference<IssueCreatedEvent>
            latestEvent =
            new AtomicReference<>();

    @EventListener
    public void handle(IssueCreatedEvent event) {
        latestEvent.set(event);
    }

    public IssueCreatedEvent getLatestEvent() {
        return latestEvent.get();
    }
}