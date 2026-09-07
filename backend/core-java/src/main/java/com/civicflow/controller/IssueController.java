package com.civicflow.controller;

import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<IssueResponse> getAllIssues() {

        return issueService.getAllIssues();
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