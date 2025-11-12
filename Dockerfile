# Build du projet avec maven et eclipse-temurin
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Image finale avec eclipse-temurin
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar act-backend.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "act-backend.jar"]
