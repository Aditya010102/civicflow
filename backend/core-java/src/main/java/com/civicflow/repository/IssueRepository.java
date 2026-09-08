package com.civicflow.repository;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository
        extends JpaRepository<IssueEntity, Long> {

    @Query("""
            SELECT i
            FROM IssueEntity i
            WHERE i.status = :status
            """)
    List<IssueEntity> findIssuesByStatus(
            @Param("status") IssueStatus status
    );

    @Query("""
            SELECT i
            FROM IssueEntity i
            WHERE i.priority = :priority
            """)
    List<IssueEntity> findIssuesByPriority(
            @Param("priority") IssuePriority priority
    );

    @Query("""
            SELECT i
            FROM IssueEntity i
            WHERE i.department.name = :departmentName
            """)
    List<IssueEntity> findIssuesByDepartmentName(
            @Param("departmentName") String departmentName
    );

    @Query("""
            SELECT i
            FROM IssueEntity i
            JOIN FETCH i.department
            """)
    List<IssueEntity> findAllWithDepartment();

    @Query("""
            SELECT i
            FROM IssueEntity i
            JOIN FETCH i.department d
            WHERE i.status = :status
            AND d.name = :departmentName
            """)
    List<IssueEntity> findByStatusAndDepartment(
            @Param("status") IssueStatus status,
            @Param("departmentName") String departmentName
    );
}