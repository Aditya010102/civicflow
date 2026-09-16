package com.civicflow.repository;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface IssueRepository
        extends JpaRepository<IssueEntity, Long>,
        JpaSpecificationExecutor<IssueEntity> {

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

    List<IssueEntity> findByStatusIn(
            Collection<IssueStatus> statuses
    );

    List<IssueEntity> findByStatusNot(
            IssueStatus status
    );

    List<IssueEntity> findByDepartmentId(
            Long departmentId
    );

    List<IssueEntity> findByDepartmentIdIsNull();

    List<IssueEntity> findByStatusOrderByCreatedAtDesc(
            IssueStatus status
    );

    @Query("""
            SELECT i
            FROM IssueEntity i
            WHERE i.departmentId = :departmentId
            """)
    List<IssueEntity> findIssuesByDepartmentId(
            @Param("departmentId") Long departmentId
    );

    @Query("""
            SELECT i
            FROM IssueEntity i
            WHERE i.status = :status
            AND i.departmentId = :departmentId
            """)
    List<IssueEntity> findByStatusAndDepartmentId(
            @Param("status") IssueStatus status,
            @Param("departmentId") Long departmentId
    );

    @Query("""
            SELECT COUNT(i)
            FROM IssueEntity i
            WHERE i.status = :status
            """)
    long countIssuesByStatus(
            @Param("status") IssueStatus status
    );

    long countByPriority(
            IssuePriority priority
    );
}