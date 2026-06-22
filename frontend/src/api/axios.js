import axios from 'axios';

/**
 * Axios Configuration - HTTP client setup
 * 
 * WHY THIS FILE IS NEEDED:
 * This file configures Axios as the HTTP client for API calls. It sets up the
 * base URL, request interceptors for adding JWT tokens, and response interceptors
 * for error handling.
 * 
 * WHAT THE CODE DOES:
 * - Creates Axios instance with base URL
 * - Request interceptor adds JWT token to Authorization header
 * - Response interceptor handles errors globally
 * - Automatically includes token from localStorage
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Every API call goes through this Axios instance
 * 2. Request interceptor adds Bearer token if available in localStorage
 * 3. Response interceptor catches 401 errors and redirects to login
 * 4. Centralized error handling for all API calls
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I configured Axios as the HTTP client for API calls. The request interceptor
 * automatically adds the JWT token from localStorage to the Authorization header
 * for authenticated requests. The response interceptor handles 401 Unauthorized
 * errors by redirecting to the login page. This centralized configuration eliminates
 * the need to manually add tokens to each request and provides consistent error
 * handling across the application."
 */
const api = axios.create({
  baseURL: 'https://smartbank-pro-j74m.onrender.com/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add JWT token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor to handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default api;
