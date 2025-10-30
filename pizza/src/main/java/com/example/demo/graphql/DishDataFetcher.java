package com.example.demo.graphql;

import com.example.demo.services.DishService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import pizza_api.pizza_api.dto.DishRequest;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.DishUpdateRequest;
import pizza_api.pizza_api.dto.PagesResponse;

import java.util.List;
import java.util.Map;

@DgsComponent
public class DishDataFetcher {
    private final DishService dishService;

    public DishDataFetcher(DishService dishService) {
        this.dishService = dishService;
    }

    @DgsQuery
    public PagesResponse<DishResponse> dishes(@InputArgument Boolean isActive, @InputArgument int page, @InputArgument int size) {
        return dishService.getAll(isActive, page, size);
    }

    @DgsQuery
    public DishResponse dishById(@InputArgument int id) {
        return dishService.findById(id);
    }

    @DgsMutation
    public DishResponse createDish(@InputArgument("input") Map<String, Object> input) {
        DishRequest request = new DishRequest((String) input.get("name"), (List<Integer>) input.get("products"), (double) input.get("price"));
        return dishService.create(request);
    }

    @DgsMutation
    public DishResponse updateDish(@InputArgument int id, @InputArgument("input") Map<String, Object> input) {
        DishUpdateRequest request = new DishUpdateRequest((int) input.get("id"), (String) input.get("name"), (List<Integer>) input.get("products"), (double) input.get("price"));
        return dishService.update(request);
    }

    @DgsMutation
    public int deleteDish(@InputArgument int id) {
        dishService.delete(id);
        return id;
    }
}


