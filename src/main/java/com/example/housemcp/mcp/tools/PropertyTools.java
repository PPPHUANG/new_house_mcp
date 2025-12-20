package com.example.housemcp.mcp.tools;

import com.example.housemcp.mcp.sdk.McpTool;
import com.example.housemcp.model.Property;
import com.example.housemcp.service.PropertyService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyTools {

    private final PropertyService propertyService;

    public PropertyTools(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @McpTool(name = "search_properties", description = "Search for properties by location and maximum price")
    public List<Property> searchProperties(String location, Double maxPrice) {
        return propertyService.getAllProperties().stream()
                .filter(p -> location == null || p.getAddress().toLowerCase().contains(location.toLowerCase()))
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
