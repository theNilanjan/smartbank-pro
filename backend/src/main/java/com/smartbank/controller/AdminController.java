package com.smartbank.controller;

import com.smartbank.dto.AdminDashboardResponse;
import com.smartbank.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController - REST API controller for admin dashboard endpoints
 * 
 * WHY THIS FILE IS NEEDED:
 * Controllers expose REST API endpoints that clients can call. This controller
 * handles admin-specific endpoints for dashboard metrics and system monitoring.
 * 
 * WHAT THE CODE DOES:
 * - @RestController: Marks this as a REST controller
 * - @RequestMapping: Base path for all endpoints (/api/admin)
 * - getDashboardMetrics(): GET endpoint to retrieve dashboard statistics
 * - @PreAuthorize: Restricts access to ADMIN role only
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Client sends HTTP GET request with JWT token in Authorization header
 * 2. JwtAuthenticationFilter validates token and sets authentication
 * 3. @PreAuthorize checks if user has ADMIN role
 * 4. If not admin, returns 403 Forbidden
 * 5. If admin, calls AdminService
 * 6. Service aggregates data from repositories
 * 7. Returns AdminDashboardResponse
 * 8. Spring converts to JSON
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AdminController exposes REST API endpoints for admin operations. The
 * @PreAuthorize('hasRole(\"ADMIN\")') annotation ensures only users with ADMIN
 * role can access these endpoints. This provides role-based access control at
 * the controller level. The controller delegates to AdminService, which aggregates
 * data from multiple repositories to provide dashboard metrics. This separation
 * keeps the controller focused on HTTP handling while the service contains
 * business logic for data aggregation."
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin Dashboard", description = "Admin Dashboard APIs")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get dashboard metrics", description = "Retrieves system-wide statistics for admin dashboard (admin only)")
    public ResponseEntity<AdminDashboardResponse> getDashboardMetrics() {
        AdminDashboardResponse response = adminService.getDashboardMetrics();
        return ResponseEntity.ok(response);
    }
}
