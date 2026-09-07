package com.civicflow.service;

import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.entity.IssueEntity;
import com.civicflow.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(
            IssueRepository issueRepository
    ) {
        this.issueRepository = issueRepository;
    }

    @Transactional
    public IssueResponse createIssue(
            CreateIssueRequest request
    ) {

        IssueEntity issue = new IssueEntity(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                IssueStatus.REPORTED
        );

        IssueEntity saved =
                issueRepository.save(issue);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getAllIssues() {

        return issueRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public IssueResponse getIssueById(
            Long id
    ) {

        IssueEntity issue =
                issueRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Issue not found: " + id
                                )
                        );

        return toResponse(issue);
    }

    @Transactional
    public IssueResponse updateStatus(
            Long id,
            UpdateIssueStatusRequest request
    ) {

        IssueEntity issue =
                issueRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Issue not found: " + id
                                )
                        );

        IssueStatus currentStatus =
                issue.getStatus();

        IssueStatus newStatus =
                request.getStatus();

        if (!currentStatus.canTransitionTo(newStatus)) {

            throw new IllegalStateException(
                    "Invalid status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }

        issue.updateStatus(newStatus);

        IssueEntity saved =
                issueRepository.save(issue);

        return toResponse(saved);
    }

    @Transactional
    public void deleteIssue(Long id) {

        if (!issueRepository.existsById(id)) {

            throw new RuntimeException(
                    "Issue not found: " + id
            );
        }

        issueRepository.deleteById(id);
    }

    private IssueResponse toResponse(
            IssueEntity issue
    ) {

        return new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getPriority(),
                issue.getStatus(),
                issue.getCreatedAt(),
                issue.getUpdatedAt()
        );
    }
}