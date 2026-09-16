package com.civicflow.department.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            DepartmentNotFoundException.class
    )
    public ResponseEntity<Map<String, String>>
    handleDepartmentNotFound(
            DepartmentNotFoundException exception
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of(
                                "error",
                                "Department Not Found",
                                "message",
                                exception.getMessage()
                        )
                );
    }
}