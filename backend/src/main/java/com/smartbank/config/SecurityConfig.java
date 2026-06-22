package com.smartbank.config;

import com.smartbank.security.CustomUserDetailsService;
import com.smartbank.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * SecurityConfig - Spring Security Configuration
 * 
 * WHY THIS FILE IS NEEDED:
 * This class configures Spring Security for the application. It defines which
 * endpoints are secured, how authentication works, and how CORS is handled.
 * 
 * WHAT THE CODE DOES:
 * - Configures HTTP security with JWT authentication
 * - Sets up CORS for cross-origin requests
 * - Configures stateless session management (no sessions)
 * - Defines which endpoints are public and which are secured
 * - Sets up authentication manager and password encoder
 * - Adds JWT filter to the security chain
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. SecurityFilterChain: Defines the security rules for HTTP requests
 * 2. SessionCreationPolicy.STATELESS: No HTTP sessions, uses JWT tokens
 * 3. permitAll(): Public endpoints (login, register, Swagger)
 * 4. authenticated(): Secured endpoints (require valid JWT)
 * 5. JWT filter added before UsernamePasswordAuthenticationFilter
 * 6. BCryptPasswordEncoder: Encrypts passwords using BCrypt algorithm
 * 7. DaoAuthenticationProvider: Uses UserDetailsService for authentication
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "SecurityConfig configures Spring Security for JWT-based authentication. I set
 * SessionCreationPolicy to STATELESS because we're using JWT tokens instead of
 * sessions. Public endpoints like /api/auth/login and /api/auth/register are
 * permitted without authentication, while all other endpoints require a valid JWT.
 * The JwtAuthenticationFilter is added before the UsernamePasswordAuthenticationFilter
 * to validate tokens before authentication. I use BCryptPasswordEncoder for password
 * encryption, which is a strong hashing algorithm. CORS is configured to allow
 * requests from the frontend URL."
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/", "/index.html", "/static/**", "/assets/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
