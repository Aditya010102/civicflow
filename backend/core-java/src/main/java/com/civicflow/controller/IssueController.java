package com.civicflow.controller;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@Tag(
        name = "Issues",
        description = "Operations for managing civic issues"
)
public class IssueController {

    private final IssueService issueService;

    public IssueController(
            IssueService issueService
    ) {
        this.issueService = issueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Create a new issue",
            description = "Creates a new civic issue with the given title, description and priority."
    )
    public IssueResponse createIssue(
            @Valid @RequestBody CreateIssueRequest request
    ) {

        return issueService.createIssue(request);
    }

    @GetMapping
    @Operation(
            summary = "Search and retrieve issues",
            description = "Retrieves civic issues with optional filtering, pagination and sorting."
    )
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
    @Operation(
            summary = "Get issue by ID",
            description = "Retrieves a single civic issue using its ID."
    )
    public IssueResponse getIssue(
            @PathVariable Long id
    ) {

        return issueService.getIssueById(id);
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update issue status",
            description = "Moves an issue to the requested valid workflow status."
    )
    public IssueResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIssueStatusRequest request
    ) {

        return issueService.updateStatus(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete an issue",
            description = "Deletes an existing civic issue."
    )
    public void deleteIssue(
            @PathVariable Long id
    ) {

        issueService.deleteIssue(id);
    }
}