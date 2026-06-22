package com.smartbank.security;

import com.smartbank.entity.User;
import com.smartbank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * CustomUserDetailsService - Implements Spring Security's UserDetailsService
 * 
 * WHY THIS FILE IS NEEDED:
 * Spring Security needs a UserDetailsService to load user-specific data during
 * authentication. This custom implementation loads users from our database.
 * 
 * WHAT THE CODE DOES:
 * - Implements UserDetailsService interface
 * - loadUserByUsername(): Loads user from database by email
 * - Converts our User entity to Spring Security's UserDetails
 * - Returns user with authorities (roles)
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. When user tries to authenticate, Spring Security calls this method
 * 2. Method queries database using UserRepository
 * 3. If user not found, throws UsernameNotFoundException
 * 4. If found, converts User entity to UserDetails object
 * 5. UserDetails contains: username, password, authorities (roles)
 * 6. Spring Security uses this to authenticate the user
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I implemented CustomUserDetailsService to integrate our User entity with Spring Security.
 * Spring Security's authentication process requires a UserDetailsService to load user data.
 * My implementation loads the user from the database using the UserRepository and converts
 * it to Spring Security's UserDetails object. The UserDetails object contains the user's
 * credentials and authorities (roles). If the user is not found, I throw a
 * UsernameNotFoundException, which Spring Security handles appropriately."
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(() -> "ROLE_" + user.getRole().name()))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
