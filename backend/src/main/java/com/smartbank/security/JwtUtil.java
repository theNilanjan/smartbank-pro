package com.smartbank.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtUtil - Utility class for JWT token generation and validation
 * 
 * WHY THIS FILE IS NEEDED:
 * JWT (JSON Web Token) is used for stateless authentication. This utility class
 * handles token creation, validation, and extraction of claims from tokens.
 * 
 * WHAT THE CODE DOES:
 * - generateToken(): Creates a JWT token for a user
 * - extractUsername(): Extracts username (email) from token
 * - validateToken(): Validates if token is valid and not expired
 * - extractClaim(): Helper method to extract specific claims from token
 * - isTokenExpired(): Checks if token has expired
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. JWT Structure: Header.Payload.Signature
 *    - Header: Algorithm and token type
 *    - Payload: Claims (user data, expiration, etc.)
 *    - Signature: Cryptographic signature to verify authenticity
 * 2. Token Generation:
 *    - Creates claims with user details
 *    - Sets expiration time
 *    - Signs with secret key using HS256 algorithm
 * 3. Token Validation:
 *    - Parses token using secret key
 *    - Checks if token is expired
 *    - Verifies username matches
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "JWT is used for stateless authentication in our application. When a user logs in,
 * we generate a JWT token containing user claims like email and role. The token is
 * signed with a secret key using HS256 algorithm. The client sends this token in
 * the Authorization header for subsequent requests. The JwtUtil class handles
 * token generation and validation. It extracts claims like username and expiration
 * date from the token. We use the secret key from application.properties to sign
 * and verify tokens. This eliminates the need for server-side session storage."
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
