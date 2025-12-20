package com.example.housemcp.service;

import com.example.housemcp.model.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    void shouldLoadProperties() {
        List<Property> properties = propertyService.getAllProperties();
        assertThat(properties).isNotEmpty();
    }
}
