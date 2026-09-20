package com.civicflow.service;

import com.civicflow.entity.IdempotencyRecordEntity;
import com.civicflow.repository.IdempotencyRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class IdempotencyService {

    private final IdempotencyRecordRepository repository;

    public IdempotencyService(
            IdempotencyRecordRepository repository
    ) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<IdempotencyRecordEntity> findByKey(
            String key
    ) {
        return repository.findByIdempotencyKey(key);
    }

    @Transactional
    public IdempotencyRecordEntity save(
            String key,
            String requestHash,
            int responseStatus,
            String responseBody
    ) {
        return repository.save(
                new IdempotencyRecordEntity(
                        key,
                        requestHash,
                        responseStatus,
                        responseBody,
                        Instant.now()
                )
        );
    }
}