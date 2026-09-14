# Job Portal Web Application

A full-stack Job Portal built with **Java, Spring Boot, Spring MVC, Spring Data JPA, Hibernate, PostgreSQL, JSP, and Bootstrap**. Employers can post jobs; applicants can browse, search, and apply to them.

## Features

- 12+ CRUD operations across Employer, Applicant, JobPosting, and JobApplication entities
- 8+ RESTful API endpoints (GET/POST/PUT/DELETE) for job postings and applications
- 5-table normalized PostgreSQL schema with one-to-many relationships (Employer → JobPosting → JobApplication ← Applicant)
- Server-side rendered JSP + Bootstrap frontend for browsing, searching, viewing, and posting jobs
- Job search by title keyword and location filter
- Centralized exception handling (`@RestControllerAdvice`) returning clean JSON error responses
- Duplicate-application prevention logic in the service layer

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2, Spring MVC |
| Data Access | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Frontend | JSP, JSTL, Bootstrap 5, vanilla JS (fetch API) |
| Build Tool | Maven |

## Database Schema

```
Employer (1) ──< (many) JobPosting (1) ──< (many) JobApplication (many) >── (1) Applicant
                        JobPosting (many) >── (1) Category
```

**Tables:** `employers`, `job_postings`, `applicants`, `job_applications`, `categories`

## REST API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/jobs` | Create a job posting |
| GET | `/api/jobs` | List all jobs (supports `?keyword=` and `?location=` filters) |
| GET | `/api/jobs/{id}` | Get a job by ID |
| PUT | `/api/jobs/{id}` | Update a job posting |
| DELETE | `/api/jobs/{id}` | Delete a job posting |
| POST | `/api/applications?jobId=&applicantId=` | Apply to a job |
| GET | `/api/applications/{id}` | Get an application by ID |
| GET | `/api/applications/applicant/{applicantId}` | Get all applications by an applicant |
| GET | `/api/applications/job/{jobId}` | Get all applicants for a job |
| PUT | `/api/applications/{id}/status?status=` | Update application status |
| POST | `/api/employers` | Register an employer |
| POST | `/api/applicants` | Register an applicant |

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 14+

### Setup

1. Clone the repo:
   ```
   git clone https://github.com/shanavasjageer/job-portal.git
   cd job-portal
   ```

2. Create the database (PostgreSQL, unlike MySQL, does NOT auto-create it for you):
   ```
   psql -U postgres
   CREATE DATABASE job_portal_db;
   \q
   ```

3. Update PostgreSQL credentials in `src/main/resources/application.properties`:
   ```
   spring.datasource.username=postgres
   spring.datasource.password=your_postgres_password
   ```
   Hibernate auto-creates the 5 tables on first run (`ddl-auto=update`).

4. Run the application:
   ```
   mvn spring-boot:run
   ```

5. Open the app:
   - Frontend: `http://localhost:8080/`
   - API base: `http://localhost:8080/api/jobs`

### Testing the API with Postman

1. Register an employer: `POST /api/employers`
   ```json
   { "companyName": "Acme Corp", "email": "hr@acme.com", "password": "test123", "contactNumber": "9999999999" }
   ```
2. Register an applicant: `POST /api/applicants`
   ```json
   { "name": "Jane Doe", "email": "jane@example.com", "password": "test123", "phone": "8888888888" }
   ```
3. Post a job: `POST /api/jobs`
   ```json
   { "title": "Java Developer", "description": "Backend role", "location": "Bengaluru", "salary": 4.5, "employer": { "id": 1 } }
   ```
4. Apply to it: `POST /api/applications?jobId=1&applicantId=1`

## Project Structure

```
src/main/java/com/shanavas/jobportal/
├── entity/         # JPA entities (5 tables)
├── repository/     # Spring Data JPA repositories
├── service/        # Business logic layer
├── controller/     # REST controllers + JSP view controller
└── exception/      # Custom exceptions + global handler

src/main/webapp/WEB-INF/jsp/   # JSP views (Bootstrap-styled)
```

## Author

Shanavas J — Java Full Stack Developer (Fresher)
[LinkedIn](https://linkedin.com/in/shanavas-j-25b361257)
