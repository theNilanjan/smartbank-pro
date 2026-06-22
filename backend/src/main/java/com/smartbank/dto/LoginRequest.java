package com.smartbank.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequest DTO - Data Transfer Object for login requests
 * 
 * WHY THIS FILE IS NEEDED:
 * DTOs are used to transfer data between client and server. They separate the
 * API contract from entity classes, providing better security and flexibility.
 * 
 * WHAT THE CODE DOES:
 * - Contains email and password fields for login
 * - @NotBlank validation ensures fields are not empty
 * - @Email validation ensures email format is correct
 * - Lombok annotations reduce boilerplate code
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends JSON with email and password
 * 2. Spring automatically maps JSON to this DTO
 * 3. Validation annotations check data integrity
 * 4. If validation fails, returns 400 Bad Request
 * 5. If valid, passes to controller for processing
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I use DTOs to separate the API layer from entity classes. The LoginRequest DTO
 * defines the contract for login requests with validation annotations. @NotBlank
 * ensures the fields are not empty, and @Email validates the email format.
 * This approach provides better security because we're not exposing entity fields
 * directly to the client. It also gives us flexibility to change the entity
 * structure without breaking the API contract."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
