import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../api/authService';

/**
 * Register Page - User registration page
 * 
 * WHY THIS FILE IS NEEDED:
 * This component provides the registration interface for new users.
 * It handles form submission, API calls, and error handling.
 * 
 * WHAT THE CODE DOES:
 * - Renders registration form with name, email, password fields
 * - Handles form submission
 * - Calls authService.register API
 * - Stores JWT token on successful registration
 * - Redirects to dashboard on success
 * - Displays error messages on failure
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. useState manages form state (fullName, email, password, error)
 * 2. Form submission calls authService.register
 * 3. On success, stores token and redirects
 * 4. On failure, displays error message
 * 5. Bootstrap provides styling
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Register component uses React hooks (useState) to manage form state. When
 * the user submits the form, it calls the authService.register method which sends
 * the registration data to the backend. On successful registration, the JWT token
 * is stored in localStorage and the user is redirected to the dashboard. Error
 * messages are displayed if registration fails (e.g., duplicate email). The
 * component uses Bootstrap for styling to ensure a professional appearance."
 */
function Register() {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('CUSTOMER');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await authService.register(fullName, email, password, role);
      navigate('/dashboard');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="banking-bg">
      <div className="container">
        <div className="row justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
          <div className="col-md-6 col-lg-4">
            <div className="card">
              <div className="card-header text-center">
                <h4>🏦 SmartBank Pro - Register</h4>
              </div>
              <div className="card-body">
                {error && <div className="alert alert-danger">{error}</div>}
                <form onSubmit={handleSubmit}>
                  <div className="mb-3">
                    <label htmlFor="fullName" className="form-label">
                      Full Name
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      id="fullName"
                      value={fullName}
                      onChange={(e) => setFullName(e.target.value)}
                      required
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                      Email
                    </label>
                    <input
                      type="email"
                      className="form-control"
                      id="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      required
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                      Password
                    </label>
                    <input
                      type="password"
                      className="form-control"
                      id="password"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      required
                      minLength="6"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="role" className="form-label">
                      Role
                    </label>
                    <select
                      className="form-select"
                      id="role"
                      value={role}
                      onChange={(e) => setRole(e.target.value)}
                      required
                    >
                      <option value="CUSTOMER">Customer</option>
                      <option value="ADMIN">Admin</option>
                    </select>
                    <small className="form-text text-muted">
                      Select "Admin" only if you need administrative access
                    </small>
                  </div>
                  <button
                    type="submit"
                    className="btn btn-primary w-100"
                    disabled={loading}
                  >
                    {loading ? 'Registering...' : 'Register'}
                  </button>
                </form>
                <p className="mt-3 text-center">
                  Already have an account? <Link to="/login">Login</Link>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="developer-badge">Developed by Nilanjan Ghosh</div>
    </div>
  );
}

export default Register;
