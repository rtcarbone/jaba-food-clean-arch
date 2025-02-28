package app.jabafood.cleanarch.domain.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class MenuItem {
    UUID id;
    String name;
    String description;
    BigDecimal price;
    boolean inRestaurantOnly;
    String imagePath;
    Restaurant restaurant;

    public MenuItem copyWith(
            String name,
            String description,
            BigDecimal price,
            Boolean inRestaurantOnly,
            String imagePath,
            Restaurant restaurant
    ) {
        return new MenuItem(
                this.id,
                name != null ? name : this.name,
                description != null ? description : this.description,
                price != null ? price : this.price,
                inRestaurantOnly != null ? inRestaurantOnly : this.inRestaurantOnly,
                imagePath != null ? imagePath : this.imagePath,
                restaurant != null ? restaurant : this.restaurant
        );
    }

}
