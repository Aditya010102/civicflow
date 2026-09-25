package com.civicflow.service;

import com.civicflow.core.event.EventType;
import com.civicflow.core.event.IssueEvent;
import com.civicflow.core.event.IssueEventPayload;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.entity.IssueEntity;
import com.civicflow.exception.InvalidIssueStatusTransitionException;
import com.civicflow.exception.IssueNotFoundException;
import com.civicflow.mapper.IssueMapper;
import com.civicflow.observability.IssueMetrics;
import com.civicflow.repository.IssueRepository;
import com.civicflow.specification.IssueSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class IssueService {

    private static final Logger logger =
            LoggerFactory.getLogger(IssueService.class);

    private static final String ISSUE_EVENTS_TOPIC =
            "civicflow.issue-events";

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final IssueMetrics issueMetrics;
    private final KafkaTemplate<String, IssueEvent> kafkaTemplate;

    public IssueService(
            IssueRepository issueRepository,
            IssueMapper issueMapper,
            IssueMetrics issueMetrics,
            KafkaTemplate<String, IssueEvent> kafkaTemplate
    ) {
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
        this.issueMetrics = issueMetrics;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PreAuthorize("hasAnyRole('CITIZEN', 'STAFF', 'ADMIN')")
    @CacheEvict(
            value = "dashboard-summary",
            key = "'global'"
    )
    @Transactional
    public IssueResponse createIssue(
            CreateIssueRequest request
    ) {

        IssueEntity issue =
                issueMapper.toEntity(request);

        IssueEntity saved =
                issueRepository.save(issue);

        issueMetrics.recordIssueCreated();

        logger.info(
                "Issue created successfully: id={}, priority={}",
                saved.getId(),
                saved.getPriority()
        );

        publishIssueCreatedEvent(saved);

        return issueMapper.toResponse(saved);
    }

    private void publishIssueCreatedEvent(
            IssueEntity issue
    ) {

        IssueEventPayload payload =
                new IssueEventPayload(
                        issue.getId(),
                        issue.getTitle(),
                        issue.getPriority().name()
                );

        IssueEvent event =
                new IssueEvent(
                        UUID.randomUUID(),
                        EventType.ISSUE_CREATED,
                        Instant.now(),
                        issue.getId(),
                        payload
                );

        kafkaTemplate.send(
                ISSUE_EVENTS_TOPIC,
                String.valueOf(issue.getId()),
                event
        ).whenComplete((result, exception) -> {

            if (exception != null) {

                logger.error(
                        "Failed to publish ISSUE_CREATED event: issueId={}",
                        issue.getId(),
                        exception
                );

                return;
            }

            logger.info(
                    "ISSUE_CREATED event published successfully: issueId={}, topic={}, partition={}, offset={}",
                    issue.getId(),
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset()
            );
        });
    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> getAllIssues(
            Pageable pageable
    ) {

        return issueRepository
                .findAll(pageable)
                .map(issueMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> searchIssues(
            IssueStatus status,
            IssuePriority priority,
            Long departmentId,
            String search,
            Pageable pageable
    ) {

        Specification<IssueEntity> specification = null;

        if (status != null) {

            specification =
                    IssueSpecifications.hasStatus(status);
        }

        if (priority != null) {

            specification =
                    specification == null
                            ? IssueSpecifications.hasPriority(priority)
                            : specification.and(
                            IssueSpecifications.hasPriority(priority)
                    );
        }

        if (departmentId != null) {

            specification =
                    specification == null
                            ? IssueSpecifications.belongsToDepartment(
                            departmentId
                    )
                            : specification.and(
                            IssueSpecifications.belongsToDepartment(
                                    departmentId
                            )
                    );
        }

        if (search != null && !search.isBlank()) {

            specification =
                    specification == null
                            ? IssueSpecifications.titleOrDescriptionContains(
                            search
                    )
                            : specification.and(
                            IssueSpecifications.titleOrDescriptionContains(
                                    search
                            )
                    );
        }

        Page<IssueEntity> page =
                specification == null
                        ? issueRepository.findAll(pageable)
                        : issueRepository.findAll(
                        specification,
                        pageable
                );

        return page.map(issueMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public IssueResponse getIssueById(
            Long id
    ) {

        IssueEntity issue =
                issueRepository.findById(id)
                        .orElseThrow(() ->
                                new IssueNotFoundException(id)
                        );

        return issueMapper.toResponse(issue);
    }

    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    @CacheEvict(
            value = "dashboard-summary",
            key = "'global'"
    )
    @Transactional
    public IssueResponse updateStatus(
            Long id,
            UpdateIssueStatusRequest request
    ) {

        IssueEntity issue =
                issueRepository.findById(id)
                        .orElseThrow(() ->
                                new IssueNotFoundException(id)
                        );

        IssueStatus currentStatus =
                issue.getStatus();

        IssueStatus newStatus =
                request.getStatus();

        logger.info(
                "Updating issue status: id={}, from={}, to={}",
                id,
                currentStatus,
                newStatus
        );

        if (!currentStatus.canTransitionTo(newStatus)) {

            logger.warn(
                    "Invalid issue status transition: id={}, from={}, to={}",
                    id,
                    currentStatus,
                    newStatus
            );

            throw new InvalidIssueStatusTransitionException(
                    currentStatus,
                    newStatus
            );
        }

        issue.updateStatus(newStatus);

        IssueEntity saved =
                issueRepository.save(issue);

        return issueMapper.toResponse(saved);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(
            value = "dashboard-summary",
            key = "'global'"
    )
    @Transactional
    public void deleteIssue(Long id) {

        if (!issueRepository.existsById(id)) {

            throw new IssueNotFoundException(id);
        }

        logger.info(
                "Deleting issue: id={}",
                id
        );

        issueRepository.deleteById(id);
    }
}