package com.civicflow.core.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class IssueEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(IssueEventConsumer.class);

    @KafkaListener(
            topics = "issue-events",
            groupId = "civicflow-issue-consumer",
            containerFactory = "issueEventKafkaListenerContainerFactory"
    )
    public void consume(IssueEvent event) {

        logger.info(
                "Received CivicFlow event: eventId={}, eventType={}, issueId={}, occurredAt={}",
                event.eventId(),
                event.eventType(),
                event.aggregateId(),
                event.occurredAt()
        );

        logger.info(
                "Issue event payload: {}",
                event.payload()
        );
    }
}