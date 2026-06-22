package com.smartbank.enums;

/**
 * AccountStatus Enum - Defines the status of a bank account
 * 
 * WHY THIS FILE IS NEEDED:
 * Accounts can be in different states (active, frozen, closed). This enum ensures
 * only valid statuses can be assigned and provides clear business logic.
 * 
 * WHAT THE CODE DOES:
 * - ACTIVE: Account is operational and can perform transactions
 * - FROZEN: Account is temporarily frozen (no transactions allowed)
 * - CLOSED: Account is permanently closed
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Provides type-safe constants for account status
 * 2. Stored as strings in database ("ACTIVE", "FROZEN", "CLOSED")
 * 3. Business logic checks status before allowing transactions
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I used an enum for account status to represent the different states an account can be in.
 * ACTIVE accounts can perform all transactions. FROZEN accounts are temporarily blocked,
 * which is useful for fraud detection or regulatory compliance. CLOSED accounts are
 * permanently closed. The transaction service checks the account status before processing
 * any transaction to ensure business rules are enforced."
 */
public enum AccountStatus {
    ACTIVE,
    FROZEN,
    CLOSED
}
