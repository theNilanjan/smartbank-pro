package com.smartbank.entity;

import com.smartbank.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User Entity - Represents a user in the banking system
 * 
 * WHY THIS FILE IS NEEDED:
 * This entity represents the users (customers and admins) of the banking system.
 * It stores user authentication data and profile information.
 * 
 * WHAT THE CODE DOES:
 * - @Entity: Marks this class as a JPA entity, meaning it will be mapped to a database table
 * - @Table: Specifies the table name as "users" (JPA defaults to class name)
 * - @Data, @NoArgsConstructor, @AllArgsConstructor: Lombok annotations to generate getters, setters, constructors
 * - @Id: Marks the primary key field
 * - @GeneratedValue: Specifies auto-generation strategy for the primary key
 * - @Column: Configures column properties like nullable, unique, length
 * - @Enumerated: Specifies how enum values should be stored (STRING stores actual enum names)
 * - @OneToMany: Defines a one-to-many relationship with Account entity
 * - @Cascade: Specifies cascade operations (when user is deleted, delete associated accounts)
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. JPA/Hibernate maps this class to a "users" table in the database
 * 2. Each field becomes a column in the table
 * 3. The @OneToMany relationship means one user can have multiple bank accounts
 * 4. mappedBy = "user" indicates that the "user" field in Account class owns the relationship
 * 5. CascadeType.ALL means operations on User (persist, merge, remove) cascade to accounts
 * 6. fetch = FetchType.LAZY means accounts are loaded only when accessed (performance optimization)
 * 7. Password should be encrypted before storing (we'll use BCrypt in the service layer)
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "The User entity is mapped to the users table using JPA annotations. It stores user credentials
 * and profile information. The @OneToMany relationship with Account entity represents that a single
 * user can have multiple bank accounts. I used FetchType.LAZY for performance - accounts are only
 * loaded when needed. The password field stores the encrypted password using BCrypt algorithm.
 * The role field uses an enum to restrict users to either CUSTOMER or ADMIN roles for security.
 * The createdAt timestamp helps track when the user registered."
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CUSTOMER;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
