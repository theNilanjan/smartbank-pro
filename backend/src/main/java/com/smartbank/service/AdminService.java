package com.smartbank.service;

import com.smartbank.dto.AdminDashboardResponse;
import com.smartbank.entity.Account;
import com.smartbank.entity.Transaction;
import com.smartbank.entity.User;
import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.TransactionType;
import com.smartbank.repository.AccountRepository;
import com.smartbank.repository.TransactionRepository;
import com.smartbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AdminService - Service layer for admin dashboard operations
 * 
 * WHY THIS FILE IS NEEDED:
 * The service layer contains business logic for admin operations. It aggregates
 * data from multiple repositories to provide dashboard metrics and system-wide
 * statistics for administrators.
 * 
 * WHAT THE CODE DOES:
 * - getDashboardMetrics(): Aggregates system-wide statistics
 * - Calculates total users, accounts, deposits, and transfers
 * - Retrieves lists of all users, accounts, and transactions
 * - Maps entities to summary DTOs for efficient data transfer
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Queries all repositories for data
 * 2. Calculates metrics by counting entities and summing amounts
 * 3. Filters transactions by type (DEPOSIT, TRANSFER)
 * 4. Maps entities to summary DTOs
 * 5. Returns aggregated dashboard response
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AdminService aggregates data from multiple repositories to provide dashboard
 * metrics for administrators. It calculates total counts of users and accounts,
 * and sums up deposit and transfer amounts. I use Java Streams to efficiently
 * process and filter the data. The service maps entities to summary DTOs to
 * control what data is exposed and reduce payload size. This allows admins to
 * monitor the banking system's health and activity in real-time."
 */
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public AdminDashboardResponse getDashboardMetrics() {
        List<User> allUsers = userRepository.findAll();
        List<Account> allAccounts = accountRepository.findAll();
        List<Transaction> allTransactions = transactionRepository.findAll();

        // Calculate metrics
        long totalUsers = allUsers.size();
        long totalAccounts = allAccounts.size();
        
        BigDecimal totalDeposits = allTransactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.DEPOSIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalTransfers = allTransactions.stream()
                .filter(t -> t.getTransactionType() == TransactionType.TRANSFER)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Map to summary DTOs
        List<AdminDashboardResponse.UserSummary> userSummaries = allUsers.stream()
                .map(this::mapToUserSummary)
                .collect(Collectors.toList());

        List<AdminDashboardResponse.AccountSummary> accountSummaries = allAccounts.stream()
                .map(this::mapToAccountSummary)
                .collect(Collectors.toList());

        List<AdminDashboardResponse.TransactionSummary> transactionSummaries = allTransactions.stream()
                .map(this::mapToTransactionSummary)
                .collect(Collectors.toList());

        return new AdminDashboardResponse(
                totalUsers,
                totalAccounts,
                totalDeposits,
                totalTransfers,
                userSummaries,
                accountSummaries,
                transactionSummaries
        );
    }

    private AdminDashboardResponse.UserSummary mapToUserSummary(User user) {
        return new AdminDashboardResponse.UserSummary(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    private AdminDashboardResponse.AccountSummary mapToAccountSummary(Account account) {
        return new AdminDashboardResponse.AccountSummary(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType().name(),
                account.getBalance(),
                account.getStatus().name(),
                account.getUser().getEmail()
        );
    }

    private AdminDashboardResponse.TransactionSummary mapToTransactionSummary(Transaction transaction) {
        return new AdminDashboardResponse.TransactionSummary(
                transaction.getId(),
                transaction.getTransactionType().name(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getAccount().getAccountNumber()
        );
    }
}
