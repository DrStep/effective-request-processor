FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app
COPY . /app
# Build the app
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
# Copy the built JAR file to the container
COPY --from=build /app/target/effective-request-processor.jar .
# Run application command
CMD ["java", "-jar", "effective-request-processor.jar"]