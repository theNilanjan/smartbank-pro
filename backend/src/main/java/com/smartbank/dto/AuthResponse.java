package com.smartbank.dto;

import com.smartbank.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthResponse DTO - Data Transfer Object for authentication responses
 * 
 * WHY THIS FILE IS NEEDED:
 * This DTO defines the response structure after successful authentication.
 * It returns the JWT token and user information to the client.
 * 
 * WHAT THE CODE DOES:
 * - Contains JWT token for subsequent requests
 * - Contains user information (id, fullName, email, role)
 * - Used in both login and registration responses
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. After successful authentication, service creates this DTO
 * 2. JWT token is generated and set
 * 3. User details are populated
 * 4. Spring automatically converts DTO to JSON
 * 5. Client receives token and stores it (usually in localStorage)
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The AuthResponse DTO defines what the client receives after successful authentication.
 * It includes the JWT token that the client will send in the Authorization header for
 * subsequent requests. It also includes user details like id, name, email, and role
 * so the frontend can display user information. Using a DTO ensures we only send
 * necessary data to the client, not sensitive information like the password."
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String fullName;
    private String email;
    private Role role;
}
