package com.example.demo.domain;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
    private int id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }
}