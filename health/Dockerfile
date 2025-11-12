Build du projet avec maven et eclipse-temurin
FROM maven:3.9.11-eclipse-temurin-21 AS build

WORKDIR /app
#copy
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
# Donner les permissions d'exécution au wrapper Maven
RUN chmod +x mvnw
# Télécharger les dépendances pour accélérer les builds
RUN ./mvnw dependency:go-offline
#COPY le code
COPY src ./src
# Compiler le projet sans exécuter les tests
RUN ./mvnw clean package -DskipTests

#image final avec eclipse-temurin
FROM eclipse-temurin:21-jdk

WORKDIR /app
# Copier le jar depuis l'étape de build
COPY --from=build /app/target/*.jar globalShopper.jar
# Exposer le port sur lequel l'application va tourner
EXPOSE 8085
# Lancer l'application en prenant en compte la variable PORT
ENTRYPOINT ["java", "-jar", "globalShopper.jar"]