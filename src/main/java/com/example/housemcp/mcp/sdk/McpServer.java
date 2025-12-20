package com.example.housemcp.mcp.sdk;

import java.util.Map;

public interface McpServer {
    void start();
    void stop();
    void addTool(Object tool);
    void addResource(Object resource);
    Object invokeTool(String name, Map<String, Object> params);
    String readResource(String uri);
}
