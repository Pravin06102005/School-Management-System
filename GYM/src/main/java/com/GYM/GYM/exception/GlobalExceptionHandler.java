package com.GYM.GYM.exception;


import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Handle Resource Not Found (custom)
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ConfigDataResourceNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Handle validation errors (e.g., @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation Failed");
        response.put("details", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle all other exceptions (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Server Error");
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
    }



}
