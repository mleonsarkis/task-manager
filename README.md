# Project Task Manager – Backend

## Overview

This project is a **Project Task Manager** backend application.  
It allow users to create **Projects** and manage **Tasks** inside each project.

The backend expose a **REST API** that is consumed by a **React frontend**.

This document explain:
- What technology stack is used
- Why this technologies was chosen
- How to run the backend locally

---

## Technology Stack

### Java 17
Java 17 is used as the main programming language.

**Why Java**
- Very stable and mature language
- Large ecosystem and community
- Strong typing help reduce bugs
- Widely used in enterprise backend systems

Java 17 is LTS (Long Term Support), so it is good choice for production systems.

---

### Spring Boot 3
Spring Boot is used to build the REST API.

**Why Spring Boot**
- Fast to setup and configure
- Embedded server (no need external Tomcat)
- Very good support for REST APIs
- Easy integration with JPA, validation and security

Spring Boot reduce boilerplate code and make project easy to maintain.

---

### Spring Web
Spring Web is used to expose HTTP endpoints.

**Why Spring Web**
- Simple annotation based controllers
- Easy to create REST endpoints
- Handles JSON serialization automatically

---

### Spring Data JPA + Hibernate
JPA is used for persistence and Hibernate as implementation.

**Why JPA**
- Object Relational Mapping (ORM)
- Less SQL boilerplate
- Easy CRUD operations
- Database independent

Entities like `Project` and `Task` are mapped directly to database tables.

---

### H2 Database (In-Memory)
H2 is used as the database for local development.

**Why H2**
- No installation needed
- Runs in memory
- Very fast for testing
- Easy to replace later with PostgreSQL or MySQL

For production, this can be replaced with a real database.

---

### Maven
Maven is used as build and dependency management tool.

**Why Maven**
- Standard for Java projects
- Easy dependency management
- Works very well with Spring Boot
- Maven Wrapper included (no need install Maven manually)

---

### Validation (Jakarta Validation)
Used to validate API request inputs.

**Why Validation**
- Prevent invalid data entering system
- Clear error messages
- Less manual checks in code

Example: project name cannot be empty, task title must have minimum length.

---

### REST API (JSON over HTTP)
The backend expose REST endpoints using JSON.

**Why REST**
- Simple and standard
- Easy to consume by frontend
- Stateless and scalable
- Works well with SPA frontend like React

---

## Project Structure

src/main/java/com/example/taskmanager
├── controller -> REST controllers
├── service -> Business logic
├── repo -> Database access (JPA repositories)
├── entity -> JPA entities (Project, Task)
├── dto -> Request and Response models
├── exception -> Custom exceptions and error handling
└── config -> Configuration (CORS, etc)


This layered structure make code clean and easy to understand.

---

## How to Run Backend (Windows)

### Requirements
- Java 17 or higher installed
- Node.js NOT required for backend
- Internet connection (first run download dependencies)

---

### Step 1: Open terminal in backend folder

From root, make sure `pom.xml` file exist.

---

### Step 2: Run Spring Boot application

On **Windows**, run:

```cmd
mvnw.cmd spring-boot:run
```

---

### Future Improvements

This project was kept simple on purpose, but there are many possible improvements.

- **Authentication and Authorization**  
  Add users login and roles (admin, normal user).  
  Each project could belong to a specific user.

- **Pagination and Search**  
  When there are many projects or tasks, pagination should be added.  
  Search by project name or task title would improve usability.

- **Database Upgrade**  
  Replace H2 in-memory database with PostgreSQL or MySQL.  
  This allow data persistence between restarts.

- **Database Migrations**  
  Use Flyway or Liquibase to manage database schema changes.  
  This help keep database versioning under control.

- **Optimistic Locking**  
  Add versioning to entities to avoid concurrent update problems.  
  Useful when multiple users edit same task.

- **Improved Validation Rules**  
  Add more business validation, like due date cannot be in the past  
  or task status transitions rules.

- **Better Frontend UI**  
  Replace browser prompts with proper modals and forms.  
  Improve layout and user experience.

- **State Management and Caching**  
  Use React Query or similar library to cache API responses  
  and reduce unnecessary network calls.

- **Automated Testing**


