package app.jabafood.cleanarch.domain.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class Restaurant {
    UUID id;
    String name;
    String cuisineType;
    String openingHours;
    UUID ownerId;
    List<UUID> menuItems;

    public Restaurant copyWith(String name, String cuisineType, String openingHours) {
        return new Restaurant(
                this.id,
                name != null ? name : this.name,
                cuisineType != null ? cuisineType : this.cuisineType,
                openingHours != null ? openingHours : this.openingHours,
                this.ownerId,
                this.menuItems
        );
    }
}
