package com.smartbank.service;

import com.smartbank.dto.AccountRequest;
import com.smartbank.dto.AccountResponse;
import com.smartbank.entity.Account;
import com.smartbank.entity.User;
import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.AccountType;
import com.smartbank.exception.ResourceNotFoundException;
import com.smartbank.repository.AccountRepository;
import com.smartbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * AccountService - Service layer for account management operations
 * 
 * WHY THIS FILE IS NEEDED:
 * The service layer contains business logic for account management. It handles
 * account creation, retrieval, and status updates. This separates business
 * logic from the controller layer.
 * 
 * WHAT THE CODE DOES:
 * - createAccount(): Creates a new account with auto-generated account number
 * - getAccountById(): Retrieves account by ID
 * - getAccountByNumber(): Retrieves account by account number
 * - getUserAccounts(): Retrieves all accounts for a user
 * - freezeAccount(): Freezes an account (no transactions allowed)
 * - activateAccount(): Activates a frozen account
 * - generateAccountNumber(): Generates unique 16-digit account number
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Account Creation:
 *    - Validates user exists
 *    - Generates unique 16-digit account number
 *    - Sets initial balance to zero
 *    - Sets status to ACTIVE
 *    - Saves account to database
 * 2. Account Number Generation:
 *    - Uses random number generation
 *    - Ensures uniqueness by checking database
 *    - Retries if collision occurs
 * 3. Status Updates:
 *    - Updates account status
 *    - Saves to database
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AccountService handles the business logic for account management. When creating
 * an account, I generate a unique 16-digit account number using a random number
 * generator. I check the database to ensure the number doesn't already exist.
 * The account is created with zero balance and ACTIVE status. For status updates
 * like freeze and activate, I simply update the status field and save. The
 * @Transactional annotation ensures these operations are atomic - either all
 * succeed or all fail, maintaining data consistency."
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public AccountResponse createAccount(Long userId, AccountRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    public AccountResponse getAccountById(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    public AccountResponse getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        return mapToResponse(account);
    }

    public List<AccountResponse> getUserAccounts(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public AccountResponse freezeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        
        account.setStatus(AccountStatus.FROZEN);
        Account savedAccount = accountRepository.save(account);
        
        return mapToResponse(savedAccount);
    }

    @Transactional
    public AccountResponse activateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        
        account.setStatus(AccountStatus.ACTIVE);
        Account savedAccount = accountRepository.save(account);
        
        return mapToResponse(savedAccount);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        String accountNumber;
        do {
            accountNumber = String.format("%016d", random.nextLong(1000000000000000L, 9999999999999999L));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private AccountResponse mapToResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus(),
                account.getCreatedAt()
        );
    }
}
