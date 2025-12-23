package com.example.housemcp.mcp.sdk;

import java.util.function.Consumer;

public interface McpTransport {
    void start(Consumer<String> messageHandler);
    void send(String message);
}
