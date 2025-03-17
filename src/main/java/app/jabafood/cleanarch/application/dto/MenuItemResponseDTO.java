package app.jabafood.cleanarch.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO for returning menu item details")
public record MenuItemResponseDTO(

        @Schema(description = "Unique identifier of the menu item",
                example = "550e8400-e29b-41d4-a716-446655440000",
                format = "uuid")
        UUID id,

        @Schema(description = "Name of the menu item",
                example = "Margherita Pizza",
                maxLength = 255)
        String name,

        @Schema(description = "Description of the menu item",
                example = "Classic pizza with fresh mozzarella and basil")
        String description,

        @Schema(description = "Price of the menu item",
                example = "12.99",
                minimum = "0")
        BigDecimal price,

        @Schema(description = "Indicates if the item is available only for in-restaurant dining",
                example = "true")
        Boolean inRestaurantOnly,

        @Schema(description = "Path to the image of the menu item",
                example = "/images/menu/margherita.jpg")
        String imagePath,

        @Schema(description = "Summary information about the restaurant associated with this menu item",
                implementation = RestaurantSummaryDTO.class)
        RestaurantSummaryDTO restaurant

) implements Serializable {
}
