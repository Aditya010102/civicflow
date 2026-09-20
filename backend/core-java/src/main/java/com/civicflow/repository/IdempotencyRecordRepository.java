package com.civicflow.repository;

import com.civicflow.entity.IdempotencyRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdempotencyRecordRepository
        extends JpaRepository<IdempotencyRecordEntity, Long> {

    Optional<IdempotencyRecordEntity>
    findByIdempotencyKey(String idempotencyKey);
}