#FROM openjdk:17-jdk-alpine
#
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the packaged JAR file from the target directory of your Maven build
#COPY services/backend/student/target/student-0.0.1-SNAPSHOT.jar /app/student.jar
#
## Specify the command to run your application when the container starts
#CMD ["java", "-jar", "student.jar"]

## Use an official Maven image as the base image
#FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the pom.xml and the project files to the container
#COPY pom.xml .
#COPY src ./src
#
## Build the application using Maven
#RUN mvn clean package -DskipTests
#
## Use an official OpenJDK image as the base image
#FROM openjdk:17-jdk-alpine
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the built JAR file from the previous stage to the container
#COPY --from=build /app/target/student.jar .
#
## Set the command to run the application
#CMD ["java", "-jar", "student.jar"]

WORKDIR /opt/app
COPY ./build/libs/student-0.0.1-SNAPSHOT.jar ./

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar student-0.0.1-SNAPSHOT.jar"]
