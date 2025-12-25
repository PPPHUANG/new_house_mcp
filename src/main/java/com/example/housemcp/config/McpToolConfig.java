package com.example.housemcp.config;

import com.example.housemcp.mcp.resources.PropertyResources;
import com.example.housemcp.mcp.tools.PropertyTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to register @Tool annotated methods as MCP tools.
 */
@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider propertyToolsCallbackProvider(PropertyTools propertyTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(propertyTools)
                .build();
    }
    
    @Bean
    public ToolCallbackProvider propertyResourcesCallbackProvider(PropertyResources propertyResources) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(propertyResources)
                .build();
    }
}
