# Use the official Maven image to build the app
FROM gradle:7.6.4-jdk17 AS build
WORKDIR /app

# Copy the project files to the container
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the project and create the fat JAR file
RUN gradle clean bootJar

# Use the official OpenJDK image for runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the JAR file from the build image
COPY --from=build /app/build/libs/task-orders.jar /app/orders-app.jar

# Set environment variables (from your configuration)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/task
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=asdASD123
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
ENV SPRING_KAFKA_CONSUMER_GROUP_ID=task-group
ENV TASK_APP_JWTSECRET=====================Task=Spring=========================
ENV TASK_APP_JWTEXPIRATIONMS=86400000

# Expose port 8081
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "orders-app.jar"]