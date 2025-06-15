# Utilisation d'une image Java légère
FROM openjdk:17-jdk-alpine

# Définition du répertoire de travail
WORKDIR /app

# Copie du fichier JAR dans le conteneur
COPY target/*.jar app.jar

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]