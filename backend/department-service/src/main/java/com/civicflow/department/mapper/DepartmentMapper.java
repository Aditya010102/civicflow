package com.civicflow.department.mapper;

import com.civicflow.department.dto.DepartmentResponse;
import com.civicflow.department.entity.DepartmentEntity;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public DepartmentResponse toResponse(
            DepartmentEntity department
    ) {
        return new DepartmentResponse(
                department.getId(),
                department.getName()
        );
    }
}