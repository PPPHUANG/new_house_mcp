package com.example.housemcp.config;

import com.example.housemcp.mcp.resources.PropertyResources;
import com.example.housemcp.mcp.tools.PropertyTools;
import com.example.housemcp.model.Property;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@Configuration
@ConditionalOnProperty(name = "mcp.transport.mode", havingValue = "sse")
public class SpringAiConfig {

    @Bean
    @Description("Search for properties by location and maximum price")
    public Function<SearchRequest, List<Property>> search_properties(PropertyTools propertyTools) {
        return request -> propertyTools.searchProperties(request.location(), request.maxPrice());
    }

    @Bean
    @Description("Get detailed information for a specific property by its ID")
    public Function<IdRequest, Property> get_property_details(PropertyTools propertyTools) {
        return request -> propertyTools.getPropertyDetails(request.id());
    }

    @Bean
    public McpServerFeatures.SyncResourceRegistration buyingGuideResource(PropertyResources propertyResources) {
        McpSchema.Resource resource = new McpSchema.Resource(
                "guide://buying",
                "Buying Guide",
                "A guide on how to buy a property",
                "text/markdown",
                null
        );

        return new McpServerFeatures.SyncResourceRegistration(resource, (request) -> {
            try {
                String content = propertyResources.getBuyingGuide();
                McpSchema.TextResourceContents textContent = new McpSchema.TextResourceContents(request.uri(), "text/markdown", content);
                return new McpSchema.ReadResourceResult(List.of(textContent));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public record SearchRequest(String location, Double maxPrice) {}
    public record IdRequest(String id) {}
}