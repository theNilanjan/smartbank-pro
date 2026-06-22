package com.smartbank.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig - Static Resource Configuration
 * 
 * WHY THIS FILE IS NEEDED:
 * This class configures Spring Boot to serve static files (HTML, CSS, JS) from the
 * frontend build directory. This allows the backend to serve the React application.
 * 
 * WHAT THE CODE DOES:
 * - Maps the frontend build directory to serve static files
 * - Allows the backend to serve index.html and bundled JavaScript/CSS files
 * - Configures resource handlers for static content
 * 
 * HOW IT WORKS INTERNALLY:
 * 1. ResourceHandlerRegistry: Registers handlers for static resources
 * 2. addResourceHandlers: Maps URL patterns to file system locations
 * 3. The build directory contains the compiled React application
 * 4. Spring Boot serves these files when requested
 * 
 * HOW TO EXPLAIN IN INTERVIEW:
 * "WebConfig configures Spring Boot to serve the React frontend's static files.
 * I mapped the root URL and all static resource requests to the frontend build directory,
 * which contains the compiled React application. This allows the Spring Boot backend
 * to serve both the API endpoints and the frontend UI from the same server, making
 * deployment simpler and avoiding CORS issues in production."
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static files from backend resources/static directory
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        
        // Specifically serve static assets (JS, CSS, images)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
