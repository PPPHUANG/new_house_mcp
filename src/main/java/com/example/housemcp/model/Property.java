package com.example.housemcp.model;

public class Property {
    private String id;
    private String address;
    private double price;
    private String description;
    private String type;
    private int bedrooms;
    private int bathrooms;

    public Property() {}

    public Property(String id, String address, double price, String description, String type, int bedrooms, int bathrooms) {
        this.id = id;
        this.address = address;
        this.price = price;
        this.description = description;
        this.type = type;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getBedrooms() { return bedrooms; }
    public void setBedrooms(int bedrooms) { this.bedrooms = bedrooms; }

    public int getBathrooms() { return bathrooms; }
    public void setBathrooms(int bathrooms) { this.bathrooms = bathrooms; }
}
