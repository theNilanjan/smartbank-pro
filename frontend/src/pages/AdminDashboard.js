import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../api/authService';
import { adminService } from '../api/adminService';
import { accountService } from '../api/accountService';

/**
 * Admin Dashboard Page - Admin dashboard for system management
 * 
 * WHY THIS FILE IS NEEDED:
 * This component provides the admin dashboard for administrators to monitor
 * the banking system, view all users, accounts, and transactions, and manage
 * account status.
 * 
 * WHAT THE CODE DOES:
 * - Displays system-wide metrics (total users, accounts, deposits, transfers)
 * - Lists all users, accounts, and transactions
 * - Provides account freeze/activate functionality
 * - Handles logout
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. useEffect loads dashboard metrics on component mount
 * 2. useState manages dashboard data and form state
 * 3. API calls to adminService and accountService
 * 4. Bootstrap provides responsive layout
 * 5. Tabs organize different views
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Admin Dashboard component provides system-wide visibility for administrators.
 * It uses useEffect to load dashboard metrics when the component mounts. The component
 * displays aggregated statistics like total users, accounts, deposits, and transfers.
 * It also provides lists of all users, accounts, and transactions. Admins can freeze
 * or activate accounts directly from the dashboard. The component uses Bootstrap for
 * a responsive grid layout and professional styling. Role-based access control ensures
 * only admins can access this page."
 */
