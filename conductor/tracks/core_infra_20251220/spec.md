# Spec: Core MCP Infrastructure & Property Search

## Overview
This track focuses on setting up the fundamental Spring Boot 4 application, integrating the MCP SDK, and implementing the first set of tools for property searching and information retrieval using JSON-based mock data.

## Scope
- Initialize Spring Boot 4 project with Maven.
- Configure MCP Server over Standard I/O.
- Implement a JSON data loader for property listings and guides.
- Expose MCP Tools: `search_properties`, `get_property_details`.
- Expose MCP Resource: `buying_guide`.

## Success Criteria
- The server starts and communicates via MCP.
- AI assistants can list and execute the search tools.
- Data is correctly loaded from JSON files.
- Integration tests verify the tool outputs.
