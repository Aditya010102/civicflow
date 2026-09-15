package com.civicflow.service;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.DashboardSummaryResponse;
import com.civicflow.repository.IssueRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

    private final IssueRepository issueRepository;

    public DashboardService(
            IssueRepository issueRepository
    ) {
        this.issueRepository = issueRepository;
    }

    @Cacheable(
            value = "dashboard-summary",
            key = "'global'"
    )
    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {

        return new DashboardSummaryResponse(
                issueRepository.count(),
                issueRepository.countIssuesByStatus(
                        IssueStatus.REPORTED
                ),
                issueRepository.countIssuesByStatus(
                        IssueStatus.ACKNOWLEDGED
                ),
                issueRepository.countIssuesByStatus(
                        IssueStatus.ASSIGNED
                ),
                issueRepository.countIssuesByStatus(
                        IssueStatus.IN_PROGRESS
                ),
                issueRepository.countIssuesByStatus(
                        IssueStatus.RESOLVED
                ),
                issueRepository.countIssuesByStatus(
                        IssueStatus.CLOSED
                ),
                issueRepository.countByPriority(
                        IssuePriority.CRITICAL
                )
        );
    }
}