package com.example.housemcp.mcp.sdk;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public Object invokeTool(String name, Map<String, Object> params) {
        for (Object toolObj : tools) {
            for (Method method : toolObj.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(McpTool.class)) {
                    McpTool annotation = method.getAnnotation(McpTool.class);
                    if (annotation.name().equals(name)) {
                        try {
                            // Simple parameter matching for this mock
                            Object[] args = new Object[method.getParameterCount()];
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            java.lang.reflect.Parameter[] parameters = method.getParameters();
                            for (int i = 0; i < parameters.length; i++) {
                                String paramName = parameters[i].getName();
                                // Note: Compilation with -parameters is needed for this to work well, 
                                // or we just use index-based matching or a map.
                                // For this mock, we'll try to match by name from the map.
                                args[i] = params.get(paramName);
                            }
                            return method.invoke(toolObj, args);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to invoke tool: " + name, e);
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException("Tool not found: " + name);
    }
}