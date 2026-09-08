package com.civicflow.repository;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface IssueRepository
        extends JpaRepository<IssueEntity, Long> {

    // ---------- Derived Queries ----------

    List<IssueEntity> findByStatus(
            IssueStatus status
    );

    List<IssueEntity> findByPriority(
            IssuePriority priority
    );

    List<IssueEntity> findByStatusAndPriority(
            IssueStatus status,
            IssuePriority priority
    );

    List<IssueEntity> findByTitleContainingIgnoreCase(
            String keyword
    );

    List<IssueEntity> findByDepartmentName(
            String departmentName
    );

    List<IssueEntity> findByStatusIn(
            Collection<IssueStatus> statuses
    );

    List<IssueEntity> findByStatusNot(
            IssueStatus status
    );

    List<IssueEntity> findByDepartmentIsNull();

    List<IssueEntity> findByStatusOrderByCreatedAtDesc(
            IssueStatus status
    );


    // ---------- JPQL Queries ----------

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

    @Query("""
            SELECT COUNT(i)
            FROM IssueEntity i
            WHERE i.status = :status
            """)
    long countIssuesByStatus(
            @Param("status") IssueStatus status
    );
}