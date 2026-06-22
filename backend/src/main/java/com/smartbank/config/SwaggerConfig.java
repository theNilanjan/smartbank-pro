package com.smartbank.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig - OpenAPI/Swagger configuration for API documentation
 * 
 * WHY THIS FILE IS NEEDED:
 * Swagger/OpenAPI provides interactive API documentation. This configuration
 * sets up the documentation for all REST endpoints, making it easy for developers
 * to understand and test the API.
 * 
 * WHAT THE CODE DOES:
 * - Configures OpenAPI 3.0 documentation
 * - Sets up API information (title, description, version)
 * - Configures JWT security scheme for authenticated endpoints
 * - Provides contact and license information
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. Spring Boot auto-configures Swagger with springdoc-openapi dependency
 * 2. This bean customizes the OpenAPI specification
 * 3. Security scheme configures JWT Bearer token authentication
 * 4. Swagger UI available at: http://localhost:8080/swagger-ui.html
 * 5. OpenAPI JSON available at: http://localhost:8080/v3/api-docs
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "I configured Swagger/OpenAPI to provide interactive API documentation. The
 * configuration includes API metadata like title, description, and version.
 * I also set up JWT security scheme so that authenticated endpoints can be
 * tested directly from the Swagger UI by providing the Bearer token. The
 * Swagger UI is available at /swagger-ui.html, allowing developers to explore
 * and test all endpoints without writing code. This improves developer experience
 * and serves as living documentation."
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartBankOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SmartBank Pro API")
                        .description("Production-ready Full Stack Banking Application API Documentation")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SmartBank Pro Team")
                                .email("support@smartbank.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Please enter a valid JWT token")));
    }
}
