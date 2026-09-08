package com.civicflow.controller;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.dto.IssueResponse;
import com.civicflow.dto.UpdateIssueStatusRequest;
import com.civicflow.exception.InvalidIssueStatusTransitionException;
import com.civicflow.exception.IssueNotFoundException;
import com.civicflow.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IssueService issueService;

    @Test
    void shouldCreateIssue() throws Exception {

        IssueResponse response =
                new IssueResponse(
                        1L,
                        "Water leakage",
                        "Water pipe is leaking near the main road",
                        IssuePriority.HIGH,
                        IssueStatus.REPORTED,
                        Instant.now(),
                        null
                );

        when(issueService.createIssue(any()))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/issues")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "Water leakage",
                                            "description": "Water pipe is leaking near the main road",
                                            "priority": "HIGH"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title")
                        .value("Water leakage"))
                .andExpect(jsonPath("$.priority")
                        .value("HIGH"))
                .andExpect(jsonPath("$.status")
                        .value("REPORTED"));
    }

    @Test
    void shouldRejectInvalidCreateIssueRequest()
            throws Exception {

        mockMvc.perform(
                        post("/api/issues")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "",
                                            "description": "",
                                            "priority": null
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenIssueDoesNotExist()
            throws Exception {

        when(issueService.getIssueById(999L))
                .thenThrow(
                        new IssueNotFoundException(999L)
                );

        mockMvc.perform(
                        get("/api/issues/999")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPaginatedIssues()
            throws Exception {

        IssueResponse response =
                new IssueResponse(
                        1L,
                        "Broken streetlight",
                        "Streetlight is not working",
                        IssuePriority.MEDIUM,
                        IssueStatus.REPORTED,
                        Instant.now(),
                        null
                );

        PageImpl<IssueResponse> page =
                new PageImpl<>(
                        List.of(response),
                        PageRequest.of(0, 20),
                        1
                );

        when(issueService.searchIssues(
                eq(IssueStatus.REPORTED),
                eq(IssuePriority.MEDIUM),
                isNull(),
                isNull(),
                any(Pageable.class)
        )).thenReturn(page);

        mockMvc.perform(
                        get("/api/issues")
                                .param("status", "REPORTED")
                                .param("priority", "MEDIUM")
                                .param("page", "0")
                                .param("size", "20")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id")
                        .value(1))
                .andExpect(jsonPath("$.content[0].title")
                        .value("Broken streetlight"))
                .andExpect(jsonPath("$.content[0].priority")
                        .value("MEDIUM"))
                .andExpect(jsonPath("$.content[0].status")
                        .value("REPORTED"))
                .andExpect(jsonPath("$.totalElements")
                        .value(1))
                .andExpect(jsonPath("$.totalPages")
                        .value(1));
    }

    @Test
    void shouldUpdateIssueStatus()
            throws Exception {

        IssueResponse response =
                new IssueResponse(
                        1L,
                        "Broken streetlight",
                        "Streetlight is not working",
                        IssuePriority.MEDIUM,
                        IssueStatus.ACKNOWLEDGED,
                        Instant.now(),
                        Instant.now()
                );

        when(issueService.updateStatus(
                eq(1L),
                any(UpdateIssueStatusRequest.class)
        )).thenReturn(response);

        mockMvc.perform(
                        put("/api/issues/1/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "status": "ACKNOWLEDGED"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(1))
                .andExpect(jsonPath("$.status")
                        .value("ACKNOWLEDGED"));
    }

    @Test
    void shouldRejectInvalidStatusTransition()
            throws Exception {

        when(issueService.updateStatus(
                eq(1L),
                any(UpdateIssueStatusRequest.class)
        )).thenThrow(
                new InvalidIssueStatusTransitionException(
                        IssueStatus.REPORTED,
                        IssueStatus.RESOLVED
                )
        );

        mockMvc.perform(
                        put("/api/issues/1/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "status": "RESOLVED"
                                        }
                                        """)
                )
                .andExpect(status().isConflict());
    }
}