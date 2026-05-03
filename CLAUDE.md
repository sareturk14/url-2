# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bat
# Build WAR (skips tests by default via maven.skip.test=true)
mvn clean package -DskipTests

# Build with tests
mvn clean package

# Run tests only
mvn test

# Deploy to Docker (MySQL + Tomcat)
deploy.bat
# or manually:
docker compose down -v
docker compose up -d
```

App is available at `http://localhost:8080/bm470/` after Docker deployment. The WAR is deployed as `ROOT.war` inside the Tomcat container, so the context path in Docker is `/bm470` only when deployed via the WAR path volume mapping.

## Architecture

N-tier Spring MVC application: **Controller → Service → DAO → Hibernate/JPA → MySQL 8**.

```
tr.edu.duzce.mf.bm/
├── config/        AppInitializer (replaces web.xml), DatabaseConfig, WebConfig, RequestLoggingInterceptor
├── controller/    UrlController (Spring MVC)
├── service/       UrlService interface + UrlServiceImpl (@Transactional)
├── dao/           UrlDao interface + UrlDaoImpl (@Repository, uses Hibernate SessionFactory)
└── entity/        UrlLink, ClickAnalytics, User (JPA @Entity with Lombok)
```

**100% annotation-based** — no web.xml, no applicationContext.xml, no hibernate.cfg.xml.

## Key Design Points

- **Configuration**: `DatabaseConfig` reads `DB_HOST` env var (defaults to `localhost`; Docker sets it to `mysql`). Connection pool is c3p0 (5–20 connections).
- **Schema**: Managed by `hbm2ddl.auto=update` — Hibernate auto-creates/updates tables on startup.
- **Short code generation**: `UrlServiceImpl` uses `SecureRandom` over 62-char alphanumeric set, 6 chars, retries on collision.
- **i18n**: `CookieLocaleResolver` with cookie `language-cookie`; default locale Turkish (`tr`). Switch via `?lang=tr` or `?lang=en`. Message bundles: `messages_tr.properties` / `messages_en.properties`.
- **Request logging**: `RequestLoggingInterceptor` logs URI, method, params on preHandle and view name on postHandle.

## Core Endpoints

| Method | Path | Action |
|--------|------|--------|
| GET | `/` | Home page |
| POST | `/shorten` | Create short URL (auto-prepends `http://` if no protocol) |
| GET | `/{shortCode}` | Redirect + increment click count |

## Tech Stack

- Java 21, Spring Framework 7.0.5, Spring MVC, Hibernate ORM 7.2.5
- Jakarta Persistence 3.2, Jakarta Servlet 6.1
- MySQL 8 (Docker), Tomcat 10.1 (Docker)
- Lombok, Log4j2 → `logs/bm470-app.log`
- JUnit Jupiter (JUnit 5) for tests
- Output artifact: `target/bm470.war`
