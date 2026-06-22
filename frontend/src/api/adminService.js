import api from './axios';

/**
 * AdminService - API calls for admin dashboard operations
 * 
 * WHY THIS FILE IS NEEDED:
 * This service encapsulates all admin-related API calls. It provides
 * a clean interface for admin dashboard operations, keeping API logic
 * separate from components.
 * 
 * WHAT THE CODE DOES:
 * - getDashboardMetrics(): Retrieves system-wide statistics for admin dashboard
 * - Returns total users, accounts, deposits, transfers
 * - Returns lists of all users, accounts, and transactions
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Calls backend API endpoint
 * 2. Axios automatically adds JWT token from interceptor
 * 3. Returns response data
 * 4. Components use this service for admin dashboard
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AdminService encapsulates all admin dashboard API calls. It provides a method
 * to retrieve system-wide metrics including total users, accounts, deposits, and
 * transfers. This separation of concerns keeps the API logic isolated from React
 * components, making the code more maintainable and testable. The service uses
 * the configured Axios instance which automatically handles token management."
 */
export const adminService = {
  getDashboardMetrics: async () => {
    const response = await api.get('/admin/dashboard');
    return response.data;
  },
};
