package com.example.housemcp.mcp;

import com.example.housemcp.mcp.sdk.McpServer;
import com.example.housemcp.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertyToolsTest {

    @Autowired
    private McpServer mcpServer;

    @Test
    void testSearchPropertiesTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("location", "Springfield");
        
        @SuppressWarnings("unchecked")
        List<Property> results = (List<Property>) mcpServer.invokeTool("search_properties", params);
        
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getAddress()).contains("Springfield");
    }

    @Test
    void testGetPropertyDetailsTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "1");
        
        Property result = (Property) mcpServer.invokeTool("get_property_details", params);
        
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
    }
}
