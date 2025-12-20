package com.example.housemcp.mcp.sdk;

public interface McpServer {
    void start();
    void stop();
    void addTool(Object tool);
    void addResource(Object resource);
}
