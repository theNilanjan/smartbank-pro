package com.smartbank.service;

import com.smartbank.dto.DepositRequest;
import com.smartbank.dto.TransactionResponse;
import com.smartbank.dto.TransferRequest;
import com.smartbank.dto.WithdrawRequest;
import com.smartbank.entity.Account;
import com.smartbank.entity.Transaction;
import com.smartbank.enums.AccountStatus;
import com.smartbank.enums.TransactionType;
import com.smartbank.exception.InsufficientBalanceException;
import com.smartbank.exception.ResourceNotFoundException;
import com.smartbank.repository.AccountRepository;
import com.smartbank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * TransactionService - Service layer for banking transaction operations
 * 
 * WHY THIS FILE IS NEEDED:
 * The service layer contains business logic for banking transactions. It handles
 * deposits, withdrawals, and fund transfers with proper validation and ACID
 * properties. This separates business logic from the controller layer.
 * 
 * WHAT THE CODE DOES:
 * - deposit(): Adds money to an account and records transaction
 * - withdraw(): Removes money from an account and records transaction
 * - transfer(): Moves money from one account to another atomically
 * - getTransactionHistory(): Retrieves all transactions for an account
 * - Uses @Transactional for ACID properties
 * - Validates account status and balance before operations
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Deposit:
 *    - Validates account exists and is active
 *    - Adds amount to account balance
 *    - Creates DEPOSIT transaction record
 *    - Saves both account and transaction atomically
 * 2. Withdraw:
 *    - Validates account exists and is active
 *    - Checks sufficient balance
 *    - Subtracts amount from account balance
 *    - Creates WITHDRAWAL transaction record
 *    - Saves both account and transaction atomically
 * 3. Transfer:
 *    - Validates both accounts exist and are active
 *    - Checks sufficient balance in from account
 *    - Subtracts amount from from account
 *    - Adds amount to to account
 *    - Creates TRANSFER transaction record
 *    - Saves all changes atomically
 * 
 * ACID Properties:
 * - Atomicity: All operations succeed or all fail (no partial updates)
 * - Consistency: Database remains in valid state (balance constraints)
 * - Isolation: Concurrent transactions don't interfere
 * - Durability: Committed transactions persist even after system failure
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransactionService handles the business logic for banking operations. I use
 * @Transactional to ensure ACID properties - Atomicity, Consistency, Isolation,
 * and Durability. For example, in a transfer operation, both the debit and credit
 * must succeed together, or both must fail. If the system crashes after debiting
 * but before crediting, the transaction rolls back automatically. This prevents
 * data corruption and ensures money is never lost. I also validate account status
 * (must be ACTIVE) and check sufficient balance before processing withdrawals
 * and transfers."
 */
@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponse deposit(DepositRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Cannot deposit to inactive account");
        }

        // Add amount to balance
        account.setBalance(account.getBalance().add(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(request.getAmount());
        transaction.setDescription("Deposit to account");
        transaction.setAccount(account);

        // Save both atomically
        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction, account);
    }

    @Transactional
    public TransactionResponse withdraw(WithdrawRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Cannot withdraw from inactive account");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Subtract amount from balance
        account.setBalance(account.getBalance().subtract(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setAmount(request.getAmount());
        transaction.setDescription("Withdrawal from account");
        transaction.setAccount(account);

        // Save both atomically
        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction, account);
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        Account fromAccount = accountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("From account not found"));

        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("To account not found"));

        if (fromAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Cannot transfer from inactive account");
        }

        if (toAccount.getStatus() != AccountStatus.ACTIVE) {
            throw new RuntimeException("Cannot transfer to inactive account");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // Subtract from from account
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));

        // Add to to account
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setAmount(request.getAmount());
        transaction.setDescription("Transfer from account " + fromAccount.getAccountNumber() + " to " + toAccount.getAccountNumber());
        transaction.setAccount(fromAccount);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);

        // Save all atomically
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction, fromAccount);
    }

    public java.util.List<TransactionResponse> getTransactionHistory(Long accountId) {
        java.util.List<Transaction> transactions = transactionRepository
                .findByAccountIdOrderByTransactionDateDesc(accountId);
        return transactions.stream()
                .map(transaction -> mapToResponse(transaction, transaction.getAccount()))
                .toList();
    }

    private TransactionResponse mapToResponse(Transaction transaction, Account account) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                account.getId(),
                account.getAccountNumber()
        );
    }
}
