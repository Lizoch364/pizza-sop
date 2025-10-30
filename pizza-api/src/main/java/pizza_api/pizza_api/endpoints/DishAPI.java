package pizza_api.pizza_api.endpoints;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import pizza_api.pizza_api.dto.DishRequest;
import pizza_api.pizza_api.dto.DishResponse;
import pizza_api.pizza_api.dto.DishUpdateRequest;
import pizza_api.pizza_api.dto.StatusResponse;

@Tag(name = "dish", description = "API для работы с блюдами")
public interface DishAPI {
    @Operation(summary = "Получить блюдо по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо найдено"),
            @ApiResponse(responseCode = "404", description = "Блюдо не найдено",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @GetMapping("/api/dishes/{id}")
    EntityModel<DishResponse> getDishByIb(@PathVariable("id") int id);

    @Operation(summary = "Создать новое блюдо")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Блюдо успешно создано"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @PostMapping("/api/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<DishResponse>> createDish(@Valid @RequestBody DishRequest request);

    @Operation(summary = "Получить список всех блюд c пагинацией и фильтрацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюда успешно получены"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @GetMapping("/api/dishes/")
    PagedModel<EntityModel<DishResponse>> getAllDish(
            @Parameter(description = "Фильтр на доступность блюда для заказа") @RequestParam(required = false) Boolean isActive,
            @Parameter(description = "Номер страницы (0..N)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Размер страницы") @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Обновить блюдо")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @PatchMapping("/api/dishes/{id}")
    EntityModel<DishResponse> updateDish(@Valid @PathVariable("id") int id, @RequestBody DishUpdateRequest request);

    @Operation(summary = "Удалить блюдо")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Блюдо успешно отменено"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @DeleteMapping("/api/dishes/{id}")
    void deleteDish(@Valid @PathVariable("id") int id);
}
