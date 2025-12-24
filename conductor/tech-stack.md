# Tech Stack - House Trade MCP Server

## 1. Core Framework & Language
- **Language:** Java 20 (Non-LTS, featuring modern language enhancements)
- **Framework:** Spring Boot 3.3.0 (Adjusted from 4.x for environment compatibility)
- **Build Tool:** Maven (standard project structure and dependency management)

## 2. MCP Integration
- **SDK:** Spring AI MCP Server (Official Spring integration) and Custom SDK (legacy stdio support).
- **Protocol:** Dual Transport support - Stdio (Custom SDK) and SSE (Spring AI).

## 3. Data Layer (Mock/In-Memory)
- **Strategy:** JSON-based persistent storage.
- **Implementation:** Data is stored in `.json` files within the `src/main/resources` directory and loaded into memory on application startup. This allows for easy editing of the "mock" data without rebuilding.

## 4. Testing & Quality
- **Framework:** Spring Boot Test (for integration and end-to-end verification of MCP tool flows).
- **Target:** High test coverage for tool logic to ensure consistent behavior with AI agents.
