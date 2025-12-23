package com.example.housemcp.mcp.sdk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McpServerImpl implements McpServer {
    private final McpTransport transport;
    private final List<Object> tools = new ArrayList<>();
    private final List<Object> resources = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public McpServerImpl(McpTransport transport) {
        this.transport = transport;
    }

    @Override
    public void start() {
        System.err.println("MCP Server starting...");
        transport.start(this::handleMessage);
    }

    @Override
    public void stop() {
        System.err.println("MCP Server stopping");
    }

    @Override
    public void addTool(Object tool) {
        System.err.println("Registering tool object: " + tool.getClass().getName());
        tools.add(tool);
    }

    @Override
    public void addResource(Object resource) {
        System.err.println("Registering resource object: " + resource.getClass().getName());
        resources.add(resource);
    }

    @Override
    public Object invokeTool(String name, Map<String, Object> params) {
        for (Object toolObj : tools) {
            Class<?> clazz = toolObj.getClass();
            if (clazz.getName().contains("$$")) {
                clazz = clazz.getSuperclass();
            }
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(McpTool.class)) {
                    McpTool annotation = method.getAnnotation(McpTool.class);
                    if (annotation.name().equals(name)) {
                        try {
                            Object[] args = new Object[method.getParameterCount()];
                            java.lang.reflect.Parameter[] parameters = method.getParameters();
                            for (int i = 0; i < parameters.length; i++) {
                                String paramName = parameters[i].getName();
                                Object val = params.get(paramName);
                                // Simple type conversion if needed
                                if (val != null) {
                                    if (parameters[i].getType() == Double.class && val instanceof Integer) {
                                        val = ((Integer) val).doubleValue();
                                    }
                                    if (parameters[i].getType() == Long.class && val instanceof Integer) {
                                        val = ((Integer) val).longValue();
                                    }
                                }
                                args[i] = val;
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

    @Override
    public String readResource(String uri) {
        for (Object resourceObj : resources) {
            for (Method method : resourceObj.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(McpResource.class)) {
                    McpResource annotation = method.getAnnotation(McpResource.class);
                    if (annotation.uri().equals(uri)) {
                        try {
                            return (String) method.invoke(resourceObj);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to read resource: " + uri, e);
                        }
                    }
                }
            }
        }
        throw new IllegalArgumentException("Resource not found: " + uri);
    }

    private void handleMessage(String message) {
        try {
            JsonNode request = objectMapper.readTree(message);
            JsonNode id = request.get("id");
            String method = request.has("method") ? request.get("method").asText() : "";

            if (method.isEmpty() && request.has("result")) {
                // Response to our request (ignore for now)
                return;
            }

            ObjectNode response = objectMapper.createObjectNode();
            response.set("jsonrpc", objectMapper.convertValue("2.0", JsonNode.class));
            response.set("id", id);

            try {
                switch (method) {
                    case "initialize":
                        ObjectNode result = objectMapper.createObjectNode();
                        result.put("protocolVersion", "2024-11-05");

                        ObjectNode caps = objectMapper.createObjectNode();
                        caps.set("tools", objectMapper.createObjectNode());
                        caps.set("resources", objectMapper.createObjectNode());
                        result.set("capabilities", caps);

                        ObjectNode serverInfo = objectMapper.createObjectNode();
                        serverInfo.put("name", "house-mcp");
                        serverInfo.put("version", "0.1.0");
                        result.set("serverInfo", serverInfo);

                        response.set("result", result);
                        break;

                    case "notifications/initialized":
                        // No response needed for notification
                        return;

                    case "tools/list":
                        ObjectNode toolsResult = objectMapper.createObjectNode();
                        ArrayNode toolsArray = objectMapper.createArrayNode();

                        System.err.println("Listing tools, count: " + tools.size());
                        for (Object toolObj : tools) {
                            System.err.println("Inspecting tool object: " + toolObj.getClass().getName());
                            Class<?> clazz = toolObj.getClass();
                            if (clazz.getName().contains("$$")) {
                                clazz = clazz.getSuperclass();
                                System.err.println("Unwrapped proxy to: " + clazz.getName());
                            }

                            for (Method m : clazz.getDeclaredMethods()) {
                                if (m.isAnnotationPresent(McpTool.class)) {
                                    McpTool ann = m.getAnnotation(McpTool.class);
                                    ObjectNode toolNode = objectMapper.createObjectNode();
                                    toolNode.put("name", ann.name());
                                    toolNode.put("description", ann.description());
                                    toolNode.set("inputSchema", generateSchema(m));
                                    toolsArray.add(toolNode);
                                }
                            }
                        }
                        toolsResult.set("tools", toolsArray);
                        response.set("result", toolsResult);
                        break;

                    case "tools/call":
                        JsonNode paramsNode = request.get("params");
                        String toolName = paramsNode.get("name").asText();
                        Map<String, Object> arguments = objectMapper.convertValue(paramsNode.get("arguments"),
                                Map.class);
                        if (arguments == null)
                            arguments = new HashMap<>();

                        Object toolResult = invokeTool(toolName, arguments);

                        ObjectNode callResult = objectMapper.createObjectNode();
                        ArrayNode content = objectMapper.createArrayNode();
                        ObjectNode contentItem = objectMapper.createObjectNode();
                        contentItem.put("type", "text");
                        contentItem.put("text", objectMapper.writeValueAsString(toolResult));
                        content.add(contentItem);
                        callResult.set("content", content);

                        response.set("result", callResult);
                        break;

                    case "resources/list":
                        ObjectNode resResult = objectMapper.createObjectNode();
                        ArrayNode resArray = objectMapper.createArrayNode();

                        System.err.println("Listing resources, count: " + resources.size());
                        for (Object resObj : resources) {
                            Class<?> clazz = resObj.getClass();
                            if (clazz.getName().contains("$$")) {
                                clazz = clazz.getSuperclass();
                            }
                            for (Method m : clazz.getDeclaredMethods()) {
                                if (m.isAnnotationPresent(McpResource.class)) {
                                    McpResource ann = m.getAnnotation(McpResource.class);
                                    ObjectNode resNode = objectMapper.createObjectNode();
                                    resNode.put("uri", ann.uri());
                                    resNode.put("name", ann.name());
                                    resNode.put("description", ann.description());
                                    resArray.add(resNode);
                                }
                            }
                        }
                        resResult.set("resources", resArray);
                        response.set("result", resResult);
                        break;

                    case "resources/read":
                        JsonNode resParams = request.get("params");
                        String uri = resParams.get("uri").asText();
                        String text = readResource(uri);

                        ObjectNode readResult = objectMapper.createObjectNode();
                        ArrayNode contents = objectMapper.createArrayNode();
                        ObjectNode item = objectMapper.createObjectNode();
                        item.put("uri", uri);
                        item.put("mimeType", "text/plain");
                        item.put("text", text);
                        contents.add(item);
                        readResult.set("contents", contents);

                        response.set("result", readResult);
                        break;

                    case "ping":
                        response.set("result", objectMapper.createObjectNode());
                        break;

                    default:
                        // Ignore unknown methods or return error
                        return;
                }

                transport.send(objectMapper.writeValueAsString(response));

            } catch (Exception e) {
                e.printStackTrace();
                ObjectNode error = objectMapper.createObjectNode();
                error.put("code", -32603);
                error.put("message", "Internal error: " + e.getMessage());
                response.set("error", error);
                transport.send(objectMapper.writeValueAsString(response));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JsonNode generateSchema(Method method) {
        ObjectNode schema = objectMapper.createObjectNode();
        schema.put("type", "object");
        ObjectNode properties = objectMapper.createObjectNode();
        ArrayNode required = objectMapper.createArrayNode();

        for (java.lang.reflect.Parameter param : method.getParameters()) {
            ObjectNode paramNode = objectMapper.createObjectNode();
            Class<?> type = param.getType();
            if (type == String.class) {
                paramNode.put("type", "string");
            } else if (type == Integer.class || type == int.class || type == Long.class || type == long.class) {
                paramNode.put("type", "integer");
            } else if (type == Double.class || type == double.class || type == Float.class || type == float.class) {
                paramNode.put("type", "number");
            } else if (type == Boolean.class || type == boolean.class) {
                paramNode.put("type", "boolean");
            } else {
                // Fallback
                paramNode.put("type", "string");
            }
            properties.set(param.getName(), paramNode);
            // Assume all are optional for now unless we add annotations, or make all
            // required?
            // Let's assume nullable objects are optional, primitives are required?
            // For now, let's just leave required empty or populate if simple.
        }
        schema.set("properties", properties);
        return schema;
    }
}