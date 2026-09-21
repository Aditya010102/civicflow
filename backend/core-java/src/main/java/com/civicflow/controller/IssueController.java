package com.civicflow.controller;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/issues")
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
            description =
                    "Creates a new civic issue with the given "
                            + "title, description and priority."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Issue created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid issue data"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Insufficient permissions"
            )
    })
    public IssueResponse createIssue(
            @Valid @RequestBody CreateIssueRequest request
    ) {
        return issueService.createIssue(request);
    }

    @GetMapping
    @Operation(
            summary = "Search and retrieve issues",
            description =
                    "Retrieves civic issues with optional filtering, "
                            + "pagination and sorting."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issues retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Insufficient permissions"
            )
    })
    public Page<IssueResponse> getAllIssues(
            @Parameter(
                    description = "Filter issues by status",
                    example = "REPORTED"
            )
            @RequestParam(required = false)
            IssueStatus status,

            @Parameter(
                    description = "Filter issues by priority",
                    example = "HIGH"
            )
            @RequestParam(required = false)
            IssuePriority priority,

            @Parameter(
                    description = "Filter issues by department ID",
                    example = "3"
            )
            @RequestParam(required = false)
            Long departmentId,

            @Parameter(
                    description =
                            "Search title and description using "
                                    + "a case-insensitive text search",
                    example = "streetlight"
            )
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
            description =
                    "Retrieves a single civic issue using its ID."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Insufficient permissions"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Issue not found"
            )
    })
    public IssueResponse getIssue(
            @Parameter(
                    description = "Unique issue identifier",
                    example = "15"
            )
            @PathVariable Long id
    ) {
        return issueService.getIssueById(id);
    }

    @PutMapping("/{id}/status")
    @Operation(
            summary = "Update issue status",
            description =
                    "Moves an issue to the requested valid "
                            + "workflow status. Requires STAFF "
                            + "or ADMIN role."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Issue status updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description =
                            "STAFF or ADMIN role required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Issue not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description =
                            "Invalid issue status transition"
            )
    })
    public IssueResponse updateStatus(
            @Parameter(
                    description = "Unique issue identifier",
                    example = "15"
            )
            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateIssueStatusRequest request
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
            description =
                    "Deletes an existing civic issue. "
                            + "Requires ADMIN role."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Issue deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "ADMIN role required"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Issue not found"
            )
    })
    public void deleteIssue(
            @Parameter(
                    description = "Unique issue identifier",
                    example = "15"
            )
            @PathVariable Long id
    ) {
        issueService.deleteIssue(id);
    }
}