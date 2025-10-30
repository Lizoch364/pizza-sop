package com.example.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    private String name;
    private String description;
    private int count;

    public Product(String name, String description, int count) {
        this.name = name;
        this.description = description;
        this.count = count;
    }

    protected Product() {}

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
