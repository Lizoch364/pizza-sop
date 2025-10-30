package com.example.demo.services;

import pizza_api.pizza_api.dto.DishRequest;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.DishUpdateRequest;
import pizza_api.pizza_api.dto.PagesResponse;


public interface DishService {
    PagesResponse<DishResponse> getAll(Boolean isActive, int pade, int size);
    DishResponse findById(int id);
    DishResponse create(DishRequest request);
    DishResponse update(DishUpdateRequest request);
    void delete(int id);
}
