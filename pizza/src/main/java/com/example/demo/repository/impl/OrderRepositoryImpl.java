package com.example.demo.repository.impl;

import com.example.demo.domain.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.baseRepository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryImpl extends BaseRepositoryImpl<Order> implements OrderRepository {
    OrderRepositoryImpl(){
        super(Order.class);
    }
}
