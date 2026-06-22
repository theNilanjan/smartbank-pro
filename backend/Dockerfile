# Use Maven image for build stage
FROM maven:3.9.16-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use Eclipse Temurin (OpenJDK) for runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar file from build stage
COPY --from=build /app/target/smartbank-pro-1.0.0.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
