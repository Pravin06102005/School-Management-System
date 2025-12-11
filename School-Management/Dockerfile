# ============================
# 1️⃣ BUILD STAGE
# ============================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy project files from School-Management folder
COPY School-Management/pom.xml .
COPY School-Management/src ./src

# Build jar
RUN mvn -q -e -DskipTests package

# ============================
# 2️⃣ RUN STAGE
# ============================
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
