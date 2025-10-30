package com.example.demo.repository.impl;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.baseRepository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl extends BaseRepositoryImpl<Product> implements ProductRepository {
    ProductRepositoryImpl(){
        super(Product.class);
    }
}
