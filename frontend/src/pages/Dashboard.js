import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../api/authService';
import { accountService } from '../api/accountService';
import { transactionService } from '../api/transactionService';

/**
 * Dashboard Page - Customer dashboard for banking operations
 * 
 * WHY THIS FILE IS NEEDED:
 * This component provides the main dashboard for customers to manage their
 * accounts and perform banking operations like deposit, withdraw, and transfer.
 * 
 * WHAT THE CODE DOES:
 * - Displays user's accounts with balances
 * - Provides deposit, withdraw, and transfer functionality
 * - Shows transaction history
 * - Allows account creation
 * - Handles logout
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. useEffect loads user accounts on component mount
 * 2. useState manages accounts, transactions, and form state
 * 3. API calls to accountService and transactionService
 * 4. Bootstrap provides responsive layout
 * 5. Tabs organize different operations
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Dashboard component is the main interface for customers. It uses useEffect
 * to load the user's accounts when the component mounts. The component manages
 * state for accounts, transactions, and form inputs using useState. It provides
 * tabs for different operations: deposit, withdraw, transfer, and transaction
 * history. Each operation calls the corresponding service method which makes API
 * calls to the backend. The component uses Bootstrap for a responsive grid layout
 * and professional styling."
 */
function Dashboard() {
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState(null);
  const [activeTab, setActiveTab] = useState('accounts');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  // Form states
  const [accountType, setAccountType] = useState('SAVINGS');
  const [depositAmount, setDepositAmount] = useState('');
  const [withdrawAmount, setWithdrawAmount] = useState('');
  const [transferToAccount, setTransferToAccount] = useState('');
  const [transferAmount, setTransferAmount] = useState('');

  const user = authService.getCurrentUser();
  const [showSplash, setShowSplash] = useState(true);
  const [navbarOpen, setNavbarOpen] = useState(false);

  const loadAccounts = useCallback(async () => {
    try {
      const data = await accountService.getUserAccounts(user.id);
      setAccounts(data);
      if (data.length > 0) {
        setSelectedAccount(data[0]);
        loadTransactions(data[0].id);
      }
    } catch (err) {
      setError('Failed to load accounts');
    }
  }, [user.id]);

  useEffect(() => {
    loadAccounts();
    // Hide splash screen after 3 seconds
    const timer = setTimeout(() => {
      setShowSplash(false);
    }, 3000);
    return () => clearTimeout(timer);
  }, [loadAccounts]);

  // Get greeting based on time of day
  const getGreeting = () => {
    const hour = new Date().getHours();
    if (hour < 12) return '🌅 Good Morning';
    if (hour < 18) return '☀️ Good Afternoon';
    return '🌙 Good Evening';
  };

  const loadTransactions = async (accountId) => {
    try {
      const data = await transactionService.getTransactionHistory(accountId);
      setTransactions(data);
    } catch (err) {
      setError('Failed to load transactions');
    }
  };

  const handleCreateAccount = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      await accountService.createAccount(accountType);
      setSuccess('Account created successfully');
      loadAccounts();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create account');
    }
  };

  const handleDeposit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      await transactionService.deposit(selectedAccount.id, parseFloat(depositAmount));
      setSuccess('Deposit successful');
      setDepositAmount('');
      loadAccounts();
      loadTransactions(selectedAccount.id);
    } catch (err) {
      setError(err.response?.data?.message || 'Deposit failed');
    }
  };

  const handleWithdraw = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      await transactionService.withdraw(selectedAccount.id, parseFloat(withdrawAmount));
      setSuccess('Withdrawal successful');
      setWithdrawAmount('');
      loadAccounts();
      loadTransactions(selectedAccount.id);
    } catch (err) {
      setError(err.response?.data?.message || 'Withdrawal failed');
    }
  };

  const handleTransfer = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    try {
      await transactionService.transfer(
        selectedAccount.id,
        transferToAccount,
        parseFloat(transferAmount)
      );
      setSuccess('Transfer successful');
      setTransferToAccount('');
      setTransferAmount('');
      loadAccounts();
      loadTransactions(selectedAccount.id);
    } catch (err) {
      setError(err.response?.data?.message || 'Transfer failed');
    }
  };

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  return (
    <>
      {showSplash && (
        <div className="splash-screen">
          <div className="splash-content">
            <div className="splash-emoji">🏦</div>
            <div className="splash-greeting">{getGreeting()}, {user.fullName}!</div>
            <div className="splash-subtitle">Welcome to SmartBank Pro 👋</div>
          </div>
        </div>
      )}
      <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
          <div className="container-fluid">
            <span className="navbar-brand">
              <span className="me-2">🏦</span>
              <span className="fw-bold">SmartBank Pro</span>
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
                    {user.fullName}
                  </span>
                </li>
                <li className="nav-item d-md-none">
                  <span className="nav-link">
                    <span className="me-1">📧</span>
                    {user.email}
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
            <div className="card-header">
              <h5 className="mb-0">Menu</h5>
            </div>
            <div className="card-body p-0">
              <div className="list-group list-group-flush">
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'accounts' ? 'active' : ''}`}
                  onClick={() => setActiveTab('accounts')}
                >
                  My Accounts
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'deposit' ? 'active' : ''}`}
                  onClick={() => setActiveTab('deposit')}
                >
                  Deposit
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'withdraw' ? 'active' : ''}`}
                  onClick={() => setActiveTab('withdraw')}
                >
                  Withdraw
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'transfer' ? 'active' : ''}`}
                  onClick={() => setActiveTab('transfer')}
                >
                  Transfer
                </button>
                <button
                  className={`list-group-item list-group-item-action ${activeTab === 'history' ? 'active' : ''}`}
                  onClick={() => setActiveTab('history')}
                >
                  Transaction History
                </button>
              </div>
            </div>
          </div>
        </div>

        <div className="col-md-9">
          {error && <div className="alert alert-danger">{error}</div>}
          {success && <div className="alert alert-success">{success}</div>}

          {activeTab === 'accounts' && (
            <div>
              <div className="card mb-4">
                <div className="card-header">
                  <h5 className="mb-0">My Accounts</h5>
                </div>
                <div className="card-body">
                  {accounts.length === 0 ? (
                    <p>No accounts found. Create your first account below.</p>
                  ) : (
                    <div className="row">
                      {accounts.map((account) => (
                        <div key={account.id} className="col-md-6 mb-3">
                          <div className="card balance-card">
                            <div className="card-body">
                              <h6 className="card-subtitle mb-2">{account.accountType}</h6>
                              <p className="card-text">Account Number: {account.accountNumber}</p>
                              <h3 className="balance-amount">₹{account.balance.toFixed(2)}</h3>
                              <p className="card-text">Status: {account.status}</p>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>

              <div className="card">
                <div className="card-header">
                  <h5 className="mb-0">Create New Account</h5>
                </div>
                <div className="card-body">
                  <form onSubmit={handleCreateAccount}>
                    <div className="mb-3">
                      <label className="form-label">Account Type</label>
                      <select
                        className="form-select"
                        value={accountType}
                        onChange={(e) => setAccountType(e.target.value)}
                      >
                        <option value="SAVINGS">Savings Account</option>
                        <option value="CURRENT">Current Account</option>
                      </select>
                    </div>
                    <button type="submit" className="btn btn-primary">
                      Create Account
                    </button>
                  </form>
                </div>
              </div>
            </div>
          )}

          {activeTab === 'deposit' && selectedAccount && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">Deposit Money</h5>
              </div>
              <div className="card-body">
                <p className="mb-3">Selected Account: {selectedAccount.accountNumber}</p>
                <form onSubmit={handleDeposit}>
                  <div className="mb-3">
                    <label className="form-label">Amount</label>
                    <input
                      type="number"
                      className="form-control"
                      value={depositAmount}
                      onChange={(e) => setDepositAmount(e.target.value)}
                      min="0.01"
                      step="0.01"
                      required
                    />
                  </div>
                  <button type="submit" className="btn btn-primary">
                    Deposit
                  </button>
                </form>
              </div>
            </div>
          )}

          {activeTab === 'withdraw' && selectedAccount && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">Withdraw Money</h5>
              </div>
              <div className="card-body">
                <p className="mb-3">Selected Account: {selectedAccount.accountNumber}</p>
                <p className="mb-3">Available Balance: ₹{selectedAccount.balance.toFixed(2)}</p>
                <form onSubmit={handleWithdraw}>
                  <div className="mb-3">
                    <label className="form-label">Amount</label>
                    <input
                      type="number"
                      className="form-control"
                      value={withdrawAmount}
                      onChange={(e) => setWithdrawAmount(e.target.value)}
                      min="0.01"
                      step="0.01"
                      max={selectedAccount.balance}
                      required
                    />
                  </div>
                  <button type="submit" className="btn btn-primary">
                    Withdraw
                  </button>
                </form>
              </div>
            </div>
          )}

          {activeTab === 'transfer' && selectedAccount && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">Transfer Money</h5>
              </div>
              <div className="card-body">
                <p className="mb-3">From Account: {selectedAccount.accountNumber}</p>
                <p className="mb-3">Available Balance: ₹{selectedAccount.balance.toFixed(2)}</p>
                <form onSubmit={handleTransfer}>
                  <div className="mb-3">
                    <label className="form-label">To Account Number</label>
                    <input
                      type="text"
                      className="form-control"
                      value={transferToAccount}
                      onChange={(e) => setTransferToAccount(e.target.value)}
                      required
                    />
                  </div>
                  <div className="mb-3">
                    <label className="form-label">Amount</label>
                    <input
                      type="number"
                      className="form-control"
                      value={transferAmount}
                      onChange={(e) => setTransferAmount(e.target.value)}
                      min="0.01"
                      step="0.01"
                      max={selectedAccount.balance}
                      required
                    />
                  </div>
                  <button type="submit" className="btn btn-primary">
                    Transfer
                  </button>
                </form>
              </div>
            </div>
          )}

          {activeTab === 'history' && (
            <div className="card">
              <div className="card-header">
                <h5 className="mb-0">Transaction History</h5>
              </div>
              <div className="card-body">
                {transactions.length === 0 ? (
                  <p>No transactions found.</p>
                ) : (
                  <div className="table-responsive">
                    <table className="table">
                      <thead>
                        <tr>
                          <th>Type</th>
                          <th>Amount</th>
                          <th>Description</th>
                          <th>Date</th>
                        </tr>
                      </thead>
                      <tbody>
                        {transactions.map((transaction) => (
                          <tr key={transaction.id}>
                            <td className={`transaction-type-${transaction.transactionType.toLowerCase()}`}>
                              {transaction.transactionType}
                            </td>
                            <td>₹{transaction.amount.toFixed(2)}</td>
                            <td>{transaction.description}</td>
                            <td>{new Date(transaction.transactionDate).toLocaleString()}</td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                )}
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

export default Dashboard;
