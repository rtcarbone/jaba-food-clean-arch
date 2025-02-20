package app.jabafood.cleanarch.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class MenuItem {
    final UUID id;
    final String name;
    final String description;
    final BigDecimal price;
    final boolean inRestaurantOnly;
    final String imagePath;
    final Restaurant restaurant;

    public MenuItem(@NonNull String name, @NonNull String description, @NonNull BigDecimal price,
                    boolean inRestaurantOnly, @NonNull String imagePath, @NonNull Restaurant restaurant) {
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price must be positive");

        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.inRestaurantOnly = inRestaurantOnly;
        this.imagePath = imagePath;
        this.restaurant = restaurant;
    }

    public MenuItem(@NonNull UUID id, @NonNull String name, @NonNull String description, @NonNull BigDecimal price,
                    boolean inRestaurantOnly, @NonNull String imagePath, @NonNull Restaurant restaurant) {
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price must be positive");

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inRestaurantOnly = inRestaurantOnly;
        this.imagePath = imagePath;
        this.restaurant = restaurant;
    }
}
