# Use a base image with JDK and Maven installed
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory in the container
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .

# Download Maven dependencies (only if pom.xml has changed)
RUN mvn dependency:go-offline -B

# Copy the entire project source
COPY src ./src

# Build the project
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM adoptopenjdk:17-jre-hotspot

# Set working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage to the runtime image
COPY --from=build /app/target/*.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
