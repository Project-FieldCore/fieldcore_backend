# FieldCore backend

API REST do **FieldOps / FieldCore** — Java 25 + Spring Boot 4.1.

## Documentação

Especificação do projeto (regras de negócio, arquitetura, contrato API, DoR/DoD):

**[`engine/projects/fieldcore/README.md`](../../engine/projects/fieldcore/README.md)** (no workspace Super)

## Requisitos locais

- JDK **25**
- Maven 3.6.3+ (ou `./mvnw`)

## Executar (dev)

```bash
./mvnw spring-boot:run
```

| Endpoint | URL |
|----------|-----|
| Health | http://localhost:8080/actuator/health |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| H2 console | http://localhost:8080/h2-console |

Profile `postgres`: `SPRING_PROFILES_ACTIVE=postgres` (requer `JWT_SECRET` e PostgreSQL).

## Status

Sprint 1 — **scaffold** (security skeleton, Flyway placeholder, OpenAPI skeleton). Auth real e CRUDs a partir da Sprint 2.

## Git

Segue [`engine/CONVENTIONS.md`](../../engine/CONVENTIONS.md).
