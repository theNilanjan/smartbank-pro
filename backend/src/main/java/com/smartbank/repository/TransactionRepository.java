package com.smartbank.repository;

import com.smartbank.entity.Transaction;
import com.smartbank.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TransactionRepository - Data access layer for Transaction entity
 * 
 * WHY THIS FILE IS NEEDED:
 * Repository interfaces provide methods to perform CRUD operations on transactions.
 * This repository handles all database operations related to banking transactions.
 * 
 * WHAT THE CODE DOES:
 * - Extends JpaRepository<Transaction, Long>: Inherits CRUD methods
 * - List<Transaction> findByAccountId(Long accountId): Find all transactions for an account
 * - List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId): Find transactions sorted by date
 * - List<Transaction> findByTransactionType(TransactionType type): Find transactions by type
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Spring Data JPA generates SQL queries based on method names
 * 2. findByAccountId → SELECT * FROM transactions WHERE account_id = ?
 * 3. OrderByTransactionDateDesc adds ORDER BY transaction_date DESC
 * 4. Results are automatically mapped to Transaction entities
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransactionRepository extends JpaRepository to handle transaction-related database operations.
 * I added methods like findByAccountId to get all transactions for a specific account, and
 * findByAccountIdOrderByTransactionDateDesc to get transactions sorted by most recent first.
 * The OrderBy clause in the method name automatically adds sorting to the SQL query.
 * This allows customers to view their transaction history in chronological order."
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    List<Transaction> findByTransactionType(TransactionType type);
}
