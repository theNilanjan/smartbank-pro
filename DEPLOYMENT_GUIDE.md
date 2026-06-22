# SmartBank Pro - Deployment Guide

This guide provides step-by-step instructions for deploying SmartBank Pro to production using Render (backend) and Vercel (frontend).

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Backend Deployment (Render)](#backend-deployment-render)
3. [Frontend Deployment (Vercel)](#frontend-deployment-vercel)
4. [Troubleshooting](#troubleshooting)

## Prerequisites

- GitHub account with repository containing the project
- Render account (free tier available)
- Vercel account (free tier available)
- MySQL database (can use Render's MySQL or external provider)

## Backend Deployment (Render)

### Step 1: Prepare Database

#### Option A: Use Render's Managed PostgreSQL/MySQL
1. Go to Render dashboard
2. Click "New +" → "Database"
3. Choose PostgreSQL or MySQL
4. Name: `smartbank-pro-db`
5. Click "Create Database"
6. Wait for database to be ready
7. Copy the Internal Database URL

#### Option B: Use External MySQL
1. Create MySQL database on your preferred provider
2. Note the connection URL, username, and password

### Step 2: Configure Environment Variables

Update `application.properties` to use environment variables:

```properties
# Server Configuration
server.port=${PORT:8080}
spring.application.name=smartbank-pro

# MySQL Database Configuration
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION:86400000}

# CORS Configuration
cors.allowed-origins=${FRONTEND_URL}
```

### Step 3: Create Render Web Service

1. Go to Render dashboard
2. Click "New +" → "Web Service"
3. Connect your GitHub repository
4. Select the `backend` folder or configure root directory
5. Configure build settings:
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/smartbank-pro-1.0.0.jar`
   - **Runtime**: Docker (recommended) or Java
6. Add Environment Variables:
   - `DATABASE_URL`: Your MySQL connection URL
   - `DATABASE_USERNAME`: Your MySQL username
   - `DATABASE_PASSWORD`: Your MySQL password
   - `JWT_SECRET`: Generate a strong secret key (use: `openssl rand -base64 32`)
   - `JWT_EXPIRATION`: `86400000` (24 hours)
   - `FRONTEND_URL`: Your Vercel frontend URL (e.g., `https://smartbank-pro.vercel.app`)
7. Click "Create Web Service"
8. Wait for deployment to complete

### Step 4: Verify Deployment

1. Check the Render dashboard for deployment status
2. Click the deployed URL to access the backend
3. Test Swagger UI: `https://your-backend-url.onrender.com/swagger-ui.html`

## Frontend Deployment (Vercel)

### Step 1: Update API Configuration

Update `src/api/axios.js` to use environment variable:

```javascript
const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});
```

### Step 2: Create Vercel Project

1. Go to Vercel dashboard
2. Click "Add New..." → "Project"
3. Import your GitHub repository
4. Configure project settings:
   - **Framework Preset**: Create React App
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`
5. Add Environment Variables:
   - `REACT_APP_API_URL`: Your Render backend URL (e.g., `https://smartbank-pro-backend.onrender.com/api`)
6. Click "Deploy"
7. Wait for deployment to complete

### Step 3: Update CORS Configuration

1. Go to your Render backend service
2. Update `FRONTEND_URL` environment variable with your Vercel URL
3. Trigger a new deployment on Render

### Step 4: Verify Deployment

1. Click the deployed Vercel URL
2. Test registration and login
3. Verify API calls are working

## Troubleshooting

### Backend Issues

#### Issue: Database Connection Failed
**Solution**: 
- Verify DATABASE_URL is correct
- Check if database is accessible from Render
- Ensure database allows remote connections
- Check firewall settings

#### Issue: JWT Token Invalid
**Solution**:
- Ensure JWT_SECRET is set and consistent
- Check token expiration time
- Verify token is being sent in Authorization header

#### Issue: CORS Errors
**Solution**:
- Verify FRONTEND_URL environment variable is correct
- Check SecurityConfig CORS configuration
- Ensure frontend URL is in allowed origins

#### Issue: Build Fails
**Solution**:
- Check Maven version compatibility
- Verify pom.xml dependencies
- Check Java version (should be 17)
- Review build logs for specific errors

### Frontend Issues

#### Issue: API Calls Failing
**Solution**:
- Verify REACT_APP_API_URL is set correctly
- Check browser console for errors
- Verify backend is running and accessible
- Check CORS configuration on backend

#### Issue: Build Fails
**Solution**:
- Check Node.js version compatibility
- Verify package.json dependencies
- Review build logs for specific errors
- Ensure all dependencies are installed

#### Issue: White Screen After Deployment
**Solution**:
- Check browser console for errors
- Verify build output directory
- Check environment variables
- Review Vercel deployment logs

### Common Issues

#### Issue: Port Already in Use
**Solution**:
- Ensure backend uses PORT environment variable
- Check for conflicting services
- Render automatically assigns PORT

#### Issue: Memory Issues
**Solution**:
- Upgrade to paid Render plan for more memory
- Optimize database queries
- Implement caching
- Use connection pooling

#### Issue: Slow Performance
**Solution**:
- Implement database indexing
- Use lazy loading for JPA relationships
- Add caching layer
- Optimize frontend bundle size

## Production Best Practices

### Backend
1. **Security**:
   - Use strong JWT secrets
   - Enable HTTPS (automatic on Render)
   - Implement rate limiting
   - Use environment variables for sensitive data

2. **Performance**:
   - Enable connection pooling
   - Implement caching
   - Use database indexes
   - Optimize queries

3. **Monitoring**:
   - Enable logging
   - Set up error tracking (Sentry)
   - Monitor application metrics
   - Set up alerts

### Frontend
1. **Performance**:
   - Enable code splitting
   - Optimize images
   - Use lazy loading
   - Minimize bundle size

2. **Security**:
   - Use httpOnly cookies for tokens in production
   - Implement CSP headers
   - Enable HTTPS (automatic on Vercel)
   - Sanitize user input

3. **SEO**:
   - Add meta tags
   - Implement proper routing
   - Use semantic HTML
   - Add sitemap

## Scaling Considerations

### When to Scale
- High traffic (1000+ concurrent users)
- Slow response times
- Database connection limits
- Memory/CPU usage > 80%

### Scaling Options
1. **Backend**:
   - Add more instances (Render supports auto-scaling)
   - Use load balancer
   - Implement caching (Redis)
   - Use message queue for async operations

2. **Database**:
   - Use read replicas
   - Implement sharding
   - Use connection pooling
   - Optimize queries

3. **Frontend**:
   - Use CDN (Vercel provides this)
   - Implement edge caching
   - Optimize assets
   - Use server-side rendering

## Cost Estimation

### Render (Backend)
- Free Tier: $0/month (limited resources)
- Starter: $7/month (better performance)
- Standard: $25/month (more resources)

### Vercel (Frontend)
- Hobby: $0/month (sufficient for most use cases)
- Pro: $20/month (more bandwidth and features)

### Database
- Render MySQL: Free tier available
- External MySQL: Varies by provider

**Estimated Monthly Cost**: $0-$50 depending on usage and chosen plans

## Maintenance

### Regular Tasks
1. Monitor application logs
2. Check for security vulnerabilities
3. Update dependencies
4. Backup database
5. Review performance metrics

### Updates
1. Backend:
   - Update Spring Boot dependencies
   - Update Java version
   - Update security patches

2. Frontend:
   - Update React dependencies
   - Update npm packages
   - Fix security vulnerabilities

## Support

For issues with:
- **Render**: https://render.com/support
- **Vercel**: https://vercel.com/support
- **Project**: Check GitHub issues or create new one

## Additional Resources

- [Render Documentation](https://render.com/docs)
- [Vercel Documentation](https://vercel.com/docs)
- [Spring Boot Deployment](https://spring.io/guides/topicals/spring-boot-application)
- [React Deployment](https://react.dev/learn/deploying)
