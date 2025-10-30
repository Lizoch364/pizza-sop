package com.example.demo.services;

import pizza_api.pizza_api.dto.OrderRequest;
import pizza_api.pizza_api.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAll(String status, String dish);
    OrderResponse findById(int id);
    OrderResponse create(OrderRequest request);
    void delete(int id);
}
