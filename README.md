# Jaba Food Backend

This is a backend system for a collaborative restaurant management platform developed as part of a tech challenge. The project aims to create a solution for restaurant management and customer interaction.

## Key Features

- User Management (Restaurant Owners and Customers)
- Password Change Functionality
- Restaurant and Menu Management
- Database Migration Management with Flyway
- API Documentation with Swagger
- Unit and Integration Testing
- **Designed with Clean Architecture for better scalability and maintainability**

## Technical Stack

- **Backend:** Java 21, Spring Boot
- **Architecture:** Clean Architecture
- **Database:** PostgreSQL
- **Containerization:** Docker, Docker Compose
- **ORM & Migrations:** Hibernate, Flyway
- **Testing:** JUnit, Mockito

## Prerequisites

- Java 21 JDK
- Docker
- Docker Compose

## Setup Instructions

1. **Clone the Repository**
    ```sh
    git clone https://github.com/rtcarbone/jaba-food-clean-arch.git
    cd jaba-food-clean-arch
    ```

2. **Build and Run the Application**
    ```sh
    docker-compose up --build
    ```

3. **Access the Swagger UI**  
   After starting the application, you can access the Swagger UI at:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

## Project Structure

The project follows **Clean Architecture**, ensuring better separation of concerns and maintainability. The main layers include:

- **Entities:** Business rules and core logic
- **Use Cases:** Application-specific rules
- **Frameworks & Drivers:** Infrastructure components like controllers and repositories