# Use a base image with Java installed
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY car_rental/target/car_rental-1.0-SNAPSHOT.jar .

# Expose the port your API listens on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "car_rental-1.0-SNAPSHOT.jar"]