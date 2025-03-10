package app.jabafood.cleanarch.interfaceAdapters.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemRequestDTO(
        @NotBlank(message = "Item name cannot be empty")
        String name,

        @NotBlank(message = "Description cannot be empty")
        String description,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be greater than or equal to 0")
        BigDecimal price,

        boolean inRestaurantOnly,

        String imagePath,

        @NotNull(message = "Restaurant ID is required")
        UUID restaurantId
) implements Serializable {
}