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
    UUID restaurantId;
}
