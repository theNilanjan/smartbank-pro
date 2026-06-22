package com.smartbank.entity;

import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Account Entity - Represents a bank account
 * 
 * WHY THIS FILE IS NEEDED:
 * This entity represents bank accounts in the system. Each account belongs to a user
 * and can have multiple transactions. It stores account details like balance, type, and status.
 * 
 * WHAT THE CODE DOES:
 * - @Entity: Maps this class to the "accounts" table
 * - accountNumber: Unique identifier for the account (auto-generated)
 * - accountType: Type of account (SAVINGS or CURRENT)
 * - balance: Current account balance using BigDecimal for precision
 * - status: Account status (ACTIVE, FROZEN, or CLOSED)
 * - @ManyToOne: Many accounts belong to one user
 * - @OneToMany: One account can have many transactions
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. JPA maps this to an "accounts" table with foreign key to users table
 * 2. @ManyToOne creates a foreign key "user_id" in accounts table
 * 3. @OneToMany with Transaction creates a foreign key "account_id" in transactions table
 * 4. BigDecimal is used instead of double for monetary values to avoid floating-point errors
 * 5. accountNumber is generated using a custom service (we'll implement this later)
 * 6. @PrePersist sets createdAt timestamp automatically
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Account entity represents bank accounts with a @ManyToOne relationship to User,
 * meaning multiple accounts can belong to one user. I used BigDecimal for balance because
 * floating-point types like double can have precision errors with monetary calculations.
 * The account number is auto-generated using a custom service to ensure uniqueness.
 * The @OneToMany relationship with Transaction allows tracking all account activities.
 * I used FetchType.LAZY for both relationships for performance optimization."
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Many accounts belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // One account can have many transactions
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
