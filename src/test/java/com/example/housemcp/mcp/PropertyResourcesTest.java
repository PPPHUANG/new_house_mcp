package com.example.housemcp.mcp;

import com.example.housemcp.mcp.sdk.McpServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertyResourcesTest {

    @Autowired
    private McpServer mcpServer;

    @Test
    void testReadBuyingGuideResource() {
        String content = mcpServer.readResource("guide://buying");
        assertThat(content).contains("Buying Guide");
    }
}
