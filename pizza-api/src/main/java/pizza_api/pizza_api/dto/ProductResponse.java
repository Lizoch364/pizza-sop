package pizza_api.pizza_api.dto;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class ProductResponse extends RepresentationModel<ProductResponse>{
  private final int id;
  private final String name;
  private final String description;
  private final int count;

  public ProductResponse(int id, String name, String description, int count) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.count = count;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getCount() {
    return count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ProductResponse that = (ProductResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description)  && Objects.equals(count, that.count);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, description, count);
  }
}
