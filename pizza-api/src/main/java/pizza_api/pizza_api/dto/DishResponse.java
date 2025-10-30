package pizza_api.pizza_api.dto;

import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class DishResponse extends RepresentationModel<DishResponse> {
  private final int id;
  private final String name;
  private final List<ProductResponse> products;
  private final Double price;
  private final Boolean isActive;

  public DishResponse(int id, String name, List<ProductResponse> products, double price, Boolean isActive) {
    this.id = id;
    this.name = name;
    this.products = products;
    this.price = price;
    this.isActive = isActive;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<ProductResponse> getProducts() {
    return products;
  }

  public Double getPrice() {
    return price;
  }

  public Boolean isActive() {
    return isActive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DishResponse that = (DishResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(products, that.products)  && Objects.equals(price, that.price) && Objects.equals(isActive, that.isActive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, products, price, isActive);
  }
}


