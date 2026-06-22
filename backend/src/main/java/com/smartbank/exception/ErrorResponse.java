package com.smartbank.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ErrorResponse DTO - Standard error response structure
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines a consistent error response structure across all API endpoints.
 * It provides clients with standardized error information including status code,
 * timestamp, error type, and message.
 * 
 * WHAT THE CODE DOES:
 * - Contains HTTP status code
 * - Contains timestamp of when error occurred
 * - Contains error type/title
 * - Contains detailed error message
 * - Used by GlobalExceptionHandler for all error responses
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Exception handler creates this DTO with error details
 * 2. Spring automatically converts to JSON
 * 3. Client receives consistent error response structure
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "ErrorResponse DTO provides a consistent error response structure across all
 * API endpoints. It includes the HTTP status code, timestamp, error type, and
 * detailed message. This consistency helps clients parse error responses reliably.
 * The @JsonFormat annotation ensures the timestamp is formatted in ISO format.
 * Using a standard error response structure is a best practice in REST API design."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private String error;

    private String message;
}
