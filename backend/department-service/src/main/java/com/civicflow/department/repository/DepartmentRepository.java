package com.civicflow.department.repository;

import com.civicflow.department.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<DepartmentEntity, Long> {
}