# Tech Stack - House Trade MCP Server

## 1. Core Framework & Language
- **Language:** Java 20 (Non-LTS, featuring modern language enhancements)
- **Framework:** Spring Boot 4 (Note: Leveraging the latest features of the Spring ecosystem)
- **Build Tool:** Maven (standard project structure and dependency management)

## 2. MCP Integration
- **SDK:** Official MCP SDK (Java) for implementing server-side tools and resources.
- **Protocol:** MCP over Standard I/O (default for most AI assistant integrations).

## 3. Data Layer (Mock/In-Memory)
- **Strategy:** JSON-based persistent storage.
- **Implementation:** Data is stored in `.json` files within the `src/main/resources` directory and loaded into memory on application startup. This allows for easy editing of the "mock" data without rebuilding.

## 4. Testing & Quality
- **Framework:** Spring Boot Test (for integration and end-to-end verification of MCP tool flows).
- **Target:** High test coverage for tool logic to ensure consistent behavior with AI agents.
