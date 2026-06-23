import React from 'react';

/**
 * Maintenance Page - Shown when website is under maintenance
 * 
 * WHY THIS FILE IS NEEDED:
 * This component displays a maintenance message when the website is temporarily
 * unavailable for registration or other operations.
 * 
 * WHAT THE CODE DOES:
 * - Displays a professional maintenance message
 * - Shows estimated downtime (optional)
 * - Provides contact information if needed
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The Maintenance component is shown when the website is under maintenance.
 * It displays a clean, professional message informing users that the site is
 * temporarily unavailable. This is useful during updates or when registration
 * needs to be disabled temporarily."
 */

function Maintenance() {
  return (
    <div className="banking-bg d-flex align-items-center justify-content-center min-vh-100">
      <div className="container">
        <div className="row justify-content-center">
          <div className="col-md-6">
            <div className="card text-center">
              <div className="card-body p-5">
                <div className="mb-4">
                  <span style={{ fontSize: '5rem' }}>🔧</span>
                </div>
                <h1 className="card-title mb-4">Under Maintenance</h1>
                <p className="card-text lead mb-4">
                  We're currently performing scheduled maintenance to improve our services.
                  Registration and other features will be temporarily unavailable.
                </p>
                <p className="card-text text-muted mb-4">
                  We apologize for any inconvenience and appreciate your patience.
                  We'll be back shortly!
                </p>
                <div className="alert alert-info">
                  <strong>📅 Expected Completion:</strong> Soon
                </div>
                <p className="card-text text-muted small">
                  For urgent inquiries, please contact support at support@smartbank-pro.com
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Maintenance;
