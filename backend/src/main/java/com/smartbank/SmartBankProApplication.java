package com.smartbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class for SmartBank Pro
 * 
 * WHY THIS FILE IS NEEDED:
 * This is the entry point of the Spring Boot application. Every Spring Boot application
 * needs a main class annotated with @SpringBootApplication that bootstraps the application.
 * 
 * WHAT THE CODE DOES:
 * - @SpringBootApplication: This is a composite annotation that combines:
 *   1. @Configuration: Marks the class as a source of bean definitions
 *   2. @EnableAutoConfiguration: Enables Spring Boot's auto-configuration mechanism
 *   3. @ComponentScan: Enables component scanning to find Spring components
 * 
 * - SpringApplication.run(): This method:
 *   1. Creates the ApplicationContext
 *   2. Starts the embedded Tomcat server
 *   3. Registers all the beans
 *   4. Runs the application
 * 
 * HOW IT WORKS INTERNALLY:
 * When you run this class, Spring Boot:
 * 1. Scans for all @Component, @Service, @Repository, @Controller annotations
 * 2. Creates beans and manages their lifecycle
 * 3. Configures the application based on dependencies in pom.xml
 * 4. Starts the embedded web server (Tomcat) on port 8080
 * 5. Sets up the database connection based on application.properties
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "This is the main entry point of our Spring Boot application. The @SpringBootApplication
 * annotation is a convenience annotation that combines three important annotations:
 * @Configuration for bean definitions, @EnableAutoConfiguration for automatic configuration,
 * and @ComponentScan for component scanning. When we run the main method, Spring Boot
 * creates the application context, starts the embedded Tomcat server, and initializes all
 * the beans. This is the standard way to bootstrap any Spring Boot application."
 */
@SpringBootApplication
public class SmartBankProApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankProApplication.class, args);
    }
}
