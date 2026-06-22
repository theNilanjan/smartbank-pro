package com.smartbank.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        String databaseUser = System.getenv("DATABASE_USER");
        String databasePassword = System.getenv("DATABASE_PASSWORD");

        if (databaseUrl != null && !databaseUrl.startsWith("jdbc:")) {
            databaseUrl = "jdbc:" + databaseUrl;
        }

        return DataSourceBuilder.create()
                .url(databaseUrl != null ? databaseUrl : "jdbc:postgresql://localhost:5432/smartbank_pro")
                .username(databaseUser != null ? databaseUser : "sa")
                .password(databasePassword != null ? databasePassword : "")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
