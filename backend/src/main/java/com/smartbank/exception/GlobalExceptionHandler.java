package com.smartbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler - Centralized exception handling for the application
 * 
 * WHY THIS FILE IS NEEDED:
 * Global exception handling provides a centralized way to handle exceptions across
 * the entire application. Instead of handling exceptions in each controller,
 * we handle them in one place using @ControllerAdvice.
 * 
 * WHAT THE CODE DOES:
 * - @RestControllerAdvice: Marks this as a global exception handler
 * - @ExceptionHandler: Handles specific exception types
 * - Returns consistent error response structure
 * - Handles validation errors, custom exceptions, and runtime exceptions
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. When an exception is thrown anywhere in the application
 * 2. Spring MVC routes it to this handler
 * 3. Matching @ExceptionHandler method is invoked
 * 4. Returns ResponseEntity with appropriate HTTP status
 * 5. Error response includes timestamp, message, and details
 * 
 * HANDLED EXCEPTIONS:
 * - ResourceNotFoundException: Returns 404 Not Found
 * - InsufficientBalanceException: Returns 400 Bad Request
 * - DuplicateEmailException: Returns 409 Conflict
 * - BadCredentialsException: Returns 401 Unauthorized
 * - MethodArgumentNotValidException: Returns 400 with validation errors
 * - RuntimeException: Returns 500 Internal Server Error
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I implemented a global exception handler using @RestControllerAdvice to handle
 * exceptions centrally across the application. This eliminates the need to handle
 * exceptions in each controller. The handler catches specific exceptions like
 * ResourceNotFoundException and returns appropriate HTTP status codes. For validation
 * errors, it extracts field-specific error messages. This provides consistent error
 * responses across all endpoints and improves the API's reliability. The error
 * response includes a timestamp, message, and details for debugging."
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                "Resource Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Insufficient Balance",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmailException(DuplicateEmailException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                "Duplicate Email",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now(),
                "Authentication Failed",
                "Invalid email or password"
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                "Validation Failed",
                errors.toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Internal Server Error",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                "Internal Server Error",
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
