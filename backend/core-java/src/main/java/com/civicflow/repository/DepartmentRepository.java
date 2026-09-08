package com.civicflow.repository;

import com.civicflow.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<DepartmentEntity, Long> {
}