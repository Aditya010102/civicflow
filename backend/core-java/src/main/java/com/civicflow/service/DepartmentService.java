package com.civicflow.service;

import com.civicflow.dto.DepartmentResponse;
import com.civicflow.mapper.DepartmentMapper;
import com.civicflow.repository.DepartmentRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.access.prepost.PreAuthorize;

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
    @Cacheable(
            value = "departments",
            key = "'all'"
    )
    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(
            value = "departments",
            allEntries = true
    )
    @Transactional
    public void deleteDepartment(Long id) {

        departmentRepository.deleteById(id);
    }
}