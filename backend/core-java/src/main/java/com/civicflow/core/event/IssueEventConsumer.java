package com.civicflow.core.event;

import com.civicflow.entity.ProcessedEventEntity;
import com.civicflow.repository.ProcessedEventRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class IssueEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(IssueEventConsumer.class);

    private final ProcessedEventRepository processedEventRepository;

    public IssueEventConsumer(
            ProcessedEventRepository processedEventRepository
    ) {
        this.processedEventRepository =
                processedEventRepository;
    }

    @Transactional
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
                processedEventRepository.existsByEventId(
                        event.eventId()
                )
        ) {

            logger.warn(
                    "Duplicate event ignored: eventId={}",
                    event.eventId()
            );

            return;
        }

        processEvent(event);

        try {

            ProcessedEventEntity processedEvent =
                    new ProcessedEventEntity(
                            event.eventId(),
                            event.eventType().name(),
                            Instant.now()
                    );

            processedEventRepository.save(
                    processedEvent
            );

        } catch (DataIntegrityViolationException exception) {

            logger.warn(
                    "Event was already processed concurrently: eventId={}",
                    event.eventId()
            );

            return;
        }

        logger.info(
                "Event processed successfully: eventId={}",
                event.eventId()
        );
    }

    private void processEvent(
            IssueEvent event
    ) {

        switch (event.eventType()) {

            case ISSUE_CREATED ->
                    handleIssueCreated(event);

            default ->
                    logger.warn(
                            "Unhandled CivicFlow event type: {}",
                            event.eventType()
                    );
        }
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