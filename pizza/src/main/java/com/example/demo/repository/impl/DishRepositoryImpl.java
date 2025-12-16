package com.example.demo.repository.impl;

import com.example.demo.domain.Dish;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.baseRepository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pizza_api.pizza_api.dto.*;
import pizza_api.pizza_api.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class DishRepositoryImpl extends BaseRepositoryImpl<Dish> implements DishRepository {
    DishRepositoryImpl(){
        super(Dish.class);
    }
}
