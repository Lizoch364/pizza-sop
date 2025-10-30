package com.example.demo.services.impl;

import ch.qos.logback.core.hook.DefaultShutdownHook;
import com.example.demo.domain.Dish;
import com.example.demo.domain.Product;
import com.example.demo.repository.DishRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza_api.pizza_api.dto.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DishServiceImpl(DishRepository dishRepository, ProductRepository productRepository) {
        this.dishRepository = dishRepository;
        this.productRepository = productRepository;
    }

    @Override
    public PagesResponse<DishResponse> getAll(Boolean isActive, int page, int size) {
        Stream<DishResponse> dishes = dishRepository.findAll().stream().map(dish -> toDishResponse(dish));

        if (isActive != null) {
            dishes = dishes.filter(dish -> dish.isActive() == isActive);
        }

        List<DishResponse> dishesList = dishes.toList();

        int totalElements = dishesList.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<DishResponse> pageContent = (fromIndex > toIndex) ? List.of() : dishesList.subList(fromIndex, toIndex);

        return new PagesResponse<>(pageContent, page, size, totalElements, totalPages, page >= totalPages - 1);
    }

    @Override
    public DishResponse findById(int id) {
        Optional<Dish> dish = dishRepository.findById(id);

        if (dish.isEmpty()) {
            throw new RuntimeException("Блюдо с " + id + " не найдено");
        }

        return toDishResponse(dish.get());
    }

    @Override
    public DishResponse create(DishRequest request) {
        List<Product> products = new ArrayList<>();

        for(int productId : request.products()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new RuntimeException("Продукт с " + productId + " не найден");
            }

            products.add(product.get());
        }

        Dish dish = new Dish(request.name(), products, request.price());
        var result = dishRepository.save(dish);

        if (result == null) {
            throw new RuntimeException("Блюдо не созданo");
        }

        return toDishResponse(dish);
    }

    @Override
    public DishResponse update(DishUpdateRequest request) {
        Optional<Dish> dish = dishRepository.findById(request.id());

        if (dish.isEmpty()) {
            throw new RuntimeException("Блюдо с " + request.id() + " не найдено");
        }

        Dish updateDish = dish.get();

        updateDish.setName(request.name());

        List<Product> products = new ArrayList<>();

        for(var productId : request.products()) {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isEmpty()) {
                throw new RuntimeException("Продукт с " + productId + " не найден");
            }

            products.add(product.get());
        }

        updateDish.setProducts(products);
        updateDish.setPrice(request.price());

        dishRepository.update(updateDish);

        return toDishResponse(updateDish);

    }

    @Override
    public void delete(int id) {
        Optional<Dish> dish = dishRepository.findById(id);

        if (dish.isEmpty()) {
            throw new RuntimeException("Блюдо с " + id + " не найдено");
        }
        dish.get().setActive(Boolean.FALSE);

        dishRepository.update(dish.get());
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
