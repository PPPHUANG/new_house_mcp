# Plan: Spring AI MCP Server Integration

## Phase 1: Infrastructure Refactor
- [ ] Task: Update `pom.xml` to add Spring AI MCP server dependencies and remove custom SDK-specific boilerplate (if any).
- [ ] Task: Configure the `McpServer` bean using Spring AI with SSE (HTTP) transport.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Infrastructure Refactor' (Protocol in workflow.md)

## Phase 2: Tool and Resource Migration
- [ ] Task: Migrate `PropertyTools` to use Spring AI's tool registration mechanism (e.g., `@Tool` annotation).
- [ ] Task: Migrate `PropertyResources` to use Spring AI's resource implementation.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Tool and Resource Migration' (Protocol in workflow.md)

## Phase 3: Cleanup and Final Verification
- [ ] Task: Remove the legacy `com.example.housemcp.mcp.sdk` package and associated configuration.
- [ ] Task: Write integration tests to verify tools and resources via the Spring AI MCP server.
- [ ] Task: Perform manual verification of the SSE endpoint.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Cleanup and Final Verification' (Protocol in workflow.md)
