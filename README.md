# SmartBank Pro - Full Stack Banking Application

A production-ready digital banking system built with Java 17, Spring Boot 3, React.js, and MySQL. This application enables customers to create accounts, perform transactions (deposit, withdraw, transfer), and view transaction history. Admins can monitor system-wide metrics and manage accounts.

## 🚀 Features

### Customer Features
- User registration and login with JWT authentication
- Create multiple bank accounts (Savings/Current)
- Auto-generated 16-digit account numbers
- Deposit money to accounts
- Withdraw money from accounts
- Transfer funds between accounts
- View transaction history
- Real-time balance updates

### Admin Features
- System-wide dashboard with metrics
- View all registered users
- View all accounts with details
- View all transactions
- Freeze/activate accounts
- Monitor total deposits and transfers

### Technical Features
- JWT-based stateless authentication
- Role-based access control (CUSTOMER/ADMIN)
- ACID-compliant transactions
- Global exception handling
- Swagger/OpenAPI documentation
- Responsive React frontend with Bootstrap 5
- Axios interceptors for automatic token management
- Protected routes with React Router

## 🛠 Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA (Hibernate)
- Spring Security
- JWT (jjwt 0.12.3)
- MySQL
- Maven
- Swagger/OpenAPI (springdoc-openapi)

### Frontend
- React.js 18.2.0
- React Router DOM 6.20.0
- Axios 1.6.2
- Bootstrap 5.3.2
- Chart.js 4.4.0

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 16+ and npm
- MySQL 8.0+

## 🏗 Project Structure

```
smartbank-pro/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/smartbank/
│   │       │   ├── config/          # Security and Swagger config
│   │       │   ├── controller/      # REST controllers
│   │       │   ├── dto/             # Data Transfer Objects
│   │       │   ├── entity/          # JPA entities
│   │       │   ├── enums/           # Enumerations
│   │       │   ├── exception/       # Custom exceptions
│   │       │   ├── repository/      # JPA repositories
│   │       │   ├── security/        # JWT and security
│   │       │   └── service/         # Business logic
│   │       └── resources/
│   │           └── application.properties
│   └── pom.xml
├── frontend/
│   ├── public/
│   │   └── index.html
│   ├── src/
│   │   ├── api/             # API services
│   │   ├── pages/           # React pages
│   │   ├── App.js
│   │   ├── index.css
│   │   └── index.js
│   └── package.json
└── README.md
```

## 🚀 Getting Started

### Backend Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd smartbank-pro/backend
```

2. **Configure MySQL Database**
```sql
CREATE DATABASE smartbank_pro;
```

3. **Update application.properties**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartbank_pro?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Build and run the backend**
```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

5. **Access Swagger Documentation**
Open `http://localhost:8080/swagger-ui.html` in your browser

### Frontend Setup

1. **Navigate to frontend directory**
```bash
cd frontend
```

2. **Install dependencies**
```bash
npm install
```

3. **Start the development server**
```bash
npm start
```

The frontend will start on `http://localhost:3000`

## 🔐 Default Credentials

After starting the application, register a new user through the registration page. The first user can be promoted to ADMIN role by updating the database:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'your-email@example.com';
```

## 📊 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Accounts
- `POST /api/accounts` - Create new account
- `GET /api/accounts/{id}` - Get account by ID
- `GET /api/accounts/number/{accountNumber}` - Get account by number
- `GET /api/accounts/user/{userId}` - Get user accounts
- `PUT /api/accounts/{id}/freeze` - Freeze account (Admin only)
- `PUT /api/accounts/{id}/activate` - Activate account (Admin only)

### Transactions
- `POST /api/transactions/deposit` - Deposit money
- `POST /api/transactions/withdraw` - Withdraw money
- `POST /api/transactions/transfer` - Transfer funds
- `GET /api/transactions/history/{accountId}` - Get transaction history

### Admin
- `GET /api/admin/dashboard` - Get dashboard metrics (Admin only)

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🚀 Deployment

### Backend Deployment (Render/Railway)

1. **Create a MySQL database** on your cloud provider
2. **Set environment variables**:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `JWT_SECRET`
3. **Deploy** using the platform's CLI or connect your GitHub repository
4. **Build command**: `mvn clean package`
5. **Start command**: `java -jar target/smartbank-pro-1.0.0.jar`

### Frontend Deployment (Vercel)

1. **Update API base URL** in `src/api/axios.js` to your deployed backend URL
2. **Connect your GitHub repository** to Vercel
3. **Configure build settings**:
   - Build command: `npm run build`
   - Output directory: `build`
4. **Deploy**

## 🔧 Environment Variables

### Backend
- `SPRING_DATASOURCE_URL` - Database connection URL
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password
- `JWT_SECRET` - Secret key for JWT token generation
- `JWT_EXPIRATION` - Token expiration time in milliseconds

### Frontend
- `REACT_APP_API_URL` - Backend API URL (for production)

## 📝 Database Schema

### Users Table
- `id` (PK, Long)
- `fullName` (String)
- `email` (String, Unique)
- `password` (String, BCrypt encrypted)
- `role` (Enum: CUSTOMER, ADMIN)
- `createdAt` (DateTime)

### Accounts Table
- `id` (PK, Long)
- `accountNumber` (String, Unique, 16 digits)
- `accountType` (Enum: SAVINGS, CURRENT)
- `balance` (BigDecimal)
- `status` (Enum: ACTIVE, FROZEN, CLOSED)
- `createdAt` (DateTime)
- `user_id` (FK, Long)

### Transactions Table
- `id` (PK, Long)
- `transactionType` (Enum: DEPOSIT, WITHDRAWAL, TRANSFER)
- `amount` (BigDecimal)
- `description` (String)
- `transactionDate` (DateTime)
- `account_id` (FK, Long)
- `from_account_id` (FK, Long, nullable)
- `to_account_id` (FK, Long, nullable)

## 🔒 Security Features

- JWT-based stateless authentication
- BCrypt password encryption
- Role-based access control
- CORS configuration
- Input validation
- SQL injection prevention (JPA/Hibernate)
- XSS protection (React)

## 📚 Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Interview Preparation: See `INTERVIEW_PREPARATION.md`

## 🤝 Contributing

This is a demo project for interview preparation. Feel free to fork and customize it for your needs.

## 📄 License

This project is for educational purposes.

## 👨‍💻 Author

Built as a production-ready full-stack banking application for Java Full Stack interview preparation.

## 🙏 Acknowledgments

- Spring Boot documentation
- React documentation
- Bootstrap documentation
- JWT documentation
