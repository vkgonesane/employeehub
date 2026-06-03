# EmployeeHub — Full-Stack Java CRUD App

A production-ready **Employee Management** CRUD web application built with:

- ☕ **Java 21**
- 🚀 **Spring Boot 3.2**
- 🐘 **PostgreSQL**
- 📦 **Gradle** (build tool)
- 🌿 **Thymeleaf** (server-side templating)
- 🗃️ **Spring Data JPA + Hibernate**
- ✅ **Bean Validation (Jakarta)**
- 🔧 **Lombok**

---

## Features

| Feature | Details |
|---|---|
| **CRUD** | Create, Read, Update, Delete employees |
| **Search** | Live debounced search across name, email, department |
| **Filter** | Filter by department and employment status |
| **Sort** | Click column headers to sort ascending/descending |
| **Pagination** | Server-side pagination with configurable page size |
| **Validation** | Server-side form validation with inline error messages |
| **Statistics** | Dashboard cards: total, active, on-leave, departments |
| **Responsive** | Mobile-friendly CSS layout |

---

## Project Structure

```
crudapp/
├── build.gradle                        # Gradle build config
├── docker-compose.yml                  # One-command local dev setup
├── Dockerfile                          # Multi-stage production image
└── src/
    ├── main/
    │   ├── java/com/example/crudapp/
    │   │   ├── CrudAppApplication.java  # Entry point
    │   │   ├── controller/
    │   │   │   ├── HomeController.java
    │   │   │   └── EmployeeController.java
    │   │   ├── service/
    │   │   │   ├── EmployeeService.java
    │   │   │   └── EmployeeServiceImpl.java
    │   │   ├── repository/
    │   │   │   └── EmployeeRepository.java
    │   │   ├── model/
    │   │   │   └── Employee.java
    │   │   ├── dto/
    │   │   │   └── EmployeeDto.java
    │   │   └── exception/
    │   │       ├── ResourceNotFoundException.java
    │   │       ├── DuplicateEmailException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       ├── application.yml
    │       ├── db/
    │       │   ├── schema.sql           # DDL
    │       │   └── data.sql             # Seed data
    │       ├── templates/
    │       │   ├── employees/
    │       │   │   ├── list.html        # Employee list with search/filter/pagination
    │       │   │   ├── form.html        # Create / Edit form
    │       │   │   └── view.html        # Employee detail
    │       │   ├── fragments/
    │       │   │   └── layout.html      # Shared nav/head/footer
    │       │   └── error.html
    │       └── static/
    │           ├── css/app.css
    │           └── js/app.js
    └── test/
        └── java/com/example/crudapp/
            └── EmployeeServiceTest.java  # Unit tests (Mockito)
```

---

## Quick Start

### Option A — Docker Compose (recommended)

```bash
# 1. Start PostgreSQL + build and run the app
docker compose up --build

# 2. Open in browser
open http://localhost:8080
```

### Option B — Local PostgreSQL

**Prerequisites:** Java 21, PostgreSQL 14+

```bash
# 1. Create the database
psql -U postgres -c "CREATE DATABASE crudapp_db;"

# 2. Run the app (schema + seed data auto-applied on first run)
./gradlew bootRun

# 3. Open in browser
open http://localhost:8080
```

**First run only:** set `spring.jpa.hibernate.ddl-auto: create` in `application.yml`, then switch to `validate` for subsequent runs.

---

## Configuration

All config lives in `src/main/resources/application.yml`.  
Override with environment variables:

| Env var | Default | Description |
|---|---|---|
| `DB_USERNAME` | `postgres` | PostgreSQL username |
| `DB_PASSWORD` | `postgres` | PostgreSQL password |
| `SERVER_PORT` | `8080` | HTTP port |

---

## Running Tests

```bash
./gradlew test
```

Tests use an **H2 in-memory database** — no PostgreSQL needed.

---

## API Endpoints (Web)

| Method | URL | Description |
|---|---|---|
| `GET` | `/employees` | List all employees (with search/filter/page) |
| `GET` | `/employees/new` | Show create form |
| `POST` | `/employees` | Create employee |
| `GET` | `/employees/{id}` | View employee detail |
| `GET` | `/employees/{id}/edit` | Show edit form |
| `POST` | `/employees/{id}` | Update employee |
| `POST` | `/employees/{id}/delete` | Delete employee |
