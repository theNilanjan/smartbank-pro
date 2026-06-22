package com.smartbank.entity;

import com.smartbank.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction Entity - Represents a banking transaction
 * 
 * WHY THIS FILE IS NEEDED:
 * This entity records all banking transactions (deposits, withdrawals, transfers).
 * It maintains an audit trail of all account activities for regulatory compliance
 * and customer reference.
 * 
 * WHAT THE CODE DOES:
 * - @Entity: Maps this class to the "transactions" table
 * - transactionType: Type of transaction (DEPOSIT, WITHDRAWAL, TRANSFER)
 * - amount: Transaction amount using BigDecimal for precision
 * - description: Optional description of the transaction
 * - transactionDate: When the transaction occurred
 * - fromAccount: Source account (for transfers)
 * - toAccount: Destination account (for transfers)
 * - @ManyToOne: Many transactions belong to one account
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. JPA maps this to a "transactions" table with foreign key to accounts table
 * 2. @ManyToOne creates a foreign key "account_id" in transactions table
 * 3. fromAccount and toAccount are used for TRANSFER transactions
 * 4. For DEPOSIT/WITHDRAWAL, only the main account relationship is used
 * 5. BigDecimal ensures precise monetary calculations
 * 6. @PrePersist sets transactionDate automatically
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Transaction entity records all banking operations with a @ManyToOne relationship
 * to Account. This allows us to track the complete transaction history for each account.
 * I used BigDecimal for the amount to avoid floating-point precision issues with money.
 * The fromAccount and toAccount fields are specifically for TRANSFER transactions,
 * while DEPOSIT and WITHDRAWAL only use the main account relationship. The transactionDate
 * is automatically set using @PrePersist, ensuring accurate timestamping for audit purposes."
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(length = 255)
    private String description;

    @Column(name = "transaction_date", nullable = false, updatable = false)
    private LocalDateTime transactionDate;

    // Many transactions belong to one account (the account involved in the transaction)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    // For TRANSFER transactions - source account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    // For TRANSFER transactions - destination account
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }
}
