package com.smartbank.repository;

import com.smartbank.entity.Account;
import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * AccountRepository - Data access layer for Account entity
 * 
 * WHY THIS FILE IS NEEDED:
 * Repository interfaces provide methods to perform CRUD operations on accounts.
 * This repository handles all database operations related to bank accounts.
 * 
 * WHAT THE CODE DOES:
 * - Extends JpaRepository<Account, Long>: Inherits CRUD methods
 * - Optional<Account> findByAccountNumber(String accountNumber): Find account by account number
 * - List<Account> findByUserId(Long userId): Find all accounts for a specific user
 * - List<Account> findByStatus(AccountStatus status): Find accounts by status
 * - boolean existsByAccountNumber(String accountNumber): Check if account number exists
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Spring Data JPA generates SQL queries based on method names
 * 2. findByAccountNumber → SELECT * FROM accounts WHERE account_number = ?
 * 3. findByUserId → SELECT * FROM accounts WHERE user_id = ?
 * 4. Uses Hibernate's session to execute queries
 * 5. Results are mapped to Account entities automatically
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AccountRepository extends JpaRepository to handle account-related database operations.
 * I added custom query methods like findByAccountNumber to retrieve accounts by their
 * unique account number, and findByUserId to get all accounts belonging to a user.
 * Spring Data JPA automatically generates the SQL queries based on the method names,
 * which follows the naming convention. This approach reduces boilerplate code and
 * makes the data access layer clean and maintainable."
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByUserId(Long userId);

    List<Account> findByStatus(AccountStatus status);

    boolean existsByAccountNumber(String accountNumber);
}
