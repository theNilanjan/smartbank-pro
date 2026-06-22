package com.smartbank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * TransferRequest DTO - Data Transfer Object for fund transfer requests
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines the contract for fund transfer operations. It ensures only valid
 * transfer requests are processed through validation.
 * 
 * WHAT THE CODE DOES:
 * - Contains from account ID, to account ID, and transfer amount
 * - @NotNull validation ensures all fields are provided
 * - @Positive validation ensures amount is greater than zero
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends JSON with from account, to account, and amount
 * 2. Spring maps JSON to this DTO
 * 3. Validation annotations check data integrity
 * 4. If validation fails, returns 400 Bad Request
 * 5. If valid, passes to service for transfer processing
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransferRequest DTO defines the contract for fund transfer operations with validation.
 * @NotNull ensures the from account, to account, and amount are provided, and @Positive
 * ensures the transfer amount is greater than zero. The service layer will additionally
 * check if the from account has sufficient balance and if both accounts are active before
 * processing the transfer. Using a DTO separates the API contract from the entity,
 * providing better security and validation at the API boundary."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    @NotNull(message = "From account ID is required")
    private Long fromAccountId;

    @NotNull(message = "To account ID is required")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
}
