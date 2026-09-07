package com.civicflow.advanced.serialization;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import tools.jackson.databind.ObjectMapper;

public class IssueJsonDemo {

    public static void main(String[] args) throws Exception {

        Issue issue = new Issue(
                101,
                "Broken streetlight",
                "Streetlight is not working",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(issue);

        System.out.println(json);
    }
}