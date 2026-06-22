package com.smartbank.enums;

/**
 * TransactionType Enum - Defines types of banking transactions
 * 
 * WHY THIS FILE IS NEEDED:
 * Different transaction types have different business rules and validation logic.
 * This enum ensures only valid transaction types can be processed.
 * 
 * WHAT THE CODE DOES:
 * - DEPOSIT: Money added to account
 * - WITHDRAWAL: Money removed from account
 * - TRANSFER: Money moved from one account to another
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Provides type-safe constants for transaction types
 * 2. Stored as strings in database ("DEPOSIT", "WITHDRAWAL", "TRANSFER")
 * 3. Business logic applies different rules based on transaction type
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I used an enum for transaction types to represent the different operations users can perform.
 * DEPOSIT adds money to an account, WITHDRAWAL removes money, and TRANSFER moves money between
 * accounts. Each transaction type has different validation rules - for example, withdrawals
 * check for sufficient balance, while transfers check both sender and receiver account status.
 * Using enums ensures type safety and makes the code more maintainable."
 */
public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}
