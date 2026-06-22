package com.smartbank.exception;

/**
 * InsufficientBalanceException - Custom exception for insufficient balance scenarios
 * 
 * WHY THIS FILE IS NEEDED:
 * Custom exceptions provide specific error handling for different scenarios.
 * This exception is thrown when a user tries to withdraw or transfer more than
 * their available balance.
 * 
 * WHAT THE CODE DOES:
 * - Extends RuntimeException for unchecked exception
 * - Contains error message
 * - Can be caught by global exception handler
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Service layer throws this exception when balance is insufficient
 * 2. Global exception handler catches it
 * 3. Returns appropriate HTTP 400 Bad Request response
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I created InsufficientBalanceException to handle scenarios where users try to
 * withdraw or transfer more than their available balance. This specific exception
 * allows the global exception handler to return a clear error message and appropriate
 * HTTP status code (400 Bad Request). This provides better user experience compared
 * to generic exceptions and helps clients understand exactly what went wrong."
 */
public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
