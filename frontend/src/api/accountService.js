import api from './axios';

/**
 * AccountService - API calls for account management operations
 * 
 * WHY THIS FILE IS NEEDED:
 * This service encapsulates all account-related API calls. It provides
 * a clean interface for account operations, keeping API logic separate
 * from components.
 * 
 * WHAT THE CODE DOES:
 * - createAccount(): Creates a new bank account
 * - getAccountById(): Retrieves account by ID
 * - getUserAccounts(): Retrieves all accounts for a user
 * - freezeAccount(): Freezes an account (admin only)
 * - activateAccount(): Activates a frozen account (admin only)
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Calls backend API endpoints
 * 2. Axios automatically adds JWT token from interceptor
 * 3. Returns response data
 * 4. Components use this service for account operations
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AccountService encapsulates all account management API calls. It provides
 * methods for creating accounts, retrieving account details, and managing account
 * status. This separation of concerns keeps the API logic isolated from React
 * components, making the code more maintainable and testable. The service uses
 * the configured Axios instance which automatically handles token management and
 * error handling."
 */
export const accountService = {
  createAccount: async (accountType) => {
    const response = await api.post('/accounts', { accountType });
    return response.data;
  },

  getAccountById: async (accountId) => {
    const response = await api.get(`/accounts/${accountId}`);
    return response.data;
  },

  getAccountByNumber: async (accountNumber) => {
    const response = await api.get(`/accounts/number/${accountNumber}`);
    return response.data;
  },

  getUserAccounts: async (userId) => {
    const response = await api.get(`/accounts/user/${userId}`);
    return response.data;
  },

  freezeAccount: async (accountId) => {
    const response = await api.put(`/accounts/${accountId}/freeze`);
    return response.data;
  },

  activateAccount: async (accountId) => {
    const response = await api.put(`/accounts/${accountId}/activate`);
    return response.data;
  },
};
