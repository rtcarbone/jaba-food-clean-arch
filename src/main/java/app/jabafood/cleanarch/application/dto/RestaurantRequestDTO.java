package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Schema(description = "DTO for creating or updating a Restaurant")
public record RestaurantRequestDTO(

        @NotBlank(message = "Restaurant name cannot be blank")
        @Size(max = 255, message = "Restaurant name must be at most 255 characters")
        @Schema(description = "Restaurant name", example = "Pizzeria Roma", maxLength = 255)
        String name,

        @NotNull(message = "Address cannot be null")
        @Schema(description = "Restaurant address details", implementation = AddressRequestDTO.class)
        AddressRequestDTO address,

        @NotNull(message = "Cuisine type cannot be null")
        @Schema(description = "Type of cuisine", example = "PIZZERIA", allowableValues = {"PIZZERIA", "BURGER", "ITALIAN", "JAPANESE"})
        CuisineType cuisineType,

        @NotNull(message = "Opening time cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Opening time of the restaurant", example = "10:00", type = "string", pattern = "HH:mm")
        LocalTime openingTime,

        @NotNull(message = "Closing time cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Closing time of the restaurant", example = "22:00", type = "string", pattern = "HH:mm")
        LocalTime closingTime,

        @NotNull(message = "Owner cannot be null")
        @Schema(description = "Owner ID of the restaurant. Must be a user of type RESTAURANT_OWNER", example = "550e8400-e29b-41d4-a716-446655440000", format = "uuid")
        UUID ownerId

) implements Serializable {
}
