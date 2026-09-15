package com.civicflow.dto;

public class DashboardSummaryResponse {

    private long totalIssues;
    private long reportedIssues;
    private long acknowledgedIssues;
    private long assignedIssues;
    private long inProgressIssues;
    private long resolvedIssues;
    private long closedIssues;
    private long criticalIssues;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(
            long totalIssues,
            long reportedIssues,
            long acknowledgedIssues,
            long assignedIssues,
            long inProgressIssues,
            long resolvedIssues,
            long closedIssues,
            long criticalIssues
    ) {
        this.totalIssues = totalIssues;
        this.reportedIssues = reportedIssues;
        this.acknowledgedIssues = acknowledgedIssues;
        this.assignedIssues = assignedIssues;
        this.inProgressIssues = inProgressIssues;
        this.resolvedIssues = resolvedIssues;
        this.closedIssues = closedIssues;
        this.criticalIssues = criticalIssues;
    }

    public long getTotalIssues() {
        return totalIssues;
    }

    public long getReportedIssues() {
        return reportedIssues;
    }

    public long getAcknowledgedIssues() {
        return acknowledgedIssues;
    }

    public long getAssignedIssues() {
        return assignedIssues;
    }

    public long getInProgressIssues() {
        return inProgressIssues;
    }

    public long getResolvedIssues() {
        return resolvedIssues;
    }

    public long getClosedIssues() {
        return closedIssues;
    }

    public long getCriticalIssues() {
        return criticalIssues;
    }
}