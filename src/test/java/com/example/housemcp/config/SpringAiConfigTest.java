package com.example.housemcp.config;

import com.example.housemcp.mcp.resources.PropertyResources;
import com.example.housemcp.mcp.tools.PropertyTools;
import io.modelcontextprotocol.server.McpServerFeatures;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SpringAiConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(SpringAiConfig.class)
            .withBean(PropertyTools.class)
            .withBean(PropertyResources.class)
            .withBean(com.example.housemcp.service.JsonPropertyService.class)
            .withBean(com.fasterxml.jackson.databind.ObjectMapper.class);

    @Test
    void sseModeLoadsSpringAiBeans() {
        contextRunner
                .withPropertyValues("mcp.transport.mode=sse")
                .run(context -> {
                    assertThat(context).hasBean("search_properties");
                    assertThat(context).hasBean("get_property_details");
                    assertThat(context).hasBean("buyingGuideResource");
                    
                    assertThat(context.getBean("search_properties")).isInstanceOf(Function.class);
                    assertThat(context.getBean("buyingGuideResource")).isInstanceOf(McpServerFeatures.SyncResourceRegistration.class);
                });
    }

    @Test
    void stdioModeDoesNotLoadSpringAiBeans() {
        contextRunner
                .withPropertyValues("mcp.transport.mode=stdio")
                .run(context -> {
                    assertThat(context).doesNotHaveBean("search_properties");
                    assertThat(context).doesNotHaveBean("get_property_details");
                    assertThat(context).doesNotHaveBean("buyingGuideResource");
                });
    }

    @Test
    void defaultModeDoesNotLoadSpringAiBeans() {
        contextRunner
                .run(context -> {
                    assertThat(context).doesNotHaveBean("search_properties");
                    assertThat(context).doesNotHaveBean("get_property_details");
                    assertThat(context).doesNotHaveBean("buyingGuideResource");
                });
    }
}
