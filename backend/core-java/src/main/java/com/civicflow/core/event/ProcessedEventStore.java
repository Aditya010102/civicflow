package com.civicflow.core.event;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProcessedEventStore {

    private final Set<UUID> processedEventIds =
            ConcurrentHashMap.newKeySet();

    public boolean hasBeenProcessed(
            UUID eventId
    ) {
        return processedEventIds.contains(eventId);
    }

    public boolean markAsProcessed(
            UUID eventId
    ) {
        return processedEventIds.add(eventId);
    }
}