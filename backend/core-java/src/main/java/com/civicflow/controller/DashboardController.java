package com.civicflow.controller;

import com.civicflow.dto.DashboardSummaryResponse;
import com.civicflow.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(
        name = "Dashboard",
        description = "CivicFlow dashboard and reporting operations"
)
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService
    ) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    @Operation(
            summary = "Get dashboard summary",
            description =
                    "Retrieves aggregated statistics about "
                            + "CivicFlow issues."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard summary retrieved successfully"
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
    public DashboardSummaryResponse getSummary() {
        return dashboardService.getSummary();
    }
}