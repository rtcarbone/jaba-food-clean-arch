package app.jabafood.cleanarch.application.dto;

import app.jabafood.cleanarch.domain.enums.CuisineType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public record RestaurantResponseDTO(
        UUID id,
        String name,
        AddressResponseDTO address,
        CuisineType cuisineType,
        LocalTime openingTime,
        LocalTime closingTime,
        UserSummaryDTO owner
) implements Serializable {
}