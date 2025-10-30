package pizza_api.pizza_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record DishRequest (
        @NotBlank(message = "Название блюда не может быть пустым") String name,
        @NotEmpty(message = "Список продуктов не может быть пустым")List<Integer> products,
        @Positive(message = "Цена должна быть положительным числом") Double price
){}
