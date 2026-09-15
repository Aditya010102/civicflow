package com.civicflow.department.controller;

import com.civicflow.department.dto.DepartmentResponse;
import com.civicflow.department.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService
    ) {
        this.departmentService =
                departmentService;
    }

    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {

        return departmentService
                .getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartment(
            @PathVariable Long id
    ) {

        return departmentService
                .getDepartmentById(id);
    }
}