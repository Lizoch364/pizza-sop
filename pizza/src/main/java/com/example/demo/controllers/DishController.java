package com.example.demo.controllers;

import com.example.demo.assemblers.DishModelAssembler;
import com.example.demo.services.impl.DishServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pizza_api.pizza_api.dto.DishRequest;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.DishUpdateRequest;
import pizza_api.pizza_api.dto.PagesResponse;
import pizza_api.pizza_api.endpoints.DishAPI;

@RestController
public class DishController implements DishAPI {
    private final DishServiceImpl service;
    private final DishModelAssembler dishModelAssembler;
    private final PagedResourcesAssembler<DishResponse> pagedResourcesAssembler;

    @Autowired
    public DishController(DishServiceImpl service, DishModelAssembler dishModelAssembler, PagedResourcesAssembler<DishResponse> pagedResourcesAssembler) {
        this.service = service;
        this.dishModelAssembler = dishModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    public PagedModel<EntityModel<DishResponse>> getAllDish(@RequestParam(required = false) Boolean isActive,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        PagesResponse<DishResponse> pagedResponse = service.getAll(isActive, page, size);

        Page<DishResponse> dishPage = new PageImpl<>(
                pagedResponse.content(),
                PageRequest.of(pagedResponse.pageNumber(), pagedResponse.pageSize()),
                pagedResponse.totalElements()
        );
        return pagedResourcesAssembler.toModel(dishPage, dishModelAssembler);
    }

    @Override
    public EntityModel<DishResponse> getDishByIb(@PathVariable("id") int id) {
        DishResponse dish = service.findById(id);
        return dishModelAssembler.toModel(dish);
    }

    @Override
    public ResponseEntity<EntityModel<DishResponse>> createDish(@Valid @RequestBody DishRequest request) {
        DishResponse dish = service.create(request);
        EntityModel<DishResponse> entityModel = dishModelAssembler.toModel(dish);

        return ResponseEntity
                .created(entityModel.getRequiredLink("self").toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<DishResponse> updateDish(@Valid int id, @RequestBody DishUpdateRequest request) {
        DishResponse updateDish = service.update(request);
        return dishModelAssembler.toModel(updateDish);
    }

    @Override
    public void deleteDish(@Valid @PathVariable("id") int id) {
        service.delete(id);
    }
}
