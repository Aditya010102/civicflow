package com.civicflow.service;

import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.entity.IssueEntity;
import com.civicflow.exception.InvalidIssueStatusTransitionException;
import com.civicflow.mapper.IssueMapper;
import com.civicflow.repository.IssueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.civicflow.exception.IssueNotFoundException;

import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;

    public IssueService(
            IssueRepository issueRepository,
            IssueMapper issueMapper
    ) {
        this.issueRepository = issueRepository;
        this.issueMapper = issueMapper;
    }

    @Transactional
    public IssueResponse createIssue(
            CreateIssueRequest request
    ) {

        IssueEntity issue =
                issueMapper.toEntity(request);

        IssueEntity saved =
                issueRepository.save(issue);

        return issueMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getAllIssues() {

        return issueRepository.findAll()
                .stream()
                .map(issueMapper::toResponse)
                .toList();
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

        if (!currentStatus.canTransitionTo(newStatus)) {

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

    @Transactional
    public void deleteIssue(Long id) {

        if (!issueRepository.existsById(id)) {

            throw new IssueNotFoundException(id);
        }

        issueRepository.deleteById(id);
    }
}