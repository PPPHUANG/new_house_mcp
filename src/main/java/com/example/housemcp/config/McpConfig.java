package com.example.housemcp.config;

import com.example.housemcp.mcp.sdk.McpServer;
import com.example.housemcp.mcp.sdk.McpServerImpl;
import com.example.housemcp.mcp.sdk.McpTransport;
import com.example.housemcp.mcp.sdk.StdioMcpTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public McpTransport mcpTransport() {
        return new StdioMcpTransport();
    }

    @Bean
    public McpServer mcpServer(McpTransport transport) {
        McpServer server = new McpServerImpl(transport);
        server.start();
        return server;
    }
}
