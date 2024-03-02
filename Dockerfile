# Use the official OpenJDK base image for Java 21
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container at the defined working directory
COPY /target/*.jar app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify any runtime arguments that you want to pass to your Spring Boot application
CMD ["java", "-jar", "app.jar"]

