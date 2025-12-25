package com.example.housemcp;

import com.example.housemcp.mcp.sdk.McpServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
    "mcp.transport.mode=stdio",
    "spring.main.web-application-type=none"
})
class McpServerConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void mcpServerBeanExists() {
        assertThat(context.getBean(McpServer.class)).isNotNull();
    }
}
