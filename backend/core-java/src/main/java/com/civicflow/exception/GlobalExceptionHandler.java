package com.civicflow.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.orm.ObjectOptimisticLockingFailureException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    GlobalExceptionHandler.class
            );

    private String getCorrelationId() {
        return MDC.get("correlationId");
    }

    // ---------------------------------------------------------
    // 1. Validation errors
    // ---------------------------------------------------------

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        Map<String, String> fieldErrors =
                new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        String correlationId =
                getCorrelationId();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        ApiErrorCode.VALIDATION_FAILED.name(),
                        "Bad Request",
                        "Request contains invalid fields",
                        request.getRequestURI(),
                        correlationId,
                        fieldErrors
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ---------------------------------------------------------
    // 2. Issue not found
    // ---------------------------------------------------------

    @ExceptionHandler(
            IssueNotFoundException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleIssueNotFound(
            IssueNotFoundException exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ApiErrorCode.ISSUE_NOT_FOUND.name(),
                        "Not Found",
                        exception.getMessage(),
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ---------------------------------------------------------
    // 3. Invalid issue status transition
    // ---------------------------------------------------------

    @ExceptionHandler(
            InvalidIssueStatusTransitionException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleInvalidStatusTransition(
            InvalidIssueStatusTransitionException exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        ApiErrorCode
                                .INVALID_ISSUE_STATUS_TRANSITION
                                .name(),
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ---------------------------------------------------------
    // 4. Email already exists
    // ---------------------------------------------------------

    @ExceptionHandler(
            EmailAlreadyExistsException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleEmailAlreadyExists(
            EmailAlreadyExistsException exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        ApiErrorCode.EMAIL_ALREADY_EXISTS.name(),
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ---------------------------------------------------------
    // 5. Idempotency key conflict
    // ---------------------------------------------------------

    @ExceptionHandler(
            IdempotencyKeyConflictException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleIdempotencyKeyConflict(
            IdempotencyKeyConflictException exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        ApiErrorCode
                                .IDEMPOTENCY_KEY_CONFLICT
                                .name(),
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ---------------------------------------------------------
    // 6. Optimistic locking conflict
    // ---------------------------------------------------------

    @ExceptionHandler(
            ObjectOptimisticLockingFailureException.class
    )
    public ResponseEntity<ApiErrorResponse>
    handleOptimisticLocking(
            ObjectOptimisticLockingFailureException exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        logger.warn(
                "Optimistic locking conflict: method={}, path={}, correlationId={}",
                request.getMethod(),
                request.getRequestURI(),
                correlationId
        );

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        ApiErrorCode
                                .ISSUE_MODIFIED_CONCURRENTLY
                                .name(),
                        "Conflict",
                        "The issue was modified by another user. "
                                + "Please refresh and try again.",
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ---------------------------------------------------------
    // 7. Unexpected / unknown exceptions
    // ---------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse>
    handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {

        String correlationId =
                getCorrelationId();

        logger.error(
                "Unexpected application error: method={}, path={}, correlationId={}",
                request.getMethod(),
                request.getRequestURI(),
                correlationId,
                exception
        );

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ApiErrorCode
                                .INTERNAL_SERVER_ERROR
                                .name(),
                        "Internal Server Error",
                        "An unexpected error occurred",
                        request.getRequestURI(),
                        correlationId,
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}