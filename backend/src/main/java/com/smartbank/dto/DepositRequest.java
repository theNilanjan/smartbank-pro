package com.smartbank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DepositRequest DTO - Data Transfer Object for deposit requests
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines the contract for deposit operations. It ensures only valid
 * deposit amounts are processed through validation.
 * 
 * WHAT THE CODE DOES:
 * - Contains account ID and deposit amount
 * - @NotNull validation ensures fields are provided
 * - @Positive validation ensures amount is greater than zero
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends JSON with account ID and amount
 * 2. Spring maps JSON to this DTO
 * 3. Validation annotations check data integrity
 * 4. If validation fails, returns 400 Bad Request
 * 5. If valid, passes to service for deposit processing
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "DepositRequest DTO defines the contract for deposit operations with validation.
 * @NotNull ensures the account ID and amount are provided, and @Positive ensures
 * the deposit amount is greater than zero. This prevents invalid or negative deposits.
 * Using a DTO separates the API contract from the entity, providing better security
 * and validation at the API boundary."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
