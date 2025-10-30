package com.example.demo.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity{
    private String name;
    private List<Product> products;
    private Double price;
    private Boolean isActive;

    public Dish(String name, List<Product>products, Double price) {
        this.name = name;
        this.products = products;
        this.price = price;
        this.isActive = Boolean.TRUE;
    }

    protected Dish() {}

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "dish_products",
            joinColumns = { @JoinColumn(name = "dish_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    public List<Product> getProducts() {
        return products;
    }

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    @Column(name = "is_active", nullable = false)
    public Boolean getActive() {
        return isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
