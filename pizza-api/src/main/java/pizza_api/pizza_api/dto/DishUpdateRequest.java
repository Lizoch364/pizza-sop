package pizza_api.pizza_api.dto;

import java.util.List;

public record DishUpdateRequest (
  int id, 
  String name,
  List<Integer> products,
  Double price
){}
