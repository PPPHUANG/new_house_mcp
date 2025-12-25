package com.example.housemcp.mcp.tools;

import com.example.housemcp.model.Property;
import com.example.housemcp.service.PropertyService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PropertyTools {

    private final PropertyService propertyService;

    public PropertyTools(PropertyService propertyService) {
        System.err.println("PropertyTools constructor called!");
        this.propertyService = propertyService;
    }

    @Tool(name = "search_properties", description = "Search for properties by location and maximum price. Returns a list of matching properties.")
    public String searchProperties(
            @ToolParam(description = "Location to search for properties. Leave empty or omit to search all locations.", required = false) String location,
            @ToolParam(description = "Maximum price for properties. Leave empty or omit to not filter by price.", required = false) Double maxPrice) {
        try {
            List<Property> results = propertyService.getAllProperties().stream()
                    .filter(p -> location == null || location.isEmpty() || 
                            (p.getAddress() != null && p.getAddress().toLowerCase().contains(location.toLowerCase())))
                    .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
            
            if (results.isEmpty()) {
                return "No properties found matching the criteria.";
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Found ").append(results.size()).append(" properties:\n\n");
            for (Property p : results) {
                sb.append("- ID: ").append(p.getId())
                  .append(", Address: ").append(p.getAddress())
                  .append(", Price: $").append(String.format("%.2f", p.getPrice()))
                  .append(", Type: ").append(p.getType())
                  .append(", Bedrooms: ").append(p.getBedrooms())
                  .append(", Bathrooms: ").append(p.getBathrooms())
                  .append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Error searching properties: " + e.getMessage();
        }
    }

    @Tool(name = "get_property_details", description = "Get detailed information for a specific property by its ID")
    public String getPropertyDetails(
            @ToolParam(description = "The ID of the property to get details for", required = true) String id) {
        try {
            Property p = propertyService.getPropertyById(id).orElse(null);
            if (p == null) {
                return "Property with ID '" + id + "' not found.";
            }
            return String.format("""
                Property Details:
                - ID: %s
                - Address: %s
                - Price: $%.2f
                - Type: %s
                - Bedrooms: %d
                - Bathrooms: %d
                - Description: %s
                """, p.getId(), p.getAddress(), p.getPrice(), p.getType(), 
                p.getBedrooms(), p.getBathrooms(), p.getDescription());
        } catch (Exception e) {
            return "Error getting property details: " + e.getMessage();
        }
    }
}
