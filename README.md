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

## 🚀 Getting Started

### Backend Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd smartbank-pro/backend
```

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

## 👨‍💻 Author

Nilanjan Ghosh

## 🙏 Acknowledgments

- Spring Boot documentation
- React documentation
- Bootstrap documentation
- JWT documentation
