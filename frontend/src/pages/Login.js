import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { authService } from '../api/authService';

/**
 * Login Page - User authentication page
 * 
 * WHY THIS FILE IS NEEDED:
 * This component provides the login interface for users to authenticate.
 * It handles form submission, API calls, and error handling.
 * 
 * WHAT THE CODE DOES:
 * - Renders login form with email and password fields
 * - Handles form submission
 * - Calls authService.login API
 * - Stores JWT token on successful login
 * - Redirects to dashboard on success
 * - Displays error messages on failure
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. useState manages form state (email, password, error)
 * 2. Form submission calls authService.login
 * 3. On success, stores token and redirects
 * 4. On failure, displays error message
 * 5. Bootstrap provides styling
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Login component uses React hooks (useState) to manage form state. When the
 * user submits the form, it calls the authService.login method which sends the
 * credentials to the backend. On successful authentication, the JWT token is stored
 * in localStorage and the user is redirected to the dashboard. Error messages
 * are displayed if authentication fails. The component uses Bootstrap for styling
 * to ensure a professional appearance."
 */
function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      await authService.login(email, password);
      const user = authService.getCurrentUser();
      if (user.role === 'ADMIN') {
        navigate('/admin');
      } else {
        navigate('/dashboard');
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Login failed. Please try again.');
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
                <h4>🏦 SmartBank Pro - Login</h4>
              </div>
              <div className="card-body">
                {error && <div className="alert alert-danger">{error}</div>}
                <form onSubmit={handleSubmit}>
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
                    />
                  </div>
                  <button
                    type="submit"
                    className="btn btn-primary w-100"
                    disabled={loading}
                  >
                    {loading ? 'Logging in...' : 'Login'}
                  </button>
                </form>
                <p className="mt-3 text-center">
                  Don't have an account? <Link to="/register">Register</Link>
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

export default Login;
