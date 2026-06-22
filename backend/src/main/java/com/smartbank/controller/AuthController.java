package com.smartbank.controller;

import com.smartbank.dto.AuthResponse;
import com.smartbank.dto.LoginRequest;
import com.smartbank.dto.RegisterRequest;
import com.smartbank.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - REST API controller for authentication endpoints
 * 
 * WHY THIS FILE IS NEEDED:
 * Controllers expose REST API endpoints that clients can call. This controller
 * handles authentication-related endpoints like login and registration.
 * 
 * WHAT THE CODE DOES:
 * - @RestController: Marks this as a REST controller (auto JSON serialization)
 * - @RequestMapping: Base path for all endpoints (/api/auth)
 * - register(): POST endpoint for user registration
 * - login(): POST endpoint for user login
 * - @Valid: Validates request DTOs using validation annotations
 * - @Tag: Swagger documentation grouping
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends HTTP POST request with JSON body
 * 2. Spring maps JSON to DTO object
 * 3. @Valid annotation triggers validation
 * 4. If validation fails, returns 400 Bad Request with errors
 * 5. If valid, calls AuthService method
 * 6. Service returns AuthResponse
 * 7. Spring automatically converts AuthResponse to JSON
 * 8. Returns 200 OK with JSON response
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AuthController exposes REST API endpoints for authentication. The @RestController
 * annotation tells Spring to automatically serialize return objects to JSON.
 * @RequestMapping defines the base path for all endpoints. The register and login
 * methods accept request DTOs with @Valid annotation for validation. The controller
 * delegates business logic to AuthService, which handles the actual authentication
 * logic. This separation of concerns keeps the controller thin and focused on HTTP
 * handling, while the service contains business logic."
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with email and password")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
