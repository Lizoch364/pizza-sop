package com.example.demo.repository;

import com.example.demo.domain.Dish;
import com.example.demo.domain.Product;
import com.example.demo.repository.baseRepository.CreateRepository;
import com.example.demo.repository.baseRepository.GetRepository;

public interface ProductRepository extends GetRepository<Product> {
}
