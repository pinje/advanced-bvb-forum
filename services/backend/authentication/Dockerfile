# Build stage
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-alpine
ARG PROFILE=prod
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/authentication-${APP_VERSION}.jar /app/

EXPOSE 8081

ENV PROFILE=${PROFILE}
ENV VERSION_TAG=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${PROFILE} authentication-${VERSION_TAG}.jar