# EmployeeHub — Spring Boot Employee Management System

EmployeeHub is a production-ready full-stack Employee Management CRUD application built using Java Spring Boot, PostgreSQL, Thymeleaf, Hibernate/JPA, and Docker.

The project demonstrates enterprise backend architecture, database integration, cloud deployment, and full CRUD operations.

---

# Live Demo

Add Railway URL here:

```text
https://employeehub-production-c8be.up.railway.app/employees
```

---

# Features

* Employee CRUD operations
* PostgreSQL database integration
* Spring Boot MVC architecture
* Hibernate/JPA ORM
* Thymeleaf server-side rendering
* Validation and exception handling
* Dockerized deployment
* Railway cloud deployment
* Production-ready configuration
* Responsive UI

---

# Tech Stack

## Backend

* Java 21
* Spring Boot 3
* Spring MVC
* Spring Data JPA
* Hibernate

## Frontend

* Thymeleaf
* HTML5
* CSS3
* JavaScript

## Database

* PostgreSQL

## DevOps / Deployment

* Docker
* Railway
* GitHub

---

# Project Architecture

```text
Controller → Service → Repository → PostgreSQL
```

### Layers

* Controller Layer

  * Handles HTTP requests

* Service Layer

  * Business logic

* Repository Layer

  * Database operations using JPA

* Database Layer

  * PostgreSQL persistence

---

# Screenshots

Add screenshots here after deployment.

---

# Local Setup

## Clone Repository

```bash
git clone https://github.com/vkgonesane/employeehub.git
cd employeehub
```

## Configure Database

Update:

```yaml
application.yml
```

## Run Application

```bash
gradle bootRun
```

Application runs at:

```text
http://localhost:8080
```

---

# Deployment

Application deployed using:

* Railway
* Docker
* PostgreSQL

---

# Learning Outcomes

This project demonstrates:

* Java enterprise backend development
* Spring Boot architecture
* Database schema management
* Hibernate/JPA integration
* Cloud deployment
* Docker containerization
* Production configuration handling
* CI/CD workflow using GitHub + Railway

---

# Author

Vaibhav Kumar Verma

GitHub:
https://github.com/vkgonesane
