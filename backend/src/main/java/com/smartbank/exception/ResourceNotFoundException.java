package com.smartbank.exception;

/**
 * ResourceNotFoundException - Custom exception for resource not found scenarios
 * 
 * WHY THIS FILE IS NEEDED:
 * Custom exceptions provide specific error handling for different scenarios.
 * This exception is thrown when a requested resource (user, account, transaction)
 * is not found in the database.
 * 
 * WHAT THE CODE DOES:
 * - Extends RuntimeException for unchecked exception
 * - Contains error message
 * - Can be caught by global exception handler
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Service layer throws this exception when resource not found
 * 2. Global exception handler catches it
 * 3. Returns appropriate HTTP 404 response
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I created custom exceptions like ResourceNotFoundException to handle specific
 * error scenarios. This allows the global exception handler to return appropriate
 * HTTP status codes and error messages. For example, when a user is not found,
 * we throw ResourceNotFoundException, which the handler catches and returns a
 * 404 Not Found response. This provides better error handling and user experience
 * compared to generic exceptions."
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
