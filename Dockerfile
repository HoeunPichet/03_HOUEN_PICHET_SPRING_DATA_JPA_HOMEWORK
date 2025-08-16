# STAGE 1: Builder using Gradle with JDK 21
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy Gradle wrapper scripts and config
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle ./
COPY settings.gradle ./

# Make gradlew executable
RUN chmod +x gradlew

# Copy source code
COPY src ./src

# Build the JAR (skip tests to avoid DB connection issues)
RUN ./gradlew build -x test

# Ensure JAR is available
RUN ls -la build/libs/

# STAGE 2: Runtime
# Use Eclipse Temurin JRE with Java 21
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from builder stage
COPY --from=builder app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]