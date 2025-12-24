# House Trade MCP Server

A specialized Model Context Protocol (MCP) server for real estate listings and trading knowledge.

## Transport Modes

This server supports two transport modes:
1. **Stdio (Standard I/O):** Default mode, uses a custom lightweight SDK.
2. **SSE (Server-Sent Events):** Uses Spring AI's official MCP server implementation over HTTP.

### Switching Modes

You can switch modes by setting the `mcp.transport.mode` property in `src/main/resources/application.properties`:

- For Stdio: `mcp.transport.mode=stdio`
- For SSE: `mcp.transport.mode=sse`

**Note:** When using SSE mode, you must also ensure the application starts as a web application. You can do this by overriding the following property:
`spring.main.web-application-type=servlet`

### Running the Server

#### Stdio Mode (Default)
```bash
mvn spring-boot:run
```

#### SSE Mode
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--mcp.transport.mode=sse --spring.main.web-application-type=servlet"
```
The SSE endpoint will be available at `http://localhost:8080/mcp/sse`.

## Features
- `search_properties`: Search for listings.
- `get_property_details`: Get deep-dive info.
- `guide://buying`: Access the buying guide resource.
