package com.smartbank.dto;

import com.smartbank.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest DTO - Data Transfer Object for registration requests
 * 
 * WHY THIS FILE IS NEEDED:
 * DTOs are used to transfer data between client and server. This DTO defines
 * the contract for user registration with validation rules.
 * 
 * WHAT THE CODE DOES:
 * - Contains user registration fields (fullName, email, password, role)
 * - @NotBlank validation ensures fields are not empty
 * - @Email validation ensures email format is correct
 * - @Size validation ensures password meets minimum length
 * - Default role is CUSTOMER for security
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends JSON with registration data
 * 2. Spring automatically maps JSON to this DTO
 * 3. Validation annotations check data integrity
 * 4. If validation fails, returns 400 Bad Request with error messages
 * 5. If valid, passes to service for user creation
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The RegisterRequest DTO defines the contract for user registration with validation.
 * I used @NotBlank to ensure required fields are provided, @Email for email format
 * validation, and @Size to enforce minimum password length. The role field defaults
 * to CUSTOMER for security - only admins can create admin users. Using DTOs separates
 * the API contract from entity classes, providing better security and flexibility."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private Role role = Role.CUSTOMER;
}
