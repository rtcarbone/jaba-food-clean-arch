package app.jabafood.cleanarch.interfaceAdapters.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Boolean inRestaurant,
        String imagePath,
        RestaurantSummaryDTO restaurant
) implements Serializable {
}
