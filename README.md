# Hotel Reservation System

A Spring Boot application for managing hotel rooms, users, and reservations.  
Supports user authentication, room management, and reservation workflows.

## Features

- User registration and login (JWT-based authentication)
- Admin and user roles
- Room management (CRUD)  
  \- Room IDs are auto-generated; not required in creation requests
- Reservation management (CRUD)  
  \- Reservation IDs are auto-generated; not required in creation requests
- User management (CRUD)  
  \- User IDs are auto-generated; not required in creation requests
- DTOs used for request/response payloads
- Robust exception handling for missing entities
- Swagger API documentation

## Technologies

- Java, Spring Boot, Spring Security, Spring Data JPA
- JWT for authentication
- Swagger/OpenAPI for documentation
- Maven for build
- PostgreSQL (main database)
- H2 (in-memory database for testing)

### Prerequisites

- Java 17+
- Maven 3.8+
- PostgreSQL

## Usage

- Use the API endpoints to manage users, rooms, and reservations.
- For creation endpoints, do **not** provide IDs; they are auto-generated.
- Admin endpoints are protected and require authentication.
- JWT tokens are required for most operations.

## Testing

- Unit and integration tests use an in-memory H2 database.
- Service layer is covered by unit tests (see `src/test/java/com/aly/brightskies/task3/services/`).
- Run `mvn test` to execute tests in an isolated environment.