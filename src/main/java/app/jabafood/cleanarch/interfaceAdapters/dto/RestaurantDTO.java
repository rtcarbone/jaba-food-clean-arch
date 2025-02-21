package app.jabafood.cleanarch.interfaceAdapters.dto;

import java.util.UUID;

public record RestaurantDTO(
        UUID id,
        String name,
        String cuisineType,
        String openingHours,
        UserDTO owner
) {
}
