package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for creating or updating a Menu Item")
public record MenuItemRequestDTO(

        @NotBlank(message = "Item name cannot be empty")
        @Schema(description = "Name of the menu item", example = "Margherita Pizza", maxLength = 255)
        String name,

        @NotBlank(message = "Description cannot be empty")
        @Schema(description = "Description of the menu item", example = "Classic pizza with fresh mozzarella and basil")
        String description,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be greater than or equal to 0")
        @Schema(description = "Price of the menu item", example = "12.99", minimum = "0")
        BigDecimal price,

        @Schema(description = "Indicates if the item is available only for in-restaurant dining", example = "true")
        boolean inRestaurantOnly,

        @Schema(description = "Path to the image of the menu item", example = "/images/menu/margherita.jpg")
        String imagePath,

        @NotNull(message = "Restaurant ID is required")
        @Schema(description = "ID of the restaurant this menu item belongs to", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
        UUID restaurantId

) implements Serializable {
}