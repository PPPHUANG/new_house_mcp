package com.example.housemcp.service;

import com.example.housemcp.model.Property;
import java.util.List;
import java.util.Optional;

public interface PropertyService {
    List<Property> getAllProperties();
    Optional<Property> getPropertyById(String id);
}
