package com.civicflow.mapper;

import com.civicflow.dto.DepartmentResponse;
import com.civicflow.entity.DepartmentEntity;
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