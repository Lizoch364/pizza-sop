package pizza_api.pizza_api.endpoints;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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
import pizza_api.pizza_api.dto.OrderRequest;
import pizza_api.pizza_api.dto.OrderResponse;
import pizza_api.pizza_api.dto.StatusResponse;

@Tag(name = "orders", description = "API для работы с заказами")
public interface OrderAPI {
 @Operation(summary = "Получить заказ по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказ найден"),
            @ApiResponse(responseCode = "404", description = "Заказ не найден",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @GetMapping("/api/orders/{id}")
    EntityModel<OrderResponse> getOrderById(@PathVariable("id") int id);

    @Operation(summary = "Создать новый заказ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EntityModel<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest request);

    @Operation(summary = "Получить список всех заказов с фильтрацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Заказы успешно получены"),
            @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                    content = @Content(schema = @Schema(implementation = StatusResponse.class)))
    })
    @GetMapping("/api/orders/")
    CollectionModel<EntityModel<OrderResponse>> getAllOrder(
            @Parameter(description = "Фильтр по статусу и содержимому заказа") @RequestParam(required = false) String orderStatus, @RequestParam(required = false) String dish
    );

   @Operation(summary = "Удалить заказ")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Заказ успешно отменен"),
           @ApiResponse(responseCode = "400", description = "Невалидный запрос",
                   content = @Content(schema = @Schema(implementation = StatusResponse.class)))
   })
   @DeleteMapping("/api/orders/{id}")
   void deleteOrder(@Valid @PathVariable("id") int id);
}
