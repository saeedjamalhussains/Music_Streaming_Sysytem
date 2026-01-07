# Build stage
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom first to leverage Docker cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build JAR
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar /app/backend.jar

EXPOSE 5050
ENTRYPOINT ["java", "-jar", "backend.jar"]
