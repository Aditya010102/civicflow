package com.civicflow.core.model;

public class Issue {

    private long id;

    private String title;

    private String description;

    private IssuePriority priority;

    public Issue(
            long id,
            String title,
            String description,
            IssuePriority priority
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
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

    @Override
    public String toString() {

        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}