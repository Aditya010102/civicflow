package com.civicflow.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ---------------------------------------------------------
    // 1. Validation errors
    // ---------------------------------------------------------

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {

        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Failed",
                        "Request contains invalid fields",
                        request.getRequestURI(),
                        fieldErrors
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    // ---------------------------------------------------------
    // 2. Issue not found
    // ---------------------------------------------------------

    @ExceptionHandler(IssueNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleIssueNotFound(
            IssueNotFoundException exception,
            HttpServletRequest request
    ) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Issue Not Found",
                        exception.getMessage(),
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }


    // ---------------------------------------------------------
    // 3. Invalid issue status transition
    // ---------------------------------------------------------

    @ExceptionHandler(InvalidIssueStatusTransitionException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidStatusTransition(
            InvalidIssueStatusTransitionException exception,
            HttpServletRequest request
    ) {

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        "Invalid Status Transition",
                        exception.getMessage(),
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }


    // ---------------------------------------------------------
    // 4. Unexpected / unknown exceptions
    // ---------------------------------------------------------

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {

        logger.error(
                "Unexpected application error: method={}, path={}",
                request.getMethod(),
                request.getRequestURI(),
                exception
        );

        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "An unexpected error occurred",
                        request.getRequestURI(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyExists(
            EmailAlreadyExistsException exception,
            HttpServletRequest request
    ) {
        ApiErrorResponse response =
                new ApiErrorResponse(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        "Conflict",
                        exception.getMessage(),
                        request.getRequestURI(),
                        Map.of()
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}