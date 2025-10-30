package com.example.demo.repository;

import com.example.demo.domain.Dish;
import com.example.demo.repository.baseRepository.CreateRepository;
import com.example.demo.repository.baseRepository.GetRepository;
import com.example.demo.repository.baseRepository.UpdateRepository;
import pizza_api.pizza_api.dto.DishRequest;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.DishUpdateRequest;
import pizza_api.pizza_api.dto.PagesResponse;

public interface DishRepository extends CreateRepository<Dish>, GetRepository<Dish>, UpdateRepository<Dish> {

}
