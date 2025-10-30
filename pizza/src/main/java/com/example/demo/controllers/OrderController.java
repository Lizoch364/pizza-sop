package com.example.demo.controllers;

import com.example.demo.assemblers.OrderModelAssemblers;
import com.example.demo.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pizza_api.pizza_api.dto.OrderRequest;
import pizza_api.pizza_api.dto.OrderResponse;
import pizza_api.pizza_api.endpoints.OrderAPI;

import java.util.List;

@RestController
public class OrderController implements OrderAPI {

  private final OrderService orderService;
  private final OrderModelAssemblers orderModelAssemblers;

  @Autowired
  public OrderController(OrderService orderService, OrderModelAssemblers orderModelAssemblers) {
      this.orderService = orderService;
      this.orderModelAssemblers = orderModelAssemblers;
  }

  @Override
  public EntityModel<OrderResponse> getOrderById(@PathVariable("id") int id) {
    OrderResponse order = orderService.findById(id);
    return orderModelAssemblers.toModel(order);
  }

  @Override
  public CollectionModel<EntityModel<OrderResponse>> getAllOrder(@RequestParam(required = false) String orderStatus,
                                                                 @RequestParam(required = false) String dish) {
    List<OrderResponse> orders = orderService.getAll(orderStatus, dish);
    return orderModelAssemblers.toCollectionModel(orders);
  }

  @Override
  public ResponseEntity<EntityModel<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request) {
    OrderResponse order = orderService.create(request);
    EntityModel<OrderResponse> entityModel = orderModelAssemblers.toModel(order);

    return  ResponseEntity
            .created(entityModel.getRequiredLink("self").toUri())
            .body(entityModel);
  }

  @Override
  public void deleteOrder(@Valid @PathVariable("id") int id) {
      orderService.delete(id);
  }
}
