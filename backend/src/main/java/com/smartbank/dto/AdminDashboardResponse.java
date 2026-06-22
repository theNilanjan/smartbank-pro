package com.smartbank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * AdminDashboardResponse DTO - Data Transfer Object for admin dashboard metrics
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines the response structure for admin dashboard metrics. It provides
 * aggregated statistics about the banking system for admin monitoring.
 * 
 * WHAT THE CODE DOES:
 * - Contains system-wide metrics (total users, accounts, deposits, transfers)
 * - Includes lists of users, accounts, and transactions
 * - Used in admin dashboard endpoint response
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Admin service aggregates data from multiple repositories
 * 2. Creates this DTO with calculated metrics
 * 3. Controller returns this DTO
 * 4. Spring automatically converts to JSON
 * 5. Admin dashboard displays the metrics
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AdminDashboardResponse DTO provides aggregated system metrics for the admin
 * dashboard. It includes counts of total users, accounts, deposits, and transfers,
 * along with detailed lists of each entity. This allows admins to monitor the
 * banking system's health and activity. The service layer calculates these metrics
 * by querying the repositories and aggregating the data. Using a DTO gives us
 * control over what data is exposed and allows for efficient data transfer."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardResponse {

    private Long totalUsers;
    private Long totalAccounts;
    private BigDecimal totalDeposits;
    private BigDecimal totalTransfers;
    private List<UserSummary> users;
    private List<AccountSummary> accounts;
    private List<TransactionSummary> transactions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSummary {
        private Long id;
        private String fullName;
        private String email;
        private String role;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountSummary {
        private Long id;
        private String accountNumber;
        private String accountType;
        private BigDecimal balance;
        private String status;
        private String userEmail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionSummary {
        private Long id;
        private String transactionType;
        private BigDecimal amount;
        private String description;
        private String accountNumber;
    }
}
