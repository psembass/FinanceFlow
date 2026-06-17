# FinanceFlow

A portfolio project demonstrating backend development with Java and Spring Boot.
Built to showcase practical experience with modern Java backend practices rather than as a production application.

## Stack

Java 21, Spring Boot 3.5, PostgreSQL 15, Hibernate, Flyway, Redis, Docker, Docker Compose, GitLab CI, Gradle, JUnit 5, Mockito

## What It Does

REST API for tracking personal income and expenses:

* Users — create and manage user accounts
* Categories — define income and expense categories
* Transactions — record and filter transactions per user
* Summary — monthly financial report with total income, total expenses, balance, and per-category breakdown

## Running

```bash
docker compose up --build
```

Starts the application, PostgreSQL, and Redis via Docker Compose. 
API available at `http://localhost:8080/swagger-ui.html`.

## CI/CD
GitLab CI pipeline runs on every push to main and on merge requests:

* Build — compiles code
* Test — runs the full test suite against a PostgreSQL service container
* Package - assembles jar

## Testing
Four-level test coverage:
* Controller (@WebMvcTest)
* Service (Mockito)
* Repository (@DataJpaTest)
* Integration (@SpringBootTest)

## Implementation Notes

- Dynamic transaction filtering via JPA Specifications (type, category, date range, pagination)
- Financial summary endpoint uses JPQL aggregation with `SUM` grouped by category
- Schema managed with Flyway versioned migrations
- Summaries cached in Redis, one by userId