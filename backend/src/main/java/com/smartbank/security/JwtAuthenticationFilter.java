package com.smartbank.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter - Intercepts requests and validates JWT tokens
 * 
 * WHY THIS FILE IS NEEDED:
 * This filter intercepts every HTTP request to validate JWT tokens. It extracts
 * the token from the Authorization header and authenticates the user.
 * 
 * WHAT THE CODE DOES:
 * - Extends OncePerRequestFilter: Ensures filter runs once per request
 * - doFilterInternal(): Main filter logic
 * - extractJwtFromRequest(): Extracts token from Authorization header
 * - Sets authentication in SecurityContext if token is valid
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Every HTTP request passes through this filter
 * 2. Extracts JWT token from "Authorization: Bearer <token>" header
 * 3. If token exists, validates it using JwtUtil
 * 4. If valid, loads user details using CustomUserDetailsService
 * 5. Creates UsernamePasswordAuthenticationToken with user details
 * 6. Sets authentication in SecurityContextHolder
 * 7. Subsequent filters/controllers can access authenticated user
 * 8. If no token or invalid, continues without authentication
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The JwtAuthenticationFilter intercepts every HTTP request to validate JWT tokens.
 * It extends OncePerRequestFilter to ensure it runs once per request. The filter extracts
 * the JWT token from the Authorization header in the format 'Bearer <token>'.
 * If a valid token is found, it validates the token using JwtUtil, loads the user
 * details using CustomUserDetailsService, and sets the authentication in the
 * SecurityContextHolder. This allows subsequent filters and controllers to access
 * the authenticated user's information. Requests without a valid token proceed
 * without authentication."
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
