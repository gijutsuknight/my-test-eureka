# my-test-eureka

A small **Spring Boot** demo for **Netflix Eureka** service discovery: one Eureka server and three independent Eureka client applications.

## Stack

- **Java** 21 (Gradle toolchains)
- **Spring Boot** 4.x
- **Spring Cloud** (Netflix Eureka Server / Eureka Client)
- **springdoc-openapi** (Swagger UI) on each client

## Modules

| Module | Role |
|--------|------|
| `eureka-server` | Eureka discovery server (standalone port) |
| `eureka-client-1` | Eureka client with REST test API and Swagger |
| `eureka-client-2` | Eureka client with REST test API and Swagger |
| `eureka-client-3` | Eureka client with REST test API and Swagger |

Each module is its own Gradle project (own `settings.gradle` and wrapper).

## Port layout (intended)

The server and each client family use **non-overlapping** port ranges:

| Component | Port range | Notes |
|-----------|------------|--------|
| Eureka Server | **Dedicated port** (not shared with clients) | Default in config is **8761**; override with `server.port` or `--server.port` if needed. |
| Eureka Client 1 | **8100–8105** | Default **8100** in `application.yml`; use another port in the range for extra instances. |
| Eureka Client 2 | **8110–8115** | Default **8110** in `application.yml`. |
| Eureka Client 3 | **8120–8125** | Default **8120** in `application.yml`. |

Run only **one** process per port. Multiple instances of the same client app should each pick a **different** free port inside that client’s range.

## Servlet context paths

Each client serves its web layer under a dedicated context path (see `server.servlet.context-path` in each client’s `application.yml`):

| Client | Context path |
|--------|----------------|
| `eureka-client-1` | `/eureka-client-1` |
| `eureka-client-2` | `/eureka-client-2` |
| `eureka-client-3` | `/eureka-client-3` |

Example base URLs (using default ports):

- Client 1: `http://localhost:8100/eureka-client-1`
- Client 2: `http://localhost:8110/eureka-client-2`
- Client 3: `http://localhost:8120/eureka-client-3`

## API and Swagger

Each client exposes:

- **`GET /test`** — returns `OK` and logs the Spring application name and requestor IP at controller entry and again immediately before the response.

Swagger UI and OpenAPI docs (springdoc):

| | Swagger UI | OpenAPI JSON |
|---|------------|----------------|
| Paths (under each context) | `/swagger-ui.html` | `/v3/api-docs` |

Full examples for default ports:

- Client 1: `http://localhost:8100/eureka-client-1/swagger-ui.html` — try **GET** `/test`
- Client 2: `http://localhost:8110/eureka-client-2/swagger-ui.html`
- Client 3: `http://localhost:8120/eureka-client-3/swagger-ui.html`

## Eureka configuration

Clients use **`application.yml`** with `eureka.client.serviceUrl.defaultZone` (default `http://localhost:8761/eureka`). You can point elsewhere with the **`EUREKA_URI`** environment variable.

Start the **Eureka Server** first, then the clients.

## Running locally

From each module directory, using the included Gradle wrapper:

```bash
cd eureka-server && ./gradlew bootRun
```

If your server `application.yml` does not set the port, pass it explicitly:

```bash
cd eureka-server && ./gradlew bootRun --args='--server.port=8761'
```

```bash
cd eureka-client-1 && ./gradlew bootRun
```

```bash
cd eureka-client-2 && ./gradlew bootRun
```

```bash
cd eureka-client-3 && ./gradlew bootRun
```

Ports and context paths come from each client’s `application.yml`; override the port with `--args='--server.port=<port>'` when running multiple instances.

## Tests

```bash
cd <module> && ./gradlew test
```
