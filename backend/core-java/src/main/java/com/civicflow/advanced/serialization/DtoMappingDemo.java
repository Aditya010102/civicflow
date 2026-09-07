package com.civicflow.advanced.serialization;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class DtoMappingDemo {

    public static void main(String[] args) {

        CreateIssueRequest request =
                new CreateIssueRequest(
                        "Water leakage",
                        "Major leakage near the market",
                        IssuePriority.HIGH
                );

        Issue issue = new Issue(
                101,
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                IssueStatus.REPORTED
        );

        IssueResponse response =
                new IssueResponse(
                        issue.getId(),
                        issue.getTitle(),
                        issue.getDescription(),
                        issue.getPriority(),
                        issue.getStatus()
                );

        System.out.println(response.getTitle());
        System.out.println(response.getStatus());
    }
}