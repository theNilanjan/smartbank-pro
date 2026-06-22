package com.smartbank.exception;

/**
 * DuplicateEmailException - Custom exception for duplicate email scenarios
 * 
 * WHY THIS FILE IS NEEDED:
 * Custom exceptions provide specific error handling for different scenarios.
 * This exception is thrown when a user tries to register with an email that
 * already exists in the database.
 * 
 * WHAT THE CODE DOES:
 * - Extends RuntimeException for unchecked exception
 * - Contains error message
 * - Can be caught by global exception handler
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Service layer throws this exception when email already exists
 * 2. Global exception handler catches it
 * 3. Returns appropriate HTTP 409 Conflict response
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I created DuplicateEmailException to handle registration scenarios where a user
 * tries to register with an email that's already registered. This specific exception
 * allows the global exception handler to return a 409 Conflict status code, which is
 * the appropriate HTTP response for duplicate resource attempts. This provides
 * better error handling and helps clients understand the specific error."
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
