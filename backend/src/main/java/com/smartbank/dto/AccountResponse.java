package com.smartbank.dto;

import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AccountResponse DTO - Data Transfer Object for account responses
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines what account data is returned to the client. It separates
 * the API response from the entity, providing better security and control.
 * 
 * WHAT THE CODE DOES:
 * - Contains account details (id, accountNumber, accountType, balance, status)
 * - Includes creation timestamp
 * - Used in responses for account operations
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Service layer creates this DTO from Account entity
 * 2. Controller returns this DTO
 * 3. Spring automatically converts to JSON
 * 4. Client receives account information
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AccountResponse DTO defines what account data is exposed to the client.
 * It includes the account number, type, balance, and status. Using a DTO
 * instead of returning the entity directly gives us control over what data
 * is sent to the client and prevents exposing sensitive information. The
 * balance uses BigDecimal for precise monetary calculations. The status
 * indicates whether the account is active, frozen, or closed."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime createdAt;
}
