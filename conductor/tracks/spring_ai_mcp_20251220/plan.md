# Plan: Dual Transport MCP Server

## Phase 1: Configuration and Dependency Setup [checkpoint: c25f0af]
- [x] Task: Update `pom.xml` to add Spring AI MCP dependencies while retaining existing dependencies.
- [x] Task: Add `mcp.transport.mode` property to `application.properties` with a default value of `stdio`.
- [x] Task: Refactor `McpConfig.java` to apply `@ConditionalOnProperty` so it only loads the custom SDK beans when `mcp.transport.mode` is `stdio`.
- [x] Task: Conductor - User Manual Verification 'Phase 1: Configuration and Dependency Setup' (Protocol in workflow.md)

## Phase 2: Spring AI SSE Implementation
- [ ] Task: Create `SpringAiConfig.java` that loads when `mcp.transport.mode` is `sse`.
- [ ] Task: Implement Tool registration in `SpringAiConfig` by creating `Function` beans that delegate to `PropertyTools`.
- [ ] Task: Implement Resource registration in `SpringAiConfig` that delegates to `PropertyResources`.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Spring AI SSE Implementation' (Protocol in workflow.md)

## Phase 3: Verification and Documentation
- [ ] Task: Verify the application starts in `stdio` mode and tools function correctly (using existing tests or manual check).
- [ ] Task: Verify the application starts in `sse` mode and exposes the endpoint (manual verification).
- [ ] Task: Update `README.md` to document how to switch transport modes.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Verification and Documentation' (Protocol in workflow.md)