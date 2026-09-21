package com.civicflow.controller;

import com.civicflow.dto.DepartmentResponse;
import com.civicflow.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@Tag(
        name = "Departments",
        description = "Operations for managing CivicFlow departments"
)
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService
    ) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(
            summary = "Get all departments",
            description =
                    "Retrieves all departments available in CivicFlow."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Departments retrieved successfully"
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
    public List<DepartmentResponse> getAllDepartments() {

        return departmentService.getAllDepartments();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a department",
            description =
                    "Deletes a department. Requires ADMIN role."
    )
    @SecurityRequirement(
            name = "bearerAuth"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Department deleted successfully"
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
                    description = "Department not found"
            )
    })
    public void deleteDepartment(
            @PathVariable Long id
    ) {
        departmentService.deleteDepartment(id);
    }
}