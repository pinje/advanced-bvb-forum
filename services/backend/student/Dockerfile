FROM openjdk:17-jdk-alpine

# Set working directory in the container
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml ./

# Download Maven dependencies
RUN mvn dependency:resolve

# Copy the entire project source
COPY src ./src

# Build the project
RUN mvn package

# Copy the built JAR file from the build stage to the runtime image
COPY /app/target/*.jar student.jar

ENTRYPOINT ["java", "-jar", "student.jar"]