function AdminDashboard() {
  const [dashboardData, setDashboardData] = useState(null);
  const [activeTab, setActiveTab] = useState('overview');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const user = authService.getCurrentUser();
  const [showSplash, setShowSplash] = useState(true);
  const [navbarOpen, setNavbarOpen] = useState(false);

  useEffect(() => {
    loadDashboardData();
    // Hide splash screen after 3 seconds
    const timer = setTimeout(() => {
      setShowSplash(false);
    }, 3000);
    return () => clearTimeout(timer);
  }, []);

  // Get greeting based on time of day
  const getGreeting = () => {
    const hour = new Date().getHours();
    if (hour < 12) return '🌅 Good Morning';
    if (hour < 18) return '☀️ Good Afternoon';
    return '🌙 Good Evening';
  };

  const loadDashboardData = async () => {
    try {
      const data = await adminService.getDashboardMetrics();
      setDashboardData(data);
    } catch (err) {
      setError('Failed to load dashboard data');
    }
  };

  const handleFreezeAccount = async (accountId) => {
    try {
      await accountService.freezeAccount(accountId);
      setSuccess('Account frozen successfully');
      loadDashboardData();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to freeze account');
    }
  };

  const handleActivateAccount = async (accountId) => {
    try {
      await accountService.activateAccount(accountId);
      setSuccess('Account activated successfully');
      loadDashboardData();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to activate account');
    }
  };

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  if (!dashboardData) {
    return <div className="container mt-5">Loading...</div>;
  }

  return (
    <>
      {showSplash && (
        <div className="splash-screen">
          <div className="splash-content">
            <div className="splash-emoji">🏦</div>
            <div className="splash-greeting">{getGreeting()}, Admin {user.fullName}!</div>
            <div className="splash-subtitle">Welcome to SmartBank Pro Admin Dashboard 👋</div>
          </div>
        </div>
      )}
      <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
          <div className="container-fluid">
            <span className="navbar-brand">
              <span className="me-2">🏦</span>
              <span className="fw-bold">SmartBank Pro - Admin</span>
            </span>
            <button 
              className="navbar-toggler" 
              type="button" 
              onClick={() => setNavbarOpen(!navbarOpen)}
              aria-expanded={navbarOpen}
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className={`collapse navbar-collapse ${navbarOpen ? 'show' : ''}`} id="navbarNav">
              <ul className="navbar-nav me-auto">
                <li className="nav-item">
                  <span className="nav-link">
                    <span className="me-1">👤</span>
                    Admin: {user.fullName}
                  </span>
                </li>
                <li className="nav-item d-md-none">
                  <span className="nav-link">
                    <span className="me-1">📧</span>
                    {user.email}
                  </span>
                </li>
                <li className="nav-item d-md-none">
                  <span className="nav-link">
                    <span className="me-1">🛡️</span>
                    Administrator
                  </span>
                </li>
              </ul>
              <ul className="navbar-nav ms-auto">
                <li className="nav-item greeting-animation">
                  <span className="nav-link d-none d-md-block">{getGreeting()}! 👋</span>
                </li>
                <li className="nav-item">
                  <button className="btn btn-outline-light btn-sm ms-2" onClick={handleLogout}>
                    <span className="me-1">🚪</span>
                    Logout
                  </button>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <div className="row">
          <div className="col-md-3">
          <div className="card">
            <div className="card-header bg-dark text-white">
              <h5 className="mb-0">Admin Menu</h5>
            </div>
            <div className="card-body p-0">
              <div className="list-group list-group-flush">
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'overview' ? 'active' : ''}`}
                  onClick={() => setActiveTab('overview')}
                >
                  Overview
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'users' ? 'active' : ''}`}
                  onClick={() => setActiveTab('users')}
                >
                  Users
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'accounts' ? 'active' : ''}`}
                  onClick={() => setActiveTab('accounts')}
                >
                  Accounts
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'transactions' ? 'active' : ''}`}
                  onClick={() => setActiveTab('transactions')}
                >
                  Transactions
                </button>
              </div>
            </div>
          </div>
        </div>

        <div className="col-md-9">
          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          {activeTab === 'overview' && (
            <div>
              <div className="row mb-4">
                <div className="col-md-3">
                  <div className="card text-white bg-primary">
                    <div className="card-body">
                      <h5 className="card-title">Total Users</h5>
                      <h2 className="card-text">{dashboardData.totalUsers}</h2>
                    </div>
                  </div>
                </div>
                <div className="col-md-3">
                  <div className="card text-white bg-success">
                    <div className="card-body">
                      <h5 className="card-title">Total Accounts</h5>
                      <h2 className="card-text">{dashboardData.totalAccounts}</h2>
                    </div>
                  </div>
                </div>
                <div className="col-md-3">
                  <div className="card text-white bg-info">
                    <div className="card-body">
                      <h5 className="card-title">Total Deposits</h5>
                      <h2 className="card-text">${dashboardData.totalDeposits.toFixed(2)}</h2>
                    </div>
                  </div>
                </div>
                <div className="col-md-3">
                  <div className="card text-white bg-warning">
                    <div className="card-body">
                      <h5 className="card-title">Total Transfers</h5>
                      <h2 className="card-text">${dashboardData.totalTransfers.toFixed(2)}</h2>
                    </div>
                  </div>
                </div>
              </div>

              <div className="card">
                <div className="card-header">
                  <h5 className="mb-0">System Overview</h5>
                </div>
                <div className="card-body">
                  <p>Welcome to the SmartBank Pro Admin Dashboard. Use the menu to navigate through different sections.</p>
                  <ul>
                    <li><strong>Users:</strong> {dashboardData.totalUsers} registered users</li>
                    <li><strong>Accounts:</strong> {dashboardData.totalAccounts} bank accounts</li>
                    <li><strong>Total Deposits:</strong> ${dashboardData.totalDeposits.toFixed(2)}</li>
                    <li><strong>Total Transfers:</strong> ${dashboardData.totalTransfers.toFixed(2)}</li>
                  </ul>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'users' && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">All Users</h5>
              </div>
              <div className="card-body">
                <div className="table-responsive">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                      </tr>
                    </thead>
                    <tbody>
                      {dashboardData.users.map((user) => (
                        <tr key={user.id}>
                          <td>{user.id}</td>
                          <td>{user.fullName}</td>
                          <td>{user.email}</td>
                          <td>{user.role}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'accounts' && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">All Accounts</h5>
              </div>
              <div className="card-body">
                <div className="table-responsive">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Account Number</th>
                        <th>Type</th>
                        <th>Balance</th>
                        <th>Status</th>
                        <th>User Email</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {dashboardData.accounts.map((account) => (
                        <tr key={account.id}>
                          <td>{account.id}</td>
                          <td>{account.accountNumber}</td>
                          <td>{account.accountType}</td>
                          <td>${account.balance.toFixed(2)}</td>
                          <td>
                            <span className={`badge ${account.status === 'ACTIVE' ? 'bg-success' : 'bg-danger'}`}>
                              {account.status}
                            </span>
                          </td>
                          <td>{account.userEmail}</td>
                          <td>
                            {account.status === 'ACTIVE' ? (
                              <button
                                className="btn btn-sm btn-danger"
                                onClick={() => handleFreezeAccount(account.id)}
                              >
                                Freeze
                              </button>
                            ) : (
                              <button
                                className="btn btn-sm btn-success"
                                onClick={() => handleActivateAccount(account.id)}
                              >
                                Activate
                              </button>
                            )}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'transactions' && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">All Transactions</h5>
              </div>
              <div className="card-body">
                <div className="table-responsive">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Account Number</th>
                      </tr>
                    </thead>
                    <tbody>
                      {dashboardData.transactions.map((transaction) => (
                        <tr key={transaction.id}>
                          <td>{transaction.id}</td>
                          <td>{transaction.transactionType}</td>
                          <td>₹{transaction.amount.toFixed(2)}</td>
                          <td>{transaction.description}</td>
                          <td>{transaction.accountNumber}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
      <div className="developer-badge">Developed by Nilanjan Ghosh</div>
      </div>
    </>
  );
}

export default AdminDashboard;
