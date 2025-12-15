package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.domain.Dish;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.events.OrderCreatedEvent;
import com.example.demo.events.OrderDeletedEvent;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.services.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza_api.pizza_api.dto.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, DishRepository dishRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<OrderResponse> getAll(String status, String dish) {

        Stream<OrderResponse> orders = orderRepository.findAll().stream().map(order -> toOrderResponse(order));

        if (status != null) {
            orders = orders.filter(order -> order.getStatus().name().equals(status));
        }

        if (dish != null) {
            String lowerDish = dish.toLowerCase();
            orders = orders.filter(order ->
                    order.getDishes().stream().anyMatch(d -> d.getName().toLowerCase().contains(lowerDish))
            );
        }

        return orders.toList();
    }

    @Override
    public OrderResponse findById(int id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new RuntimeException("Заказ с " + id + " не найден");
        }

        return toOrderResponse(order.get());
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        List<Dish> dishes = new ArrayList<>();
        for (int dishId : request.dishes()) {
            Optional<Dish> dish = dishRepository.findById(dishId);
            if (dish.isEmpty()) {
                throw new RuntimeException("Блюдо с " + dishId + " не найдено");
            }

            dishes.add(dish.get());
        }

        Order order = new Order(dishes, request.promo());
        orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getDishes().stream().map(dish -> dish.getId()).toList(),
                order.getPromo(),
                order.getStatus().toString()
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_ORDER_CREATED, event);
        return toOrderResponse(order);
    }

    @Override
    public void delete(int id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new RuntimeException("Заказ с " + id + " не найден");
        }

        order.get().setStatus(OrderStatus.CANCELLED);

        OrderDeletedEvent event = new OrderDeletedEvent(id);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_ORDER_DELETED, event);

        orderRepository.update(order.get());
    }

    private OrderResponse toOrderResponse(Order order) {
        List<DishResponse> dishes = new ArrayList<>();

        for (var dish : order.getDishes()) {
            dishes.add(toDishResponse(dish));
        }

        return new OrderResponse(
                order.getId(),
                dishes,
                order.getStatus(),
                order.getPromo(),
                order.getCreatedAt()
        );
    }

    private DishResponse toDishResponse(Dish dish) {
        List<ProductResponse> products = new ArrayList<>();

        for(var product : dish.getProducts()) {
            products.add(toProductResponse(product));
        }

        return new DishResponse(
                dish.getId(),
                dish.getName(),
                products,
                dish.getPrice(),
                dish.getActive()
        );
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCount()
        );
    }
}
