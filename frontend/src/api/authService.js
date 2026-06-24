import api from './axios';

/**
 * AuthService - API calls for authentication operations
 * 
 * WHY THIS FILE IS NEEDED:
 * This service encapsulates all authentication-related API calls. It provides
 * a clean interface for login and register operations, keeping API logic
 * separate from components.
 * 
 * WHAT THE CODE DOES:
 * - login(): Authenticates user with email and password
 * - register(): Registers a new user
 * - Returns JWT token and user data
 * - Stores token in localStorage
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Calls backend API endpoints
 * 2. Axios automatically adds JWT token from interceptor
 * 3. Returns response data
 * 4. Components use this service for authentication
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "AuthService encapsulates all authentication API calls. The login method
 * sends credentials to the backend and receives a JWT token. The register
 * method creates a new user account. This separation of concerns keeps the
 * API logic isolated from React components, making the code more maintainable
 * and testable. The service uses the configured Axios instance which automatically
 * handles token management."
 */
export const authService = {
  login: async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
  },

  register: async (fullName, email, password, role = 'CUSTOMER') => {
    const response = await api.post('/auth/register', { fullName, email, password, role });
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  getCurrentUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },
};
