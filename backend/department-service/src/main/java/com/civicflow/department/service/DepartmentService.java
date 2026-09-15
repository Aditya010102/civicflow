package com.civicflow.department.service;

import com.civicflow.department.dto.DepartmentResponse;
import com.civicflow.department.entity.DepartmentEntity;
import com.civicflow.department.mapper.DepartmentMapper;
import com.civicflow.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            DepartmentMapper departmentMapper
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository
                .findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(
            Long id
    ) {

        DepartmentEntity department =
                departmentRepository
                        .findById(id)
                        .orElseThrow();

        return departmentMapper.toResponse(
                department
        );
    }
}