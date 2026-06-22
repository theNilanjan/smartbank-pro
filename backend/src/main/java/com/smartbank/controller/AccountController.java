package com.smartbank.controller;

import com.smartbank.dto.AccountRequest;
import com.smartbank.dto.AccountResponse;
import com.smartbank.entity.User;
import com.smartbank.exception.ResourceNotFoundException;
import com.smartbank.repository.UserRepository;
import com.smartbank.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AccountController - REST API controller for account management endpoints
 * 
 * WHY THIS FILE IS NEEDED:
 * Controllers expose REST API endpoints that clients can call. This controller
 * handles account-related endpoints like creating accounts, viewing accounts,
 * and managing account status.
 * 
 * WHAT THE CODE DOES:
 * - @RestController: Marks this as a REST controller
 * - @RequestMapping: Base path for all endpoints (/api/accounts)
 * - createAccount(): POST endpoint to create new account
 * - getAccountById(): GET endpoint to retrieve account by ID
 * - getAccountByNumber(): GET endpoint to retrieve account by number
 * - getUserAccounts(): GET endpoint to retrieve all user accounts
 * - freezeAccount(): PUT endpoint to freeze an account
 * - activateAccount(): PUT endpoint to activate an account
 * - @PreAuthorize: Role-based access control
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends HTTP request with JWT token in Authorization header
 * 2. JwtAuthenticationFilter validates token and sets authentication
 * 3. @PreAuthorize checks if user has required role
 * 4. Controller method calls AccountService
 * 5. Service performs business logic
 * 6. Returns AccountResponse
 * 7. Spring converts to JSON
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AccountController exposes REST API endpoints for account management. The
 * @PreAuthorize annotation enforces role-based access control - for example,
 * only admins can freeze accounts. The Authentication object provides access
 * to the currently authenticated user's details. The controller delegates
 * business logic to AccountService, which handles account creation, retrieval,
 * and status updates. This separation keeps the controller focused on HTTP
 * handling while the service contains business logic."
 */
@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Account Management", description = "Account Management APIs")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create a new account", description = "Creates a new bank account for the authenticated user")
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountRequest request,
            Authentication authentication) {
        
        // Extract user ID from authentication
        // In a real scenario, you'd get this from the JWT token or user details
        // For now, we'll assume the user ID is passed or extracted from token
        Long userId = getUserIdFromAuthentication(authentication);
        
        AccountResponse response = accountService.createAccount(userId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by ID", description = "Retrieves account details by account ID")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        AccountResponse response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{accountNumber}")
    @Operation(summary = "Get account by number", description = "Retrieves account details by account number")
    public ResponseEntity<AccountResponse> getAccountByNumber(@PathVariable String accountNumber) {
        AccountResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user accounts", description = "Retrieves all accounts for a specific user")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(@PathVariable Long userId) {
        List<AccountResponse> response = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/freeze")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Freeze account", description = "Freezes an account (admin only)")
    public ResponseEntity<AccountResponse> freezeAccount(@PathVariable Long id) {
        AccountResponse response = accountService.freezeAccount(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate account", description = "Activates a frozen account (admin only)")
    public ResponseEntity<AccountResponse> activateAccount(@PathVariable Long id) {
        AccountResponse response = accountService.activateAccount(id);
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getId();
    }
}
