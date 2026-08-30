package com.civicflow.core.model;

public class Issue {

    private long id;

    private String title;

    private String description;

    private IssuePriority priority;

    private IssueStatus status;

    private static long issueCount = 0;

    public Issue(
            long id,
            String title,
            String description,
            IssuePriority priority,
            IssueStatus status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;

        issueCount++;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public static long getIssueCount() {
        return issueCount;
    }
    @Override
    public String toString() {

        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status=" + status +
                '}';
    }
}