package com.civicflow.mapper;

import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.entity.IssueEntity;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public IssueEntity toEntity(
            CreateIssueRequest request
    ) {

        return new IssueEntity(
                request.getTitle(),
                request.getDescription(),
                request.getPriority(),
                IssueStatus.REPORTED
        );
    }

    public IssueResponse toResponse(
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