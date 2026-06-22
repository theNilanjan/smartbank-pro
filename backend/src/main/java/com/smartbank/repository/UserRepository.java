package com.smartbank.repository;

import com.smartbank.entity.User;
import com.smartbank.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository - Data access layer for User entity
 * 
 * WHY THIS FILE IS NEEDED:
 * Repository interfaces provide methods to perform CRUD operations on entities.
 * Spring Data JPA automatically generates the implementation at runtime.
 * 
 * WHAT THE CODE DOES:
 * - Extends JpaRepository<User, Long>: Inherits CRUD methods (save, findById, findAll, delete, etc.)
 * - Optional<User> findByEmail(String email): Custom method to find user by email
 * - boolean existsByEmail(String email): Check if email already exists
 * - Optional<User> findByEmailAndPassword(String email, String password): For authentication
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Spring Data JPA creates a proxy implementation at runtime
 * 2. Method names following naming conventions are automatically converted to SQL queries
 * 3. findByEmail → SELECT * FROM users WHERE email = ?
 * 4. existsByEmail → SELECT COUNT(*) FROM users WHERE email = ?
 * 5. No need to write SQL - Spring Data JPA handles it
 * 6. Uses Hibernate as the JPA provider under the hood
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "UserRepository extends JpaRepository which provides built-in CRUD methods like save,
 * findById, findAll, and delete. I also added custom query methods like findByEmail and
 * existsByEmail. Spring Data JPA automatically generates the SQL based on the method names
 * following naming conventions. For example, findByEmail generates a SELECT query with
 * a WHERE clause. This eliminates the need to write boilerplate DAO code and makes
 * data access much cleaner."
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
}
