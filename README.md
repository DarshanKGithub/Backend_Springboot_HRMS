# HRMS Spring Boot Backend

This is a Java 21 / Spring Boot 3.3.x migration scaffold for the existing Python FastAPI HRMS backend.

## What is included
- Spring Boot application bootstrap
- Spring Security + JWT-ready auth setup
- CORS config
- Health endpoint
- Authentication controller and DTOs
- Project structure aligned to the Python backend modules

## Run
```bash
cd SpringBackend
mvn spring-boot:run
```

## Default endpoint
- `GET /health`
- `POST /auth/login`

## Notes
This is a starting migration layer. The existing Python backend is much larger than this scaffold, so the next step is to port the actual business modules one by one: employees, org, attendance, leave, payroll, recruitment, lifecycle, etc.
