# my-test-eureka

A small **Spring Boot** demo for **Netflix Eureka** service discovery: one Eureka server and three independent Eureka client applications.

## Stack

- **Java** 21 (Gradle toolchains)
- **Spring Boot** 4.x
- **Spring Cloud** (Netflix Eureka Server / Eureka Client)

## Modules

| Module | Role |
|--------|------|
| `eureka-server` | Eureka discovery server (standalone port) |
| `eureka-client-1` | Eureka client application |
| `eureka-client-2` | Eureka client application |
| `eureka-client-3` | Eureka client application |

Each module is its own Gradle project (own `settings.gradle` and wrapper).

## Port layout (intended)

The server and each client family use **non-overlapping** port ranges:

| Component | Port range | Notes |
|-----------|------------|--------|
| Eureka Server | **Dedicated port** (not shared with clients) | Conventionally **8761** for Eureka; configure via `server.port` if you use a different value. |
| Eureka Client 1 | **8100–8105** | Assign instances within this range (e.g. local profiles or `--server.port`). |
| Eureka Client 2 | **8110–8115** | Same idea as Client 1. |
| Eureka Client 3 | **8120–8125** | Same idea as Client 1. |

Run only **one** process per port. Multiple instances of the same client app should each pick a **different** free port inside that client’s range.

## Running locally

From each module directory, using the included Gradle wrapper:

```bash
cd eureka-server && ./gradlew bootRun --args='--server.port=8761'
```

```bash
cd eureka-client-1 && ./gradlew bootRun --args='--server.port=8100'
```

```bash
cd eureka-client-2 && ./gradlew bootRun --args='--server.port=8110'
```

```bash
cd eureka-client-3 && ./gradlew bootRun --args='--server.port=8120'
```

Start the **Eureka Server** first, then the clients. Point clients at the server URL in `application.properties` or environment variables (for example `eureka.client.service-url.defaultZone`) once you configure discovery.

## Tests

```bash
cd <module> && ./gradlew test
```
