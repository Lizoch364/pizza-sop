package com.example.demo.assemblers;

import com.example.demo.controllers.OrderController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pizza_api.pizza_api.dto.OrderResponse;
import pizza_api.pizza_api.dto.OrderStatus;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssemblers implements RepresentationModelAssembler<OrderResponse, EntityModel<OrderResponse>> {
    @Override
    public EntityModel<OrderResponse> toModel(OrderResponse order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrder(null, null)).withRel("get-all")
        );
    }
}
