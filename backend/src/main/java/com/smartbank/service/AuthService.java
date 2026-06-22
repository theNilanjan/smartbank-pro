package com.smartbank.service;

import com.smartbank.dto.AuthResponse;
import com.smartbank.dto.LoginRequest;
import com.smartbank.dto.RegisterRequest;
import com.smartbank.entity.User;
import com.smartbank.enums.Role;
import com.smartbank.exception.DuplicateEmailException;
import com.smartbank.exception.ResourceNotFoundException;
import com.smartbank.repository.UserRepository;
import com.smartbank.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthService - Service layer for authentication operations
 * 
 * WHY THIS FILE IS NEEDED:
 * The service layer contains business logic for authentication. It handles
 * user registration, login, and JWT token generation. This separates business
 * logic from the controller layer.
 * 
 * WHAT THE CODE DOES:
 * - register(): Registers a new user with encrypted password
 * - login(): Authenticates user and generates JWT token
 * - Uses PasswordEncoder to encrypt passwords
 * - Uses AuthenticationManager for authentication
 * - Uses JwtUtil to generate tokens
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Registration:
 *    - Checks if email already exists
 *    - Encrypts password using BCrypt
 *    - Saves user to database
 *    - Generates JWT token
 *    - Returns AuthResponse with token and user details
 * 2. Login:
 *    - Uses AuthenticationManager to authenticate
 *    - If successful, generates JWT token
 *    - Returns AuthResponse with token and user details
 * 3. Password Encryption:
 *    - BCrypt is a strong hashing algorithm
 *    - Automatically handles salt generation
 *    - One-way hash (cannot be decrypted)
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AuthService handles the business logic for authentication. For registration,
 * I check if the email already exists, encrypt the password using BCryptPasswordEncoder,
 * save the user to the database, and generate a JWT token. For login, I use the
 * AuthenticationManager to authenticate the user with their credentials. If
 * authentication succeeds, I generate a JWT token and return it. BCrypt is used
 * for password encryption because it's a strong, one-way hashing algorithm that
 * automatically handles salt generation, making it resistant to rainbow table attacks."
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(
                token,
                "Bearer",
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
