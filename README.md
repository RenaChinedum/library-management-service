# Library Management Service

The Library Management Service is a Spring Boot application that allows patrons to borrow and return books. It features caching, unit tests, Spring Security, and logging using AOP.

## Features

- Patrons can borrow books
- Patrons can return books
- Overdue books attract a penal charge
- Spring caching for improved performance
- Unit tests for service layers and repository layers using Mockito
- Spring Security for authentication and authorization
- Logging using AOP for tracking application events

## Technologies Used

- Java 17
- Spring Boot 3.0.5
- Spring Data JPA
- Spring Security
- Spring AOP
- Mockito
- JUnit 5
- H2 Database (for testing)
- MySQL (for production)
- 
- ## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/RenaChinedum/library-management-service.git

2. Navigate to the project directory:
bash
cd library-management-service

3. Build the project:
bash
./mvnw clean install

4. Run the application:
bash
./mvnw spring-boot:run

5. Access the application at http://localhost:8089.

- ## Testing

-To run the unit tests:
bash
./mvnw test

-The tests cover the service layers and repository layers using Mockito.

- ## Security
The application uses Spring Security for authentication and authorization. The default username is admin and the default password is password.

- ## Logging
Logging is implemented using Spring Slfj4. The logs are written to the console and can be configured in the application.properties file.

- ## Configuration
The application can be configured using the application.properties file. You can set the database connection details, caching configurations, and other settings.

- ## Contributing
If you find any issues or have suggestions for improvements, feel free to create a new issue or submit a pull request.
