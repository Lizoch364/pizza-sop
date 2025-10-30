package pizza_api.pizza_api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class OrderResponse extends RepresentationModel<OrderResponse> {
  private final int id;
  private final List<DishResponse> dishes;
  private final OrderStatus status;
  private final String promo;
  private final LocalDateTime createdAt;

  public OrderResponse(int id, List<DishResponse> dishes, OrderStatus status, String promo, LocalDateTime createdAt) {
    this.id = id;
    this.dishes = dishes;
    this.status = status;
    this.promo = promo;
    this.createdAt = createdAt;
  }

  public int getId() {
    return id;
  }


  public List<DishResponse> getDishes() {
    return dishes;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public String getPromo() {
    return promo;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    OrderResponse that = (OrderResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(dishes, that.dishes)  && Objects.equals(status, that.status)  && Objects.equals(createdAt, that.createdAt) && Objects.equals(promo, that.promo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, dishes, status, promo, createdAt);
  }
}
