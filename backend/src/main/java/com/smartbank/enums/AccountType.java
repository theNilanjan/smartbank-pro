package com.smartbank.enums;

/**
 * AccountType Enum - Defines types of bank accounts
 * 
 * WHY THIS FILE IS NEEDED:
 * Different account types have different features and rules. This enum ensures
 * only valid account types can be assigned to accounts.
 * 
 * WHAT THE CODE DOES:
 * - SAVINGS: Standard savings account for personal use
 * - CURRENT: Business account with higher transaction limits
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Provides type-safe constants for account types
 * 2. Stored as strings in database ("SAVINGS", "CURRENT")
 * 3. Can be extended with more types in future (FD, RD, etc.)
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I used an enum for account types to ensure type safety. Savings accounts are for
 * personal use with interest, while Current accounts are for businesses with higher
 * transaction limits. Using enums prevents invalid values and makes the code more
 * maintainable. If we need to add new account types later, we just add them to the enum."
 */
public enum AccountType {
    SAVINGS,
    CURRENT
}
