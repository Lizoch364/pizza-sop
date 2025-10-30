package pizza_api.pizza_api.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
  @NotEmpty(message = "Список блюд не может быть пустым")
  List<Integer> dishes,
  String promo
) {}
