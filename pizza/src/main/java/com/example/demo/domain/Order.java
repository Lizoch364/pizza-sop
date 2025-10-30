package com.example.demo.domain;

import jakarta.persistence.*;
import pizza_api.pizza_api.dto.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    private List<Dish> dishes;
    private OrderStatus status;
    private String promo;
    private LocalDateTime createdAt;

    public Order(List<Dish> dishes, String promo) {
        this.dishes = dishes;
        this.promo = promo;
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    protected Order() {}

    @ManyToMany(cascade = { CascadeType.PERSIST })
    @JoinTable(name = "order_dishes",
            joinColumns = { @JoinColumn(name = "order_id") },
            inverseJoinColumns = { @JoinColumn(name = "dish_id") }
    )
    public List<Dish> getDishes() {
        return dishes;
    }

    @Column(name = "promo", nullable = false)
    public String getPromo() {
        return promo;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
