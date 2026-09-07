package com.civicflow.repository;

import com.civicflow.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository
        extends JpaRepository<IssueEntity, Long> {
}