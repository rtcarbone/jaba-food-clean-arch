package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for creating or updating a Menu Item")
public record MenuItemRequestDTO(

        @NotBlank(message = "Item name cannot be empty")
        @Size(max = 255, message = "Item name must be at most 255 characters")
        @Schema(description = "Name of the menu item", example = "Margherita Pizza", maxLength = 255)
        String name,

        @NotBlank(message = "Description cannot be empty")
        @Size(max = 1000, message = "Description must be at most 1000 characters")
        @Schema(description = "Description of the menu item", example = "Classic pizza with fresh mozzarella and basil", maxLength = 1000)
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0")
        @Schema(description = "Price of the menu item", example = "12.99", minimum = "0.00")
        BigDecimal price,

        @Schema(description = "Indicates if the item is available only for in-restaurant dining", example = "true")
        boolean inRestaurantOnly,

        @Size(max = 500, message = "Image path must be at most 500 characters")
        @Schema(description = "Path to the image of the menu item", example = "/images/menu/margherita.jpg", maxLength = 500)
        String imagePath,

        @NotNull(message = "Restaurant ID is required")
        @Schema(description = "ID of the restaurant this menu item belongs to", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
        UUID restaurantId

) implements Serializable {
}