# Task Orders Microservice

This project is a demo microservice for managing order-related operations. It's built using Java and Spring Boot, integrating with Kafka for messaging between services.

## Features
- Order management (CRUD operations)
- Kafka messaging for asynchronous communication
- Microservice architecture

## Prerequisites
- Java 11+
- Apache Kafka
- Gradle

## Running the Application

1. Clone the repository:
    ```bash
    git clone https://github.com/ac1dr3d/task-orders.git
    cd task-orders
    ```

2. Build the application:
    ```bash
    ./gradlew build
    ```

3. Ensure that Kafka is running:
   Kafka must be running locally or available remotely for proper integration. You can use Docker to run Kafka.

4. Run the application:
    ```bash
    ./gradlew bootRun
    ```

## API Endpoints
- `POST /orders`: Create a new order
- `GET /orders`: Get all orders
- `GET /orders/{id}`: Get an order by ID
- `PUT /orders/{id}`: Update an existing order
- `DELETE /orders/{id}`: Delete an order

## Kafka Integration

The microservice is designed to interact with Kafka to handle order-related events. Ensure Kafka is set up and available at `localhost:9092` (or adjust the configuration).

Example Kafka configuration in `application.properties`:
```properties
spring.kafka.bootstrap-servers=localhost:29092
spring.kafka.consumer.group-id=order-group
spring.kafka.consumer.auto-offset-reset=earliest

