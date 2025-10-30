package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.repository.baseRepository.CreateRepository;
import com.example.demo.repository.baseRepository.GetRepository;
import com.example.demo.repository.baseRepository.UpdateRepository;
import pizza_api.pizza_api.dto.OrderRequest;
import pizza_api.pizza_api.dto.OrderResponse;

import java.util.List;

public interface OrderRepository extends CreateRepository<Order>, GetRepository<Order>, UpdateRepository<Order> {
}
