package com.example.housemcp.config;

import com.example.housemcp.mcp.sdk.*;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

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

    @Bean
    public SmartInitializingSingleton mcpInitializer(ApplicationContext context, McpServer server) {
        return () -> {
            String[] beanNames = context.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                Object bean = context.getBean(beanName);
                Class<?> beanClass = bean.getClass();
                // Handle CGLIB proxies if necessary
                if (beanClass.getName().contains("$$")) {
                    beanClass = beanClass.getSuperclass();
                }
                for (Method method : beanClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(McpTool.class)) {
                        server.addTool(bean);
                        break;
                    }
                }
            }
        };
    }
}
