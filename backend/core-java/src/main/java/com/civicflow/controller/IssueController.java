package com.civicflow.controller;

import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(
            IssueService issueService
    ) {
        this.issueService = issueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponse createIssue(
            @Valid @RequestBody CreateIssueRequest request
    ) {

        return issueService.createIssue(request);
    }

    @GetMapping
    public Page<IssueResponse> getAllIssues(
            @RequestParam(required = false)
            IssueStatus status,

            @RequestParam(required = false)
            IssuePriority priority,

            @RequestParam(required = false)
            Long departmentId,

            @RequestParam(required = false)
            String search,

            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {

        return issueService.searchIssues(
                status,
                priority,
                departmentId,
                search,
                pageable
        );
    }

    @GetMapping("/{id}")
    public IssueResponse getIssue(
            @PathVariable Long id
    ) {

        return issueService.getIssueById(id);
    }

    @PutMapping("/{id}/status")
    public IssueResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody
            UpdateIssueStatusRequest request
    ) {

        return issueService.updateStatus(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(
            @PathVariable Long id
    ) {

        issueService.deleteIssue(id);
    }
}