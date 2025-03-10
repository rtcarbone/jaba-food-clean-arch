package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.CuisineType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantRequestDTO(
        @NotBlank(message = "Restaurant name cannot be blank")
        @Size(max = 255, message = "Restaurant name must be at most 255 characters")
        String name,

        @NotNull(message = "Address cannot be null")
        AddressRequestDTO address,

        @NotNull(message = "Cuisine type cannot be null")
        CuisineType cuisineType,

        @NotNull(message = "Opening time cannot be null")
        LocalTime openingTime,

        @NotNull(message = "Closing time cannot be null")
        LocalTime closingTime,

        @NotNull(message = "Owner cannot be null")
        UUID ownerId
) implements Serializable {
}