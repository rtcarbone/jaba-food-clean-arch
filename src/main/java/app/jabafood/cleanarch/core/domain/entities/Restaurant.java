package app.jabafood.cleanarch.core.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class Restaurant {
    final UUID id;
    final String name;
    final String cuisineType;
    final String openingHours;
    final User owner;

    public Restaurant(UUID id, @NonNull String name, @NonNull String cuisineType,
                      @NonNull String openingHours, @NonNull User owner) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.openingHours = openingHours;
        this.owner = owner;
    }
}
