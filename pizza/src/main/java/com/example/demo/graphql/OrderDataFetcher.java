package com.example.demo.graphql;

import com.example.demo.services.OrderService;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.OrderRequest;
import pizza_api.pizza_api.dto.OrderResponse;

import java.util.List;
import java.util.Map;

public class OrderDataFetcher {
    private final OrderService orderService;

    public OrderDataFetcher(OrderService orderService) {
        this.orderService = orderService;
    }

    @DgsQuery
    public OrderResponse orderById(@InputArgument int id) {
        return orderService.findById(id);
    }

    @DgsQuery
    public List<OrderResponse> orders(@InputArgument String orderStatus, @InputArgument String dish) {
        return orderService.getAll(orderStatus, dish);
    }

    @DgsData(parentType = "Order", field = "dish")
    public List<DishResponse> dish(DataFetchingEnvironment dfe) {
        OrderResponse order = dfe.getSource();
        return order.getDishes();
    }

    @DgsMutation
    public OrderResponse createOrder(@InputArgument("input") Map<String, Object> input) {
        OrderRequest request = new OrderRequest(
                (List<Integer>) input.get("dishes"),
                (String) input.get("promo")
        );
        return orderService.create(request);
    }

    @DgsMutation
    public int deleteOrder(@InputArgument int id) {
        orderService.delete(id);
        return id;
    }

}
