package com.example.housemcp.mcp.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class StdioMcpTransport implements McpTransport {
    @Override
    public void start(Consumer<String> messageHandler) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        messageHandler.accept(line);
                    } catch (Exception e) {
                        System.err.println("Error handling message: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading from stdin: " + e.getMessage());
            }
        }, "McpStdioListener").start();
    }

    @Override
    public void send(String message) {
        System.out.println(message);
    }
}
