package com.smartbank.enums;

/**
 * Role Enum - Defines user roles in the banking system
 * 
 * WHY THIS FILE IS NEEDED:
 * Enums provide type-safe constants for user roles. This prevents invalid values
 * and makes the code more readable and maintainable.
 * 
 * WHAT THE CODE DOES:
 * - Defines two roles: CUSTOMER and ADMIN
 * - CUSTOMER: Regular bank users who can manage their accounts
 * - ADMIN: System administrators who can view all data and manage accounts
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Java enums are special classes that represent a fixed set of constants
 * 2. When stored in database with @Enumerated(EnumType.STRING), it stores "CUSTOMER" or "ADMIN"
 * 3. Provides compile-time type safety - you can't assign invalid values
 * 4. Can be used in switch statements and if conditions
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I used an enum for roles because it provides type safety and prevents invalid values.
 * Instead of using strings like 'customer' or 'admin' which can have typos, enums give us
 * compile-time checking. In the database, we store these as strings using @Enumerated(EnumType.STRING).
 * This approach is cleaner and more maintainable than using string constants or integer codes."
 */
public enum Role {
    CUSTOMER,
    ADMIN
}
