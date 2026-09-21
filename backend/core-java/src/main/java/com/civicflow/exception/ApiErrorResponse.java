package com.civicflow.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(
        description = "Standard CivicFlow API error response"
)
public class ApiErrorResponse {

    @Schema(
            description = "Time when the error occurred",
            example = "2026-09-22T05:00:00Z"
    )
    private Instant timestamp;

    @Schema(
            description = "HTTP status code",
            example = "404"
    )
    private int status;

    @Schema(
            description = "Stable machine-readable CivicFlow error code",
            example = "ISSUE_NOT_FOUND"
    )
    private String code;

    @Schema(
            description = "Human-readable HTTP error category",
            example = "Not Found"
    )
    private String error;

    @Schema(
            description = "Human-readable explanation of the error",
            example = "Issue not found with id: 15"
    )
    private String message;

    @Schema(
            description = "Request path that produced the error",
            example = "/api/v1/issues/15"
    )
    private String path;

    @Schema(
            description = "Correlation ID used to trace the request in application logs",
            example = "8f7c2a1e-1234-4567-8901-abcdef123456"
    )
    private String correlationId;

    @Schema(
            description = "Validation errors grouped by request field"
    )
    private Map<String, String> fieldErrors;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(
            Instant timestamp,
            int status,
            String code,
            String error,
            String message,
            String path,
            String correlationId,
            Map<String, String> fieldErrors
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.error = error;
        this.message = message;
        this.path = path;
        this.correlationId = correlationId;
        this.fieldErrors = fieldErrors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}