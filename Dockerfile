# Image contenant Maven pour construire l'application
FROM maven:3.8.5-eclipse-temurin-17 AS build
# Répertoire de travail
WORKDIR /app
# Copie du fichier pom.xml
COPY pom.xml .
# Copie du répertoire src
COPY src ./src
# Compilation de l'application
RUN mvn clean package -DskipTests
# Image contenant Java 17 pour exécuter l'application
RUN mvn install

# Image contenant Java 17 fournissant un environnement léger pour exéuter l'application
FROM eclipse-temurin:17-jdk
# Répertoire de travail
WORKDIR /app
# Copie du fichier jar compilé de l'étape de construction
COPY --from=build /app/target/*.jar app.jar
# Exposition du port 8080
EXPOSE 8080
# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]