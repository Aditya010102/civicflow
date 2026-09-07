package com.civicflow.core.functional;

import com.civicflow.core.exceptions.IssueNotFoundException;
import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class IssueAnalyticsEngine {

    private final List<Issue> issues;

    public IssueAnalyticsEngine(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Issue> findCriticalIssues() {

        return issues.stream()
                .filter(issue ->
                        issue.getPriority()
                                == IssuePriority.CRITICAL)
                .toList();
    }

    public List<Issue> findOpenCriticalIssues() {

        return issues.stream()
                .filter(issue ->
                        issue.getPriority()
                                == IssuePriority.CRITICAL)
                .filter(issue ->
                        issue.getStatus()
                                != IssueStatus.RESOLVED)
                .toList();
    }

    public Map<IssueStatus, Long> countByStatus() {

        return issues.stream()
                .collect(
                        Collectors.groupingBy(
                                Issue::getStatus,
                                Collectors.counting()
                        )
                );
    }

    public Map<IssuePriority, Long> countByPriority() {

        return issues.stream()
                .collect(
                        Collectors.groupingBy(
                                Issue::getPriority,
                                Collectors.counting()
                        )
                );
    }

    public Optional<Issue> findById(long id) {

        return issues.stream()
                .filter(issue ->
                        issue.getId() == id)
                .findFirst();
    }

    public Issue getRequiredIssue(long id) {

        return findById(id)
                .orElseThrow(
                        () ->
                                new IssueNotFoundException(
                                        "Issue not found: " + id
                                )
                );
    }

    public long countResolvedIssues() {

        return issues.stream()
                .filter(issue ->
                        issue.getStatus()
                                == IssueStatus.RESOLVED)
                .count();
    }
}