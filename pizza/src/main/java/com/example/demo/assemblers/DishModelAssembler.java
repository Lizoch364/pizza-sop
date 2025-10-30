package com.example.demo.assemblers;

import com.example.demo.controllers.DishController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import pizza_api.pizza_api.dto.DishResponse;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DishModelAssembler implements RepresentationModelAssembler<DishResponse, EntityModel<DishResponse>> {
    @Override
    public EntityModel<DishResponse> toModel(DishResponse dish) {
        return EntityModel.of(dish,
                linkTo(methodOn(DishController.class).getDishByIb(dish.getId())).withSelfRel(),
                linkTo(methodOn(DishController.class).getAllDish(true, 0, 10)).withRel("collection")
        );
    }
}
