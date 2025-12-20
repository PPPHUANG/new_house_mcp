# Plan: Core MCP Infrastructure & Property Search

## Phase 1: Project Initialization & Infrastructure
- [x] Task: Initialize Spring Boot 4 project using Maven (pom.xml, basic structure). [7f36749]
- [x] Task: Integrate MCP Java SDK and configure the server for Standard I/O. [1bb7253]
- [ ] Task: Implement the JSON data loading service to read property data from `src/main/resources`.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Project Initialization & Infrastructure' (Protocol in workflow.md)

## Phase 2: Tool Implementation
- [ ] Task: Implement the `search_properties` tool (filtering by location, price).
- [ ] Task: Implement the `get_property_details` tool (fetching by ID).
- [ ] Task: Implement the `buying_guide` resource.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Tool Implementation' (Protocol in workflow.md)

## Phase 3: Verification
- [ ] Task: Write integration tests using Spring Boot Test to verify tool responses.
- [ ] Task: Perform manual verification using an MCP inspector or CLI.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Verification' (Protocol in workflow.md)
