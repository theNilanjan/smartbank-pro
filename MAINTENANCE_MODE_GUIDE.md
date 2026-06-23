# Maintenance Mode Guide

## Overview
This guide explains how to enable and disable maintenance mode for SmartBank Pro. When maintenance mode is enabled, users will see a maintenance page instead of being able to register or access the application.

## How Maintenance Mode Works
- When enabled: All public routes (login, register, dashboard) show a maintenance page
- When enabled: Admin route (`/admin`) still works so administrators can access the system
- When disabled: Normal application functionality is restored

## How to Enable Maintenance Mode

### Step 1: Edit the Configuration File
Open `frontend/src/config.js` and change:
```javascript
export const MAINTENANCE_MODE = false; // Set to true to enable maintenance mode
```
to:
```javascript
export const MAINTENANCE_MODE = true; // Set to true to enable maintenance mode
```

### Step 2: Build the Frontend
```bash
cd frontend
npm run build
```

### Step 3: Commit and Push to GitHub
```bash
git add .
git commit -m "Enable maintenance mode"
git push origin main
```

### Step 4: Wait for Vercel Deployment
Vercel will automatically detect the changes and redeploy. The maintenance page will be live once deployment completes.

## How to Disable Maintenance Mode

### Step 1: Edit the Configuration File
Open `frontend/src/config.js` and change:
```javascript
export const MAINTENANCE_MODE = true; // Set to true to enable maintenance mode
```
to:
```javascript
export const MAINTENANCE_MODE = false; // Set to true to enable maintenance mode
```

### Step 2: Build the Frontend
```bash
cd frontend
npm run build
```

### Step 3: Commit and Push to GitHub
```bash
git add .
git commit -m "Disable maintenance mode"
git push origin main
```

### Step 4: Wait for Vercel Deployment
Vercel will automatically detect the changes and redeploy. Normal functionality will be restored once deployment completes.

## Important Notes
- Admin users can still access `/admin` even during maintenance mode
- The maintenance page displays a professional message to users
- No changes to the backend are required - this is a frontend-only feature
- Changes take effect after Vercel deployment (usually 1-2 minutes)

## Quick Reference Commands

### Enable Maintenance Mode
```bash
# Edit frontend/src/config.js to set MAINTENANCE_MODE = true
cd frontend
npm run build
cd ..
git add .
git commit -m "Enable maintenance mode"
git push origin main
```

### Disable Maintenance Mode
```bash
# Edit frontend/src/config.js to set MAINTENANCE_MODE = false
cd frontend
npm run build
cd ..
git add .
git commit -m "Disable maintenance mode"
git push origin main
```
