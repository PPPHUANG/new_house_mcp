# Spec: Dual Transport MCP Server (Stdio & Spring AI SSE)

## Overview
This track focuses on enhancing the House Trade MCP Server to support two transport modes: the existing Standard I/O (stdio) transport using the custom SDK, and a new HTTP/SSE transport using Spring AI. The user will be able to select the desired transport via a configuration property.

## Functional Requirements
- **Configuration Switch:** Introduce a configuration property (e.g., `mcp.transport.mode`) to select between `stdio` and `sse`.
- **Legacy Stdio Support:** Ensure the existing custom SDK and Stdio transport remain functional and are the default.
- **Spring AI SSE Support:** Implement a new transport layer using Spring AI that operates over HTTP/SSE.
- **Tool Adaptation:** Adapt the existing `PropertyTools` to be usable by the Spring AI MCP implementation without breaking the custom SDK usage.
- **Resource Adaptation:** Adapt the existing `PropertyResources` to be usable by the Spring AI MCP implementation.

## Non-Functional Requirements
- **Spring Boot 4:** Maintain compatibility.
- **Isolation:** The two transport implementations should not interfere with each other. Beans for one should not be loaded when the other is active (where appropriate).

## Acceptance Criteria
- Setting `mcp.transport.mode=stdio` (or omitting it) starts the server with the custom Stdio transport.
- Setting `mcp.transport.mode=sse` starts the server with the Spring AI SSE transport on a web port.
- `search_properties` and `get_property_details` work in both modes.
- `buying_guide` resource works in both modes.