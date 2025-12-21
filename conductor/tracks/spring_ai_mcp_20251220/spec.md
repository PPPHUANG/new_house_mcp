# Spec: Spring AI MCP Server Integration

## Overview
This track focuses on refactoring the existing House Trade MCP Server to use Spring AI's official MCP server libraries. The goal is to replace the custom `com.example.housemcp.mcp.sdk` implementation with a standardized, robust framework supported by the Spring ecosystem.

## Functional Requirements
- **Refactor Core Infrastructure:** Replace the custom `McpServer`, `McpTool`, and `McpResource` implementation with Spring AI's MCP server components.
- **Streamable HTTP (SSE) Transport:** Configure the server to communicate over HTTP using Server-Sent Events (SSE).
- **Tool Migration:** Re-register the existing `search_properties` and `get_property_details` tools using Spring AI's tool registration mechanism (e.g., `@Tool` annotation).
- **Resource Migration:** Migrate the `buying_guide` resource to use Spring AI's resource handling.

## Non-Functional Requirements
- **Spring Boot 4:** Ensure compatibility with Spring Boot 4 (as specified in the tech stack).
- **Standardization:** Adhere to the official Model Context Protocol (MCP) spec as implemented by Spring AI.

## Acceptance Criteria
- The application starts and initializes a Spring AI MCP server.
- The server is accessible over HTTP/SSE.
- Existing tools (`search_properties`, `get_property_details`) are functional via the new server.
- Existing resources (`buying_guide`) are accessible via the new server.
- The old `com.example.housemcp.mcp.sdk` package is removed or deprecated.

## Out of Scope
- Adding new AI models (e.g., LLM integration) beyond the MCP server infrastructure.
