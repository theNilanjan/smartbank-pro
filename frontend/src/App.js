import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { authService } from './api/authService';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import AdminDashboard from './pages/AdminDashboard';

/**
 * App.js - Main application component with routing
 * 
 * WHY THIS FILE IS NEEDED:
 * This is the root component that sets up React Router for navigation.
 * It defines all the routes in the application and handles protected routes.
 * 
 * WHAT THE CODE DOES:
 * - Sets up React Router with BrowserRouter
 * - Defines public routes (login, register)
 * - Defines protected routes (dashboard, admin dashboard)
 * - PrivateRoute component checks authentication
 * - Redirects unauthenticated users to login
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Router wraps the entire application
 * 2. Routes define URL path to component mapping
 * 3. PrivateRoute checks if user is authenticated
 * 4. If not authenticated, redirects to login
 * 5. If authenticated, renders the protected component
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "App.js is the root component that sets up React Router for navigation. I defined
 * public routes for login and register, and protected routes for the dashboard and
 * admin dashboard. The PrivateRoute component checks if the user is authenticated
 * using the authService. If not authenticated, it redirects to the login page.
 * This ensures that only authenticated users can access protected pages. React
 * Router provides client-side routing, making the application feel like a single-page
 * application."
 */

// Private Route component to protect routes
const PrivateRoute = ({ children }) => {
  return authService.isAuthenticated() ? children : <Navigate to="/login" />;
};

// Admin Route component to protect admin routes
const AdminRoute = ({ children }) => {
  const user = authService.getCurrentUser();
  const isAdmin = user && user.role === 'ADMIN';
  return isAdmin ? children : <Navigate to="/dashboard" />;
};

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        />
        <Route
          path="/admin"
          element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
        />
        <Route path="/" element={<Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;
