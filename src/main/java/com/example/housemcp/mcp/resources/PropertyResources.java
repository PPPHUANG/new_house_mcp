package com.example.housemcp.mcp.resources;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class PropertyResources {

    @Tool(name = "get_buying_guide", description = "Get the house buying guide. A comprehensive guide on how to buy a property.")
    public String getBuyingGuide() throws IOException {
        ClassPathResource resource = new ClassPathResource("guides/buying_guide.md");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}
