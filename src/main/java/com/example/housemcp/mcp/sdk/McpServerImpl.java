package com.example.housemcp.mcp.sdk;

import java.util.ArrayList;
import java.util.List;

public class McpServerImpl implements McpServer {
    private final McpTransport transport;
    private final List<Object> tools = new ArrayList<>();
    private final List<Object> resources = new ArrayList<>();

    public McpServerImpl(McpTransport transport) {
        this.transport = transport;
    }

    @Override
    public void start() {
        System.err.println("MCP Server starting on transport: " + transport);
    }

    @Override
    public void stop() {
        System.err.println("MCP Server stopping");
    }

    @Override
    public void addTool(Object tool) {
        tools.add(tool);
    }

    @Override
    public void addResource(Object resource) {
        resources.add(resource);
    }
}
