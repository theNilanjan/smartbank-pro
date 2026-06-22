package com.smartbank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * WithdrawRequest DTO - Data Transfer Object for withdrawal requests
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines the contract for withdrawal operations. It ensures only valid
 * withdrawal amounts are processed through validation.
 * 
 * WHAT THE CODE DOES:
 * - Contains account ID and withdrawal amount
 * - @NotNull validation ensures fields are provided
 * - @Positive validation ensures amount is greater than zero
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends JSON with account ID and amount
 * 2. Spring maps JSON to this DTO
 * 3. Validation annotations check data integrity
 * 4. If validation fails, returns 400 Bad Request
 * 5. If valid, passes to service for withdrawal processing
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "WithdrawRequest DTO defines the contract for withdrawal operations with validation.
 * @NotNull ensures the account ID and amount are provided, and @Positive ensures
 * the withdrawal amount is greater than zero. The service layer will additionally
 * check if the account has sufficient balance before processing the withdrawal.
 * Using a DTO separates the API contract from the entity, providing better security
 * and validation at the API boundary."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
