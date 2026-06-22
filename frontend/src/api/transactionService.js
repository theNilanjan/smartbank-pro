import api from './axios';

/**
 * TransactionService - API calls for banking transaction operations
 * 
 * WHY THIS FILE IS NEEDED:
 * This service encapsulates all transaction-related API calls. It provides
 * a clean interface for transaction operations, keeping API logic separate
 * from components.
 * 
 * WHAT THE CODE DOES:
 * - deposit(): Deposits money to an account
 * - withdraw(): Withdraws money from an account
 * - transfer(): Transfers money between accounts
 * - getTransactionHistory(): Retrieves transaction history for an account
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Calls backend API endpoints
 * 2. Axios automatically adds JWT token from interceptor
 * 3. Returns response data
 * 4. Components use this service for transaction operations
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "TransactionService encapsulates all banking transaction API calls. It provides
 * methods for deposit, withdraw, transfer, and retrieving transaction history.
 * This separation of concerns keeps the API logic isolated from React components,
 * making the code more maintainable and testable. The service uses the configured
 * Axios instance which automatically handles token management and error handling."
 */
export const transactionService = {
  deposit: async (accountId, amount) => {
    const response = await api.post('/transactions/deposit', { accountId, amount });
    return response.data;
  },

  withdraw: async (accountId, amount) => {
    const response = await api.post('/transactions/withdraw', { accountId, amount });
    return response.data;
  },

  transfer: async (fromAccountId, toAccountId, amount) => {
    const response = await api.post('/transactions/transfer', {
      fromAccountId,
      toAccountId,
      amount,
    });
    return response.data;
  },

  getTransactionHistory: async (accountId) => {
    const response = await api.get(`/transactions/history/${accountId}`);
    return response.data;
  },
};
