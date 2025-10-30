package com.example.demo.storage;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.OrderResponse;
import pizza_api.pizza_api.dto.OrderStatus;
import pizza_api.pizza_api.dto.ProductResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStorage {
//    public final Map<Integer, OrderResponse> orders = new ConcurrentHashMap<>();
//    public final Map<Integer, DishResponse> dishes = new ConcurrentHashMap<>();
//    public final Map<Integer, ProductResponse> products = new ConcurrentHashMap<>();
//
//    private int dishId = 1;
//    private int orderId = 1;
//    private int productId = 1;
//
//    @PostConstruct
//    public void init() {
//        ProductResponse milk = new ProductResponse(incrementProductId(), "milk", "cow milk", 2);
//        ProductResponse cacao = new ProductResponse(incrementProductId(), "cacao", "choco cacao", 2);
//
//        List<ProductResponse> products1 = new ArrayList<ProductResponse>();
//        products1.add(milk);
//        products1.add(cacao);
//
//        DishResponse dish1 = new DishResponse(incrementDishId(), "cacao",products1, 12, Boolean.TRUE);
//
//        List<DishResponse> dishes1 = new ArrayList<>();
//        dishes1.add(dish1);
//
//        OrderResponse order1 = new OrderResponse(incrementOrderId(), dishes1, OrderStatus.CREATED, LocalDateTime.now());
//
//        products.put(milk.getId(), milk);
//        products.put(cacao.getId(), cacao);
//
//        dishes.put(dish1.getId(), dish1);
//
//        orders.put(order1.getId(), order1);
//    }
//
//    public int incrementDishId() {
//       return dishId++;
//    }
//
//    public int incrementOrderId() {
//        return orderId++;
//    }
//
//    public int incrementProductId() { return productId++; }

}
