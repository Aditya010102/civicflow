package com.civicflow.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IssueEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(IssueEventConsumer.class);

    private final ProcessedEventStore processedEventStore;

    public IssueEventConsumer(
            ProcessedEventStore processedEventStore
    ) {
        this.processedEventStore =
                processedEventStore;
    }

    @KafkaListener(
            topics = "issue-events",
            groupId = "civicflow-issue-consumer",
            containerFactory = "issueEventKafkaListenerContainerFactory"
    )
    public void consume(
            IssueEvent event
    ) {

        logger.info(
                "Received CivicFlow event: eventId={}, eventType={}, issueId={}, occurredAt={}",
                event.eventId(),
                event.eventType(),
                event.aggregateId(),
                event.occurredAt()
        );

        if (
                processedEventStore.hasBeenProcessed(
                        event.eventId()
                )
        ) {

            logger.warn(
                    "Duplicate event ignored: eventId={}",
                    event.eventId()
            );

            return;
        }

        switch (event.eventType()) {

            case ISSUE_CREATED ->
                    handleIssueCreated(event);

            default ->
                    logger.warn(
                            "Unhandled CivicFlow event type: {}",
                            event.eventType()
                    );
        }

        processedEventStore.markAsProcessed(
                event.eventId()
        );

        logger.info(
                "Event processed successfully: eventId={}",
                event.eventId()
        );
    }

    private void handleIssueCreated(
            IssueEvent event
    ) {

        logger.info(
                "Processing ISSUE_CREATED event: issueId={}, payload={}",
                event.aggregateId(),
                event.payload()
        );
    }
}