FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the target directory of your Maven build
COPY /target/student-0.0.1-SNAPSHOT.jar.jar /app/student.jar

# Specify the command to run your application when the container starts
CMD ["java", "-jar", "student.jar"]
