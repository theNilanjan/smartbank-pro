package com.smartbank.controller;

import com.smartbank.dto.DepositRequest;
import com.smartbank.dto.TransactionResponse;
import com.smartbank.dto.TransferRequest;
import com.smartbank.dto.WithdrawRequest;
import com.smartbank.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TransactionController - REST API controller for banking transaction endpoints
 * 
 * WHY THIS FILE IS NEEDED:
 * Controllers expose REST API endpoints that clients can call. This controller
 * handles transaction-related endpoints like deposit, withdraw, transfer, and
 * transaction history.
 * 
 * WHAT THE CODE DOES:
 * - @RestController: Marks this as a REST controller
 * - @RequestMapping: Base path for all endpoints (/api/transactions)
 * - deposit(): POST endpoint to deposit money
 * - withdraw(): POST endpoint to withdraw money
 * - transfer(): POST endpoint to transfer funds
 * - getTransactionHistory(): GET endpoint to retrieve transaction history
 * - @Valid: Validates request DTOs
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends HTTP request with JWT token in Authorization header
 * 2. JwtAuthenticationFilter validates token and sets authentication
 * 3. Controller method validates request DTO using @Valid
 * 4. If validation fails, returns 400 Bad Request
 * 5. If valid, calls TransactionService
 * 6. Service performs business logic with @Transactional
 * 7. Returns TransactionResponse
 * 8. Spring converts to JSON
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransactionController exposes REST API endpoints for banking operations. The
 * @Valid annotation triggers validation of request DTOs before the service layer
 * is called. The controller delegates business logic to TransactionService,
 * which handles the actual transaction processing with @Transactional for ACID
 * properties. This separation keeps the controller focused on HTTP handling while
 * the service contains business logic. All transaction operations require a valid
 * JWT token in the Authorization header."
 */
@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Banking Transaction APIs")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit money", description = "Deposits money into an account")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest request) {
        TransactionResponse response = transactionService.deposit(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money", description = "Withdraws money from an account")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        TransactionResponse response = transactionService.withdraw(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer funds", description = "Transfers money from one account to another")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionResponse response = transactionService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{accountId}")
    @Operation(summary = "Get transaction history", description = "Retrieves transaction history for an account")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable Long accountId) {
        List<TransactionResponse> response = transactionService.getTransactionHistory(accountId);
        return ResponseEntity.ok(response);
    }
}
