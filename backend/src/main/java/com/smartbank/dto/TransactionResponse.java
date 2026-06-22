package com.smartbank.dto;

import com.smartbank.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TransactionResponse DTO - Data Transfer Object for transaction responses
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines what transaction data is returned to the client. It separates
 * the API response from the entity, providing better security and control.
 * 
 * WHAT THE CODE DOES:
 * - Contains transaction details (id, type, amount, description, date)
 * - Includes account information
 * - Used in responses for transaction operations
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Service layer creates this DTO from Transaction entity
 * 2. Controller returns this DTO
 * 3. Spring automatically converts to JSON
 * 4. Client receives transaction information
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransactionResponse DTO defines what transaction data is exposed to the client.
 * It includes the transaction ID, type (DEPOSIT, WITHDRAWAL, TRANSFER), amount,
 * description, and timestamp. Using a DTO instead of returning the entity directly
 * gives us control over what data is sent to the client and prevents exposing
 * sensitive information. The amount uses BigDecimal for precise monetary calculations.
 * The transactionDate provides an audit trail for all banking operations."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private Long accountId;
    private String accountNumber;
}
