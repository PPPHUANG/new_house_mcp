package com.example.housemcp.service;

import com.example.housemcp.model.Property;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JsonPropertyService implements PropertyService {

    private List<Property> properties = Collections.emptyList();
    private final ObjectMapper objectMapper;

    public JsonPropertyService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("properties.json");
        if (resource.exists()) {
            properties = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Property>>() {});
        } else {
            System.err.println("properties.json not found!");
        }
    }

    @Override
    public List<Property> getAllProperties() {
        return properties;
    }

    @Override
    public Optional<Property> getPropertyById(String id) {
        return properties.stream().filter(p -> p.getId().equals(id)).findFirst();
    }
}
